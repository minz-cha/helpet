package com.helpet.vector

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.helpet.R
import com.helpet.databinding.ActivityVectorCameraBinding
import kotlinx.android.synthetic.main.activity_pet_register.*
import kotlinx.android.synthetic.main.activity_vector_camera.*
import kotlinx.android.synthetic.main.activity_vector_choice_pet.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody.Part.Companion.create
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*


class PetRegisterActivity : BaseActivity() {
    var statuspet = ""
    var imgpet = ""
    var speciespet = ""
    var namepet= ""
    var agepet = 0
    var birthpet = ""
    var genderpet = ""
//    status: 성공 실패 여부
//    petImg: 반려동물 사진
//    petSpecies: 반려동물 종(강아지/ 고양이)
//    petName : 이름
//    petAge : 나이
//    petBirth : 생일 (YYYYMMDD형식)
//    petGender: 남자 여자

    val PERM_STORAGE= 9
    val PERM_CAMERA= 10
    val REQ_CAMERA=11
    val CROP_PICTURE = 2

    val binding by lazy { ActivityVectorCameraBinding.inflate(LayoutInflater.from(applicationContext)) }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_register)
        requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), PERM_STORAGE)

        registerBack.setOnClickListener {
            val intent = Intent(this, VectorChoicePet::class.java)
            startActivity(intent)
        }
    }
    @RequiresApi(Build.VERSION_CODES.M)
    fun initViews(){
        choiceCamera.setOnClickListener {
            requestPermissions(arrayOf(Manifest.permission.CAMERA),PERM_CAMERA)
        }
    }

    //이미지의 실제 주소 가져올 변수 선언
    var realUri: Uri? = null
    fun openCamera() {

        //카메라 권한이 있는지 확인
        val intent= Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        createImageUri(newFileName(),"image/jpeg")?.let{uri->
            realUri= uri
            Log.d(realUri.toString(), "openCamera: opencamera")
            intent.putExtra(MediaStore.EXTRA_OUTPUT, realUri)
            intent.putExtra("return-data",true);

            getRealPathFromURI(realUri!!)

        }
        startActivityForResult(intent, CROP_PICTURE);

    }

    private fun getRealPathFromURI(uri: Uri): String {
        val buildName = Build.MANUFACTURER
        if(buildName.equals("Xiaomi")) {
            return uri.path.toString()
        }

        var columnIndex = 0
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        var cursor = contentResolver.query(uri, proj, null, null, null)

        if(cursor!!.moveToFirst()) {
            columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        }
        var result= cursor.getString(columnIndex)

        Log.d(cursor.getString(columnIndex), "getRealPathFromURI")
        return result!!

    }

    fun createImageUri(filename: String, mimeType: String): Uri?{
        val values=ContentValues()
        values.put(MediaStore.Images.Media.DISPLAY_NAME,filename)
        values.put(MediaStore.Images.Media.MIME_TYPE,mimeType)

        return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values)
    }

    fun newFileName():String{
        val sdf=SimpleDateFormat("yyyyMMdd_HHmmss")
        val filename=sdf.format(System.currentTimeMillis())
        Log.d(filename, "newFileName: filename")
        return "${filename}.jpeg"
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun permissionGranted(requestCode: Int) {
        when(requestCode){
            PERM_STORAGE -> initViews()
            PERM_CAMERA->openCamera()
        }
    }

    override fun permissionDenied(requestCode: Int) {
        when(requestCode){
            PERM_STORAGE->{
                Toast.makeText(this,"공용저장소 권한을 승인해야 사용할 수 있습니다", Toast.LENGTH_SHORT).show()
                finish()
            }
            PERM_CAMERA-> Toast.makeText(this,"카메라 권한을 승인해야 카메라를 사용할 수 있습니다.", Toast.LENGTH_SHORT).show()
        }
    }



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode== RESULT_OK){
            when(requestCode){
                // 이미지 크롭
                CROP_PICTURE-> {
                    // 이미지를 가져온 이후의 리사이즈할 이미지 크기를 결정합니다.
                    // 이후에 이미지 크롭 어플리케이션을 호출하게 됩니다.

                    val intent = Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(realUri, "image/*");

                    intent.putExtra("outputX", 400); //크롭한 이미지 x축 크기
                    intent.putExtra("outputY", 400); //크롭한 이미지 y축 크기
                    intent.putExtra("aspectX", 1); //크롭 박스의 x축 비율
                    intent.putExtra("aspectY", 1); //크롭 박스의 y축 비율
                    intent.putExtra("scale", true);
                    intent.putExtra("return-data", true);
                    intent.putExtra("output", realUri); // 크랍된 이미지를 해당 경로에 저장
                    // realUri2 -> Bitmap
                    // Bitmap -> ByteArray
                    startActivityForResult(intent, REQ_CAMERA);
                }
                REQ_CAMERA ->{
                    btnChoiceSuccess.isEnabled=true
                    btnChoiceSuccess.setBackgroundColor(Color.parseColor("#FD9374"))
                    realUri?.let { uri ->
                        var bitmap: Bitmap? = null
                        //카메라에서 찍은 사진을 비트맵으로 변환
                        bitmap = MediaStore.Images.Media
                            .getBitmap(contentResolver, realUri)
                        //이미지뷰에 이미지 로딩
                        choiceCamera.setImageBitmap(bitmap)
                    }

                    btnChoiceSuccess.setOnClickListener {
                        var bitmap = MediaStore.Images.Media.getBitmap(contentResolver, realUri)
                        val imgString = bitmapToString(bitmap)
                        UpdatePhoto(imgString!!,this)
                    }
                }
            }
        }
    }

    private val server2=  RetrofitApi2.retrofit2.create(PetService::class.java)

    fun UpdatePhoto(imgString: String, context: Context) {
        val fileBody = RequestBody.create("image/*".toMediaTypeOrNull(), imgString)
        val multipartBody: MultipartBody.Part? =
            MultipartBody.Part.createFormData("postImg", "postImg.jpeg", fileBody)
        val mediaType = "multipart/form-data".toMediaType()

        var petSpecies = ""
        if (choiceCat.isClickable){
            petSpecies = "고양이"
        }
        if (choiceDog.isClickable){
            petSpecies = "강아지"
        }
        val textSpecies = petSpecies.toRequestBody(mediaType)
        val petName: String = petName.text.toString()
        val textName = petName.toRequestBody(mediaType)
        val petAge: String = petAge.text.toString()
        val textAge = petAge.toRequestBody(mediaType)
        val petBirth: String = petBirth.text.toString()
        val textBirth= petBirth.toRequestBody(mediaType)

        var petGender = ""
        if (genderBoy.isClickable){
            petGender = "고양이"
        }
        if (genderGirl.isClickable){
            petGender = "강아지"
        }
        val textGender = petSpecies.toRequestBody(mediaType)

        server2.PetRegister(multipartBody!!,textSpecies,textName,textAge,textBirth,textGender).enqueue(object :
            Callback<PetResponseDto?> {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(call: Call<PetResponseDto?>?, response: Response<PetResponseDto?>) {
//                Toast.makeText(context, "File Uploaded Successfully...", Toast.LENGTH_LONG).show();
                Log.d("레트로핏 결과2", "" + response.body().toString())
                // 다른 액티비티로 intent
                val intent = Intent(context, VectorChoicePet::class.java)

                // 인텐트에 데이터 추가
//                statuspet = response.body()?.status!!
//                imgpet = response.body()?.petList?.get(0)?.petImg!!
//                speciespet =  response.body()?.petList?.get(0)?.petBirth!!
//                namepet = response.body()?.petList?.get(0)?.petName!!
//                agepet = response.body()?.petList?.get(0)?.petAge!!
//                birthpet = response.body()?.petList?.get(0)?.petBirth!!
//                genderpet = response.body()?.petList?.get(0)?.petGender!!

                //이미지 다시 비트맵으로 변환
//                val imgpet2 = stringToBitmap(imgpet)

//                intent.putExtra("statuspet", statuspet)
//                intent.putExtra("imgpet", imgpet2)
//                intent.putExtra("speciespet", speciespet)
//                intent.putExtra("namepet", namepet)
//                intent.putExtra("agepet", agepet)
//                intent.putExtra("birthpet", birthpet)
//                intent.putExtra("genderpet", genderpet)
                // 액티비티 시작
                context.startActivity(intent)
            }

            override fun onFailure(call: Call<PetResponseDto?>?, t: Throwable) {
                Toast.makeText(context, "통신 실패", Toast.LENGTH_SHORT).show()
                Log.d("에러", t.message!!)
            }
        })
    }

    //bitmap 을  string 형태로 변환하는 메서드 (이렇게 string 으로 변환된 데이터를 mysql 에서 longblob 의 형태로 저장하는식으로 사용가능)
    @RequiresApi(Build.VERSION_CODES.O)
    fun bitmapToString(bitmap: Bitmap): String? {
        var image = ""
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val byteArray = stream.toByteArray()
        image = Base64.getEncoder().encodeToString(byteArray)
        return image
    }

    //string 을  bitmap 형태로 변환하는 메서드
    @RequiresApi(Build.VERSION_CODES.O)
    fun stringToBitmap(data: String?): Bitmap? {
        var bitmap: Bitmap? = null
        val byteArray: ByteArray = Base64.getDecoder().decode(data)
        val stream = ByteArrayInputStream(byteArray)
        bitmap = BitmapFactory.decodeStream(stream)
        return bitmap
    }
}



