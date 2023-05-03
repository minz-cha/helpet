package com.helpet.vector

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
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
import java.text.SimpleDateFormat


class PetRegisterActivity : BaseActivity() {
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
    }
    @RequiresApi(Build.VERSION_CODES.M)
    fun initViews(){
        cameraAlt.setOnClickListener {
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
            intent.putExtra("return-data", true);

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
                    btn_success.isEnabled=true
                    btn_success.setBackgroundColor(Color.parseColor("#FD9374"))
                    realUri?.let { uri ->
                        var bitmap: Bitmap? = null
                        //카메라에서 찍은 사진을 비트맵으로 변환
                        bitmap = MediaStore.Images.Media
                            .getBitmap(contentResolver, realUri)
                        //이미지뷰에 이미지 로딩
                        cameraAlt.setImageBitmap(bitmap)
                    }

                    btn_success.setOnClickListener {
                        var bitmap = MediaStore.Images.Media.getBitmap(contentResolver, realUri)
                        UpdatePhoto(SerialBitmap.translate(bitmap),this)
                    }
                }
            }
        }
    }

    private val server=  RetrofitApi2.retrofit2.create(PetService::class.java)

    fun UpdatePhoto(byteArray: ByteArray, context: Context) {
        val fileBody = RequestBody.create("image/*".toMediaTypeOrNull(), byteArray)
        val multipartBody: MultipartBody.Part? =
            MultipartBody.Part.createFormData("postImg", "postImg.jpeg", fileBody)
        var bitmap = MediaStore.Images.Media.getBitmap(contentResolver, realUri)
        val mediaType = "multipart/form-data".toMediaType()
        val petName: String = petName.text.toString()
        val textName = petName.toRequestBody(mediaType)
        val petAge: String = petAge.text.toString()
        val textAge = petAge.toRequestBody(mediaType)
        val petBirth: String = petBirth.text.toString()
        val textBirth= petBirth.toRequestBody(mediaType)

        server.PetRegister(textName,textAge,textBirth,multipartBody!!).enqueue(object :
            Callback<PetResponseDTO?> {
            override fun onResponse(call: Call<PetResponseDTO?>?, response: Response<PetResponseDTO?>) {
//                Toast.makeText(context, "File Uploaded Successfully...", Toast.LENGTH_LONG).show();
                Log.d("레트로핏 결과2", "" + response.body().toString())
                // 다른 액티비티로 intent
                val intent = Intent(context, VectorChoicePet::class.java)
                // 인텐트에 데이터 추가
                // 액티비티 시작
                context.startActivity(intent)
            }

            override fun onFailure(call: Call<PetResponseDTO?>?, t: Throwable) {
                Toast.makeText(context, "통신 실패", Toast.LENGTH_SHORT).show()
                Log.d("에러", t.message!!)
            }
        })
    }

}



