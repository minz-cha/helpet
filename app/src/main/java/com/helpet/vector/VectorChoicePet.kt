package com.helpet.vector


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.helpet.R
import kotlinx.android.synthetic.main.activity_vector_choice_pet.*
import kotlinx.android.synthetic.main.activity_vector_choice_pet.back
import retrofit2.Call
import retrofit2.Response
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.*
import android.widget.ImageView
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_sub_pet.*


class VectorChoicePet : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vector_choice_pet)
        // SharedPreferences 객체 생성
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        // 유저아이디 데이터 읽기
        val value = sharedPreferences.getString("userId", "null")
        Log.d("value",value!!)

        petRegister.setOnClickListener {
            Log.d("hi","hi")
            val intent= Intent(this, PetRegisterActivity::class.java  )
            startActivity(intent)
        }

        back.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
//        petRegister.setOnClickListener {
//            Log.d("hi","hi")
//            val intent= Intent(applicationContext, PetRegisterActivity::class.java  )
//            startActivity(intent)
//        }

        //유저가 이미 저장해둔 반려동물 정보 가져오는 데이터 값들
        val server3=  RetrofitApi2.retrofit2.create(GetPetService::class.java)

        server3.getPetRegister(value).enqueue(object :retrofit2.Callback<petListResponseDTO>{
            @SuppressLint("SetTextI18n")
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(call: Call<petListResponseDTO?>?, response: Response<petListResponseDTO?>){
                Log.d("반려동물 리스트", "" + response.body().toString())
                Log.d("개수", response.body()?.result?.size!!.toString())

                // 서버에서 가져온 데이터의 개수만큼 반복문을 실행합니다
                for (i in 0 until (response.body()?.result?.size!!)) {

                    val imgpet = response.body()?.result?.get(i)?.petImg
                    val namepet = response.body()?.result?.get(i)?.petName
                    val genderpet = response.body()?.result?.get(i)?.petGender
                    val birthpet = response.body()?.result?.get(i)?.petBirth
                    val agepet = response.body()?.result?.get(i)?.petAge
                    val speciespet = response.body()?.result?.get(i)?.petSpecies
                    Log.d("imgpet", imgpet.toString())


                    val choiceLayout = createLayout(imgpet!!, namepet!!,speciespet!!, birthpet!!, agepet!!)
                    petLayout.addView(choiceLayout)

                    petRegister.setOnClickListener {
                        Log.d("hi","hi")
                        val intent= Intent(applicationContext, PetRegisterActivity::class.java  )
                        startActivity(intent)
                    }

                }

            }

            override fun onFailure(call: Call<petListResponseDTO>, t: Throwable) {
                Log.d("에러", t.message!!)
                petRegister.setOnClickListener {
                    val intent= Intent(applicationContext, PetRegisterActivity::class.java  )
                    startActivity(intent)
                }
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

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    fun createLayout(imgpet:String, name: String, species: String, birth: String, age: Int): View {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layout = inflater.inflate(R.layout.activity_sub_pet, null) as LinearLayout

        val petimg = stringToBitmap(imgpet)


        val petImg = layout.findViewById<ImageView>(R.id.choicePetImg)
        val petName = layout.findViewById<TextView>(R.id.choicePetName)
        val petAge = layout.findViewById<TextView>(R.id.choicePetAge)
        val petBirth = layout.findViewById<TextView>(R.id.choicePetBirth)

        petImg?.setImageBitmap(petimg)
        petName?.text = name
        petAge?.text = "나이: $age 살"
        petBirth?.text = "생일: $birth"
        choicePet?.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        choicePet?.setImageResource(R.drawable.petchoice)
        choicePet?.setPadding(0, 0, 0, 0)

        layout.setOnClickListener {
            val intent = Intent(applicationContext, VectorCamera::class.java)
            intent.putExtra("namepet", name)
            intent.putExtra("speciespet", species)
            startActivity(intent)
        }

        return layout
    }
}