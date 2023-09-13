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
import kotlin.collections.List
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

        val name = intent.getStringExtra("namepet")
        val birth = intent.getStringExtra("birthpet")
        val gender = intent.getStringExtra("genderpet")
        val age = intent.getIntExtra("agepet", 0)

        val dbHelper = PetHelper(this)
        val petImageByteArray = dbHelper.getPetImage(name!!, birth!!, gender!!, age)

        if (petImageByteArray != null) {
            // 조회된 이미지가 있다면 이미지뷰에 표시
            val petImageBitmap =
                BitmapFactory.decodeByteArray(petImageByteArray, 0, petImageByteArray.size)
            binding.myPetImg.setImageBitmap(petImageBitmap)
        } else {
            // 조회된 이미지가 없다면 기본 이미지 또는 처리할 내용을 여기에 추가
        }
//        binding.myPetImg.setImageBitmap(stringToBitmap(petimg))
        binding.infName.text = name
        binding.infAge.text = "나이: $age 살"
        binding.infBirth.text = "생일: $birth"
        binding.infGender.text = "성별: $gender"


        // SharedPreferences 객체 생성
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        // 유저아이디 데이터 읽기
        val value = sharedPreferences.getString("userId", "null")


        //유저가 이미 저장해둔 반려동물 진단기록 정가져오는 데이터 값들
        val userId = value.toString()
        val petName = name.toString()
        val mpserver = RetrofitApi2.retrofit2.create(MyPetVectService::class.java)

        // 진단 결과 데이터베이스 초기화
        val vectHelper = VectHelper(applicationContext)
        val db = dbHelper.writableDatabase
        // 데이터베이스 초기화
        vectHelper.onUpgrade(db, 4, 5)

        Log.d("dbfirst", vectHelper.getAllVectorInfo().toString())

        mpserver.myPetService(userId, petName).enqueue(object : retrofit2.Callback<MypetVectDTO> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<MypetVectDTO>, response: Response<MypetVectDTO>) {
                Log.d("진단기록 리스트", "" + response.body().toString())

                val petname = response.body()?.petName
                val petage = response.body()?.petAge
                val petbirth = response.body()?.petBirth

                //데이터베이스에 저장되어있는 반려동물들 이름 추출
                val getNames: List<String> = vectHelper.getDistinctPetNames()
                Log.d("getNames", getNames.toString())

                if (response.body() == null) {
                    binding.noVectResult.isVisible = true
                } else if (response.body() != null) {
                    // 서버에서 가져온 데이터의 개수만큼 반복문을 실행합니다
                    for (i in 0 until (response.body()?.result?.size!!)) {

                        val vectimg = response.body()?.result?.get(i)?.vectImg
                        val vectname = response.body()?.result?.get(i)?.vectName
                        val vectdate = response.body()?.result?.get(i)?.vectDate
                        val vectprob = response.body()?.result?.get(i)?.vectProb


                        //이미 디비에 있으면 저장 안함
                        if (getNames.isEmpty()) {
                            vectHelper.insertVectData(petname!!, petage!!, petbirth!!, convertImageStringToByteArray(vectimg!!)!!, vectname!!.joinToString(), vectdate!!, vectprob!!)
                            Log.d("getDb", vectHelper.getAllVectorInfo().toString())
                        } else {
                            if (petname!! in getNames) {
                                Log.d("디비에 이미 있음", "똑같음")
                            } else {
                                // VectHelper 데이터베이스에 데이터 저장
                                val success = vectHelper.insertVectData(petname, petage!!, petbirth!!, convertImageStringToByteArray(vectimg!!)!!, vectname!!.joinToString(), vectdate!!, vectprob!!)
                                if (success) {
                                    // 저장에 성공한 경우
                                    Log.d("데이터 저장 성공", "성공")
                                    Log.d("getDb", vectHelper.getAllVectorInfo().toString())
                                } else {
                                    // 저장에 실패한 경우
                                    Log.d("데이터 저장 실패", "실패")
                                }
                            }
                        }


                    binding.vectorInfL.addView(
                        createLayout(
                            vectname!!,
                            vectdate!!,
                            vectprob!!,
                            petname,
                            petage!!,
                            petbirth!!
                        )
                    )
                }
                }

            }

            override fun onFailure(call: Call<MypetVectDTO>, t: Throwable) {
                Log.d("에러", t.message!!)
            }
        })


        val delservice = RetrofitApi2.retrofit2.create(PetDelService::class.java)

        binding.petOptions.setOnClickListener { view ->
            val popupMenu = PopupMenu(this, view) // 팝업 메뉴 생성

            // 메뉴 아이템 추가
            popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.deletePet -> {
                        // 메뉴 아이템 1 클릭 처리
                        delservice.petDelete(userId, petName)
                            .enqueue(object : retrofit2.Callback<petDeleteDTO> {
                                @SuppressLint("SetTextI18n")
                                override fun onResponse(
                                    call: Call<petDeleteDTO>,
                                    response: Response<petDeleteDTO>
                                ) {
                                    if (response.isSuccessful) {
                                        Log.d("반려동물 삭제하기", "" + response.body()?.status)
                                        Toast.makeText(applicationContext, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
                                        onBackPressedDispatcher.onBackPressed()
                                        finish()
                                        // 서버로부터 성공적으로 응답 받았을 때의 처리

//                                        // VectorMain 프래그먼트를 찾아서 참조
//                                        val fragmentManager = supportFragmentManager
//                                        val vectorMainFragment = fragmentManager.findFragmentById(R.id.petContainer) as VectorMain
//
//// VectorMain 프래그먼트를 삭제하고 다시 추가 (재생성)
//                                        val transaction = fragmentManager.beginTransaction()
//                                        transaction.remove(vectorMainFragment)
//                                        transaction.add(R.id.fl_container, vectorMainFragment)
//                                        transaction.addToBackStack(null) // 뒤로가기 스택에 추가
//                                        transaction.commit()

                                    } else {
                                        Log.d("반려동물 삭제하기", "서버 응답 오류:" + response.message() )
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

                    else -> false
                }
            }
            popupMenu.show() // 팝업 메뉴 표시
        }
    }



    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n", "InflateParams")
    fun createLayout(vectname: List<String>, vectdate :String, vectprob: Double, petname:String, petage:Int, petbirth:String ) : View {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layout = inflater.inflate(R.layout.activity_vect_sub_layout, null) as LinearLayout

        Log.d("vectprob", vectprob.toString())
        val vectinfdate = layout.findViewById<TextView>(R.id.vectorInfDate)
        val vectinfname = layout.findViewById<TextView>(R.id.vectorInfName)
        val vectinfprob = layout.findViewById<TextView>(R.id.vectorInfProb)

        vectinfdate.text = vectdate
//        vectinfname.text = vectname.toString()
        if(vectprob >= 50.0){
            vectinfname.text = "유증상"
        }
        else{
            vectinfname.text = "무증상"
        }
        vectinfprob.text = "확률: $vectprob"

        layout.setOnClickListener {
            val intent = Intent(applicationContext, MPVectorResult::class.java)
            intent.putExtra("vectdate", vectdate)
            intent.putExtra("vectprob",vectprob)
            intent.putExtra("petname", petname)
            intent.putExtra("petage", petage)
            intent.putExtra("petbirth", petbirth)
            startActivity(intent)

        }

        return layout

    }


    fun convertImageStringToByteArray(imageString: String): ByteArray? {
        return try {
            android.util.Base64.decode(imageString, android.util.Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}