package com.helpet.vector


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.helpet.R
import com.helpet.R.layout.activity_vector_choice_pet
import kotlinx.android.synthetic.main.activity_choice_mypet.*
import kotlinx.android.synthetic.main.activity_my_pet.*
import kotlinx.android.synthetic.main.activity_vector_choice_pet.*
import kotlinx.android.synthetic.main.activity_vector_choice_pet.back
import retrofit2.Call
import retrofit2.Response
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.*
import android.widget.ImageView
import androidx.core.content.ContextCompat



class VectorChoicePet : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vector_choice_pet)
        // SharedPreferences 객체 생성
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        // 유저아이디 데이터 읽기
        val value = sharedPreferences.getString("userId", "null")

        Log.d("value",value!!)

        back.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }


        //유저가 이미 저장해둔 반려동물 정보 가져오는 데이터 값들
        val textuser = value.toString()
        val server3=  RetrofitApi2.retrofit2.create(GetPetService::class.java)

        server3.getPetRegister(textuser).enqueue(object :retrofit2.Callback<petListResponseDTO>{

            @SuppressLint("SetTextI18n")
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(call: Call<petListResponseDTO?>?, response: Response<petListResponseDTO?>){
                Log.d("반려동물 리스트", "" + response.body().toString())
                Log.d("개수", response.body()?.result?.size!!.toString())
                // 서버에서 가져온 데이터의 개수만큼 반복문을 실행합니다.
//                val agepet = response.body()?.result?.get(0)?.petAge
//                val birthpet = response.body()?.result?.get(0)?.petBirth
//                val imgpet = response.body()?.result?.get(0)?.petImg
//                val namepet = response.body()?.result?.get(0)?.petName
//
//                val choicePetName= findViewById<TextView>(R.id.choicePetName)
//                choicePetName.text = namepet

                for (i in 0 until (response.body()?.result?.size!!)) {



                    val agepet = response.body()?.result?.get(i)?.petAge
                    val birthpet = response.body()?.result?.get(i)?.petBirth
                    val imgpet = response.body()?.result?.get(i)?.petImg
//                    val imgpet2 = stringToBitmap(imgpet!!)
                    val namepet = response.body()?.result?.get(i)?.petName



                    petLayout.addView(createLayout(namepet!!, agepet!!, birthpet!!))

                    //리스트가 눌리면 해당 동물 종이 고양이인지 강아지인지 같이 넘겨줌
                    petLayout.setOnClickListener {
                        val intent = Intent(applicationContext, ChoicePetSpecies::class.java)
                        startActivity(intent)
                    }


                }

            }
            override fun onFailure(call: Call<petListResponseDTO>, t: Throwable) {
                Log.d("에러", t.message!!)
            }
        })


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

    fun createLayout(namepet: String, agepet :Int, birthpet:String ) :View{
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layout = inflater.inflate(R.layout.activity_my_pet, null) as LinearLayout

        val choicePetImg = layout.findViewById<ImageView>(R.id.choicePetImg)
        val choicePetName = layout.findViewById<TextView>(R.id.choicePetName)
        val choicePetAge = layout.findViewById<TextView>(R.id.choicePetAge)
        val choicePetBirth = layout.findViewById<TextView>(R.id.choicePetBirth)
        val choicePet = layout.findViewById<ImageView>(R.id.choicePet)

        // 설정할 내용들을 직접 지정해주세요.
        choicePetImg.setImageResource(R.drawable.petex)
        choicePetName.text = namepet
        choicePetAge.text = "나이 : $agepet 살"
        choicePetBirth.text = "생일: $birthpet "
        choicePet.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        choicePet.setImageResource(R.drawable.petchoice)
        choicePet.setPadding(0, 0, 0, 0)

        return layout

    }
}