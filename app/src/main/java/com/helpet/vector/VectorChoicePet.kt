package com.helpet.vector

import com.helpet.R
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_vector_choice_pet.*
import kotlinx.android.synthetic.main.custom_petlist_item.*
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.*
import javax.security.auth.callback.Callback

class VectorChoicePet : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vector_choice_pet)

//유저가 이미 저장해둔 반려동물 정보 가져오는 데이터 값들
        val userId = "qwer"
        val textuser = userId.toRequestBody()
        val server3=  RetrofitApi2.retrofit2.create(GetPetService::class.java)

        server3.getPetRegister(textuser).enqueue(object :retrofit2.Callback<PetResponseDto>{
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(call: Call<PetResponseDto?>?, response: Response<PetResponseDto?>){
                Log.d("반려동물 리스트", "" + response.body().toString())

                val statuspet = response.body()?.status
                val imgpet = response.body()?.petList?.get(0)?.petImg
                val imgpet2 = stringToBitmap(imgpet!!)
                val namepet = response.body()?.petList?.get(0)?.petName
                val speciespet = response.body()?.petList?.get(0)?.petSpecies
                val agepet = response.body()?.petList?.get(0)?.petAge
                val birthpet = response.body()?.petList?.get(0)?.petBirth
                val genderpet = response.body()?.petList?.get(0)?.petGender

            }
            override fun onFailure(call: Call<PetResponseDto>, t: Throwable) {
                Log.d("에러", t.message!!)
            }
        })




        //반려동물 등록 후 가져온 데이터들 : 이미지 아직 안함
//        val statuspet = intent.getStringExtra("statuspet")
//        val namepet = intent.getStringExtra("namepet")
//        val speciespet = intent.getStringExtra("speciespet")
//        val agepet = intent.getIntExtra("agepet", 0)
//        val birthpet = intent.getStringExtra("birthpet")
//        val genderpet = intent.getStringExtra("genderpet")

        petRegister.setOnClickListener {
            val intent= Intent(this, PetRegisterActivity::class.java  )
            startActivity(intent)
        }
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