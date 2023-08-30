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
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.multidex.MultiDex
import com.helpet.R
import com.helpet.databinding.ActivityPetInfBinding
import retrofit2.Call
import retrofit2.Response
import retrofit2.create
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.math.log

class PetInfActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPetInfBinding

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPetInfBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mpChoiceBack.setOnClickListener {
            finish()
        }

        MultiDex.install(this)

        val petimg = intent.getStringExtra("imgpet")
        val name = intent.getStringExtra("namepet")
        val birth = intent.getStringExtra("birthpet")
        val gender = intent.getStringExtra("genderpet")
        val age = intent.getIntExtra("agepet", 0)

        Log.d("name", name!!)
        Log.d("age", age.toString())
        Log.d("birth", birth!!)
        Log.d("gender", gender!!)

        binding.myPetImg.setImageBitmap(stringToBitmap(petimg))
        binding.infName.text = name
        binding.infAge.text = "나이: $age 살"
        binding.infBirth.text = "생일: $birth"
        binding.infGender.text = "성별: $gender"



        // SharedPreferences 객체 생성
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        // 유저아이디 데이터 읽기
        val value = sharedPreferences.getString("userId", "null")

        Log.d("value",value!!)



        //유저가 이미 저장해둔 반려동물 진단기록 정가져오는 데이터 값들
        val userId = value.toString()
        Log.d("ellen", userId)
        val petName = name.toString()
        Log.d("몽이", petName)
        val mpserver=  RetrofitApi2.retrofit2.create(MyPetVectService::class.java)


        mpserver.myPetService(userId, petName).enqueue(object :retrofit2.Callback<MypetVectDTO>{
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<MypetVectDTO>, response: Response<MypetVectDTO>) {
                Log.d("진단기록 리스트", "" + response.body().toString())

                val petname = response.body()?.petName
                val petage = response.body()?.petAge
                val petbirth = response.body()?.petBirth


                if (response.body() == null){
                    binding.noVectResult.isVisible= true
                }
                else if(response.body()!= null) {
                    // 서버에서 가져온 데이터의 개수만큼 반복문을 실행합니다
                    for (i in 0 until (response.body()?.result?.size!!)) {

                        val vectimg = response.body()?.result?.get(i)?.vectImg
                        val vectname = response.body()?.result?.get(i)?.vectName
                        val vectdate = response.body()?.result?.get(i)?.vectDate
                        val vectprob = response.body()?.result?.get(i)?.vectProb


                        binding.vectorInfL.addView(createLayout(stringToBitmap(vectimg)!!, vectname!!, vectdate!!, vectprob!!, petname!!, petage!!, petbirth!!))

                    }
                }


            }

            override fun onFailure(call: Call<MypetVectDTO>, t: Throwable) {
                Log.d("에러", t.message!!)
            }



        })

        val delservice = RetrofitApi2.retrofit2.create(PetDelService::class.java)

        binding.petOptions.setOnClickListener { view->
            val popupMenu = PopupMenu(this, view) // 팝업 메뉴 생성

            // 메뉴 아이템 추가
            popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.deletePet -> {
                        // 메뉴 아이템 1 클릭 처리
                        Toast.makeText(this, "삭제되었습니다.", Toast.LENGTH_SHORT).show()

                        delservice.petDelete(userId, petName).enqueue(object :retrofit2.Callback<petDeleteDTO> {
                            @SuppressLint("SetTextI18n")
                            override fun onResponse(call: Call<petDeleteDTO>, response: Response<petDeleteDTO>) {
                                if (response.isSuccessful) {
                                    Log.d("반려동물 삭제하기", "" + response.body()?.status)
                                    val intent = Intent(applicationContext, ChoiceMyPetF::class.java)
                                    startActivity(intent)
                                    // 서버로부터 성공적으로 응답 받았을 때의 처리
                                } else {
                                    Log.d("반려동물 삭제하기", "서버 응답 오류: " + response.code())
                                    // 서버 응답 오류에 대한 처리
                                }
                            }

                            override fun onFailure(call: Call<petDeleteDTO>, t: Throwable) {
                                Log.d("반려동물 삭제하기", "서버 요청 실패: " + t.message)
                                // 서버 요청 실패에 대한 처리
                            }
                        })

                        true // 클릭 처리 완료
                    }

                    // ...
                    else -> false
                }
            }
            popupMenu.show() // 팝업 메뉴 표시
        }


    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n", "InflateParams")
    fun createLayout(vectImg: Bitmap, vectname: String, vectdate :String, vectprob: Double, petname:String, petage:Int, petbirth:String ) : View {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layout = inflater.inflate(R.layout.activity_vect_sub_layout, null) as LinearLayout

        Log.d("vectprob", vectprob.toString())
        val vectinfdate = layout.findViewById<TextView>(R.id.vectorInfDate)
        val vectinfname = layout.findViewById<TextView>(R.id.vectorInfName)
        val vectinfprob = layout.findViewById<TextView>(R.id.vectorInfProb)

        vectinfdate.text = vectdate
        vectinfname.text = vectname
        vectinfprob.text = "확률: $vectprob"

        layout.setOnClickListener {
            val intent = Intent(applicationContext, MPVectorResult::class.java)
//            Log.d("넘기자", "넘겨")
            intent.putExtra("vectimg", SerialBitmap.translate(vectImg) )
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
    //string 을  bitmap 형태로 변환하는 메서드
    @RequiresApi(Build.VERSION_CODES.O)
    fun stringToBitmap(data: String?): Bitmap? {
        var bitmap: Bitmap? = null
        val byteArray: ByteArray = Base64.getDecoder().decode(data)
        val stream = ByteArrayInputStream(byteArray)
        bitmap = BitmapFactory.decodeStream(stream)
        return bitmap
    }

    override fun onStop() {
        super.onStop()
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        finish()
    }
}