package com.helpet.vector

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.helpet.R
import kotlinx.android.synthetic.main.activity_pet_inf.*
import kotlinx.android.synthetic.main.activity_pet_inf.back
import kotlinx.android.synthetic.main.activity_pet_register.*
import kotlinx.android.synthetic.main.activity_vector_choice_pet.*
import retrofit2.Call
import retrofit2.Response
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.*

class PetInfActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_inf)

        val name = intent.getStringExtra("namepet")
        val birth = intent.getStringExtra("birthpet")
        val gender = intent.getStringExtra("genderpet")
        val age = intent.getIntExtra("agepet", 0)

        Log.d("name", name!!)
        Log.d("age", age.toString())
        Log.d("birth", birth!!)
        Log.d("gender", gender!!)

        infName.text = name
        infAge.text = "나이: $age 살"
        infBirth.text = "생일: $birth"
        infGender.text = "성별: $gender"



        // SharedPreferences 객체 생성
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        // 유저아이디 데이터 읽기
        val value = sharedPreferences.getString("userId", "null")

        Log.d("value",value!!)

        back.setOnClickListener {
            val intent = Intent(this, ChoiceMyPetF::class.java)
            startActivity(intent)
        }


        //유저가 이미 저장해둔 반려동물 진단기록 정가져오는 데이터 값들
        val userId = value.toString()

        val mpserver=  RetrofitApi2.retrofit2.create(MyPetVectService::class.java)

        mpserver.myPetService(name,userId).enqueue(object :retrofit2.Callback<MpVectResponseDTO>{
            @SuppressLint("SetTextI18n")
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(call: Call<MpVectResponseDTO?>?, response: Response<MpVectResponseDTO?>){
                Log.d("진단기록 리스트", "" + response.body().toString())
                Log.d("개수", response.body()?.result?.size!!.toString())

                val petname = response.body()?.petName
                val petage = response.body()?.petAge
                val petbirth = response.body()?.petBirth
                // 서버에서 가져온 데이터의 개수만큼 반복문을 실행합니다
                for (i in 0 until (response.body()?.result?.size!!)) {

                    val vectname = response.body()?.result?.get(i)?.vectName
                    val vectdate = response.body()?.result?.get(i)?.vectDate
                    val vectprob = response.body()?.result?.get(i)?.vectProb


                    vectorInfL.addView(createLayout(vectname!!, vectdate!!, vectprob!!, petname!!, petage!!, petbirth!!))

                }
            }
            override fun onFailure(call: Call<MpVectResponseDTO>, t: Throwable) {
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

    @SuppressLint("SetTextI18n", "InflateParams")
    fun createLayout(vectname: String, vectdate :String, vectprob: Double, petname:String, petage:Int, petbirth:String ) : View {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layout = inflater.inflate(R.layout.activity_vect_sub_layout, null) as LinearLayout

        val vectinfdate = layout.findViewById<TextView>(R.id.vectorInfDate)
        val vectinfname = layout.findViewById<TextView>(R.id.vectorInfName)
        val vectinfprob = layout.findViewById<TextView>(R.id.vectorInfProb)

        // 설정할 내용들을 직접 지정해주세요.
        vectinfdate.text = vectdate
        vectinfname.text = vectname
        vectinfprob.text = "확률: $vectinfprob"

        layout.setOnClickListener {
            val intent = Intent(this, MPVectorResult::class.java)
            intent.putExtra("vectdate", vectdate)
            intent.putExtra("vectname", vectname)
            intent.putExtra("vectprob",vectprob)
            intent.putExtra("petname", petname)
            intent.putExtra("petage", petage)
            intent.putExtra("petbirth", petbirth)
            startActivity(intent)
        }


        return layout

    }

}