package com.helpet.vector

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.annotation.LongDef
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import com.helpet.databinding.ActivityVectorCameraBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.SimpleDateFormat
import kotlin.concurrent.thread

class VectorCamera : BaseActivity() {
    var name :List<String> = listOf()
    var symptomProbability = 0.0
    var asymptomaticProbability = 0.0
    var vectContent = ""

    val PERM_STORAGE= 9
    val PERM_CAMERA= 10
    val REQ_CAMERA=11
    val CROP_PICTURE = 2

    private lateinit var binding : ActivityVectorCameraBinding

    private val diseaseList = mutableListOf<DiseaseName>()


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityVectorCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonVector.isEnabled=false
        binding.buttonVector.backgroundTintList = ColorStateList.valueOf(Color.LTGRAY) // 회색으로 설정

        binding.camback.setOnClickListener {
            val intent = Intent(this, VectorChoicePet::class.java)
            startActivity(intent)
            finish()
        }
        requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), PERM_STORAGE)

    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun initViews(){
        binding.cameraBtn.setOnClickListener {
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

    fun loadBitmap(photoUri:Uri):Bitmap?{
        try {
            return if (Build.VERSION.SDK_INT>Build.VERSION_CODES.O_MR1){
                val source= ImageDecoder.createSource(contentResolver,photoUri)
                ImageDecoder.decodeBitmap(source)
            }
            else{
                MediaStore.Images.Media.getBitmap(contentResolver,photoUri)
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
        return null
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
                    binding.buttonVector.isEnabled=true
                    binding.buttonVector.setBackgroundColor(Color.parseColor("#FD9374"))
                    realUri?.let { uri ->
                        var bitmap: Bitmap? = null
                        //카메라에서 찍은 사진을 비트맵으로 변환
                        bitmap = MediaStore.Images.Media.getBitmap(contentResolver, realUri)
                        //이미지뷰에 이미지 로딩
                        binding.cameraBtn.setImageBitmap(bitmap)
                        binding.camTitle.text="촬영 완료"
                        binding.sub1.text="아래의 진단 시작 버튼을\n누르면 진단이 시작됩니다."
                        binding.sub2.isVisible=false
                        binding.sub3.isVisible=false
                        binding.camSubTitle.isVisible=false
                        binding.buttonVector.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FD9374")) // 오렌지색으로 설정
                        binding.ReCam.isVisible = true
                    }


                    binding.buttonVector.setOnClickListener {
                        var bitmap = MediaStore.Images.Media.getBitmap(contentResolver, realUri)

                        UpdatePhoto(SerialBitmap.translate(bitmap),this)
                        Log.d("사진" , SerialBitmap.translate(bitmap).toString())
                        binding.buttonVector.isVisible=false
                        binding.camSubLayout.isVisible=false
                        binding.loadingLayout.isVisible=true
                        binding.vectorProgress.isIndeterminate = true
                        binding.camTitle.text = "진단 중"
                    }
                    binding.ReCam.setOnClickListener {
                        // 기존 이미지 파일 삭제
                        if (realUri != null) {
                            val file = File(getRealPathFromURI(realUri!!))
                            if (file.exists()) {
                                file.delete()
                            }
                        }

                        // 다시 카메라 열기
                        openCamera()
                    }
                }
            }
        }
    }
private val server: VectorService by lazy {
    RetrofitApi.retrofit.create(VectorService::class.java) }

private val server2 = RetrofitApi.retrofit.create(catVectorService::class.java)
//    val service: VectorService = retrofit.create(VectorService::class.java)


    fun UpdatePhoto(byteArray: ByteArray, context: Context) {
        //강아지인지 고양이인지
        val petSpecies = intent.getStringExtra("speciespet")
        Log.d("speciespet", petSpecies!!)
        // SharedPreferences 객체 생성
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        // 유저아이디 데이터 읽기
        val value = sharedPreferences?.getString("userId", "null")
        val namepet = intent.getStringExtra("namepet")

        val fileBody = RequestBody.create("image/*".toMediaTypeOrNull(), byteArray)
        val multipartBody: MultipartBody.Part? =
            MultipartBody.Part.createFormData("postImg", "postImg.jpeg", fileBody)
        var bitmap = MediaStore.Images.Media.getBitmap(contentResolver, realUri)
        val image = SerialBitmap.translate(bitmap)
        //만약 강아지 눈진단이면 if문 활용

        if (petSpecies == "강아지"){
            server.vectorResult(multipartBody!!).enqueue(object : Callback<ResultVectDTO?> {
                override fun onResponse(call: Call<ResultVectDTO?>, response: Response<ResultVectDTO?>) {
//                Toast.makeText(context, "File Uploaded Successfully...", Toast.LENGTH_LONG).show();
                    Log.d("레트로핏 결과2", "" + response.body().toString())

                    for (diseaseName in response.body()?.diseaseNames!!) {
                        diseaseList.add(DiseaseName(diseaseName))
                    }


                    name= response.body()?.diseaseNames!!
                    asymptomaticProbability= response.body()?.asymptomaticProbability!!
                    symptomProbability=response.body()?.symptomProbability!!
//                    val vectContent = response.body()?.vectContent!!
                    // 다른 액티비티로 intent
                    val intent = Intent(context, VectorResult::class.java)
                    // 인텐트에 데이터 추가
                    intent.putExtra("namepet", namepet )
                    intent.putParcelableArrayListExtra("name" ,ArrayList(diseaseList))
                    intent.putExtra("symptomProbability",symptomProbability)
                    intent.putExtra("asymptomaticProbability",asymptomaticProbability )
                    intent.putExtra("vectImg",image)
                    intent.putExtra("vectContent", vectContent)
                    intent.putExtra("value", value)
                    // 액티비티 시작
                    context.startActivity(intent)
                    finish()
                }

                override fun onFailure(call: Call<ResultVectDTO?>, t: Throwable) {
//                    Toast.makeText(context, "통신 실패", Toast.LENGTH_SHORT).show()
                    Log.d("에러", t.message!!)
                }
            })
        }

        //고양이 눈 진단 하면

        else if (petSpecies == "고양이"){

            server2.catvectorResult(multipartBody!!).enqueue(object : Callback<ResultVectDTO?> {
                override fun onResponse(call: Call<ResultVectDTO?>, response: Response<ResultVectDTO?>) {
//                Toast.makeText(context, "File Uploaded Successfully...", Toast.LENGTH_LONG).show();

                    for (diseaseName in response.body()?.diseaseNames!!) {
                        diseaseList.add(DiseaseName(diseaseName))
                    }

                    name= response.body()?.diseaseNames!!
                    asymptomaticProbability= response.body()?.asymptomaticProbability!!
                    symptomProbability=response.body()?.symptomProbability!!
//                    val vectContent = response.body()?.vectContent!!
                    // 다른 액티비티로 intent
                    val intent = Intent(context, VectorResult::class.java)
                    // 인텐트에 데이터 추가
                    intent.putExtra("namepet", namepet )
                    intent.putParcelableArrayListExtra("name" ,ArrayList(diseaseList))
                    intent.putExtra("symptomProbability",symptomProbability)
                    intent.putExtra("asymptomaticProbability",asymptomaticProbability )
                    intent.putExtra("vectImg",image)
                    intent.putExtra("vectContent", vectContent)
                    intent.putExtra("value", value)
                    // 액티비티 시작
                    context.startActivity(intent)
                    finish()
                }

                override fun onFailure(call: Call<ResultVectDTO?>, t: Throwable) {
                    Toast.makeText(context, "통신 실패", Toast.LENGTH_SHORT).show()
                    Log.d("에러", t.message!!)
                }
            })
        }
    }
}