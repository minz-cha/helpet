package com.helpet.calendar


import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.helpet.R
import com.helpet.login.LogResponseDTO
import com.helpet.login.LoginService
import com.helpet.login.RetrofitInterface
import com.helpet.vector.HomeActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.*


class PlanMemo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plan_memo)

        val tvtodayDate = findViewById<TextView>(R.id.tvtodayDate)
        val date = intent.getStringExtra("date")
        val btnClose = findViewById<Button>(R.id.btnClose)
        val edtTitle: EditText = findViewById(R.id.edtTitle)
        val edtPlan: EditText = findViewById(R.id.edtPlan)
        val btnSave: Button = findViewById(R.id.btnSave)

        //일정 등록하기
        tvtodayDate.text = date

        val userId: String = intent.getStringExtra("userId") ?: "" // userId 값을 인텐트로부터 추출


//        val title = edtTitle.text.toString()
//        val description = edtPlan.text.toString()
//        setResult(RESULT_CANCELED)

        btnClose.setOnClickListener {
            finish()
        }


        btnSave.setOnClickListener {
            val title = edtTitle.text.toString()
            val description = edtPlan.text.toString()
//            val intent = Intent(this,MainActivity::class.java)
            if (title.isNotEmpty()) {
                val intent = Intent()
                intent.putExtra("title", title)
                setResult(RESULT_OK, intent)
                finish()
            } else {
                Toast.makeText(this, "제목을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }

            //retrofit 서버 요청 _ date,userId,title,description
            val server = CalRetrofitInterface.retrofit.create(CalendarService::class.java)
//            val schedule = Schedule(date, userId, title, description)
            Log.d("일정 등록", date.toString())
            Log.d("일정 등록", userId)
            Log.d("일정 등록", title)
            Log.d("일정 등록", description)



            server.CalendarResult(date, userId, title, description).enqueue(object : Callback<CalendarPlanResultDTO?> {
                override fun onResponse(
                    call: Call<CalendarPlanResultDTO?>?,
                    response: Response<CalendarPlanResultDTO?>
                ) {
                    val result = response.body()
                    val status = response.body()?.status
                    Log.d("status", status!!)

                    Log.d("retrofit 서버 요청 성공여부", "${result}")
                    if (status.toString() == "success") {
                        val intent = Intent()
                        intent.putExtra("title", title)
                        setResult(RESULT_OK, intent)
                        finish()

                    } else {
                        Toast.makeText(
                            applicationContext,
                            "저장 실패",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                    Log.d("retrofit 일정 추가", "${result}")

                }

                override fun onFailure(
                    call: retrofit2.Call<CalendarPlanResultDTO?>?,
                    t: Throwable
                ) {
                    Log.d("에러", t.message!!)

                }
            })
        }
    }
}
//
//interface CalendarService {
//    @POST("auth/calendar")
//    fun CalendarResult(
//        @Body schedule: Schedule
//    ): retrofit2.Call<CalendarPlanResultDTO?>
//}

interface CalendarService {
    @FormUrlEncoded
    @POST("calendar/add")
    fun CalendarResult(
        @Field("date") date: String?,
        @Field("userId") userId: String,
        @Field("title") title: String,
        @Field("description") description: String
    ): Call<CalendarPlanResultDTO?>
}

//        btnSave.setOnClickListener {
//            val title = edtTitle.text.toString()
//            val content = edtPlan.text.toString()
//            val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
//
//            // SharedPreferences 객체 생성
//            val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
//            // 유저아이디 데이터 읽기
//            val userId = sharedPreferences.getString("userId", "null")
//
//            val server = CalRetrofitInterface.retrofit.create(CalendarService::class.java)
//
//            server.CalendarResult(date, userId!!, title, content).enqueue(object : Callback<CalendarPlanResult?> {
//                override fun onResponse(call: Call<CalendarPlanResult?>?, response: Response<CalendarPlanResult?>) {
//                    val result = response.body()
//                    val success : Boolean? = response.body()?.success
//                    Log.d("retrofit 로그인 성공 유무", "${result}")
//                    if (success.toString() == "true") {
//                        val intent = Intent(applicationContext , HomeActivity::class.java)
//                        startActivity(intent)
//
//                        // SharedPreferences에 userId 저장
//                        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
//                        val editor = sharedPreferences.edit()
//                        editor.putString("userId", userId) // 로그인한 userId를 저장
//                        Log.d("userId 전송 확인", userId)
//
//                        // "로그인 유지" 옵션을 선택한 경우, 체크박스 상태를 저장
//                        val isLoginChecked = checkbox_login.isChecked
//                        editor.putBoolean("isLoginChecked", isLoginChecked)
//
//                        editor.apply()
//                    캘린더 받아와서 정리
//
//                    } else if (success.toString() == "false") {
//                        Toast.makeText(applicationContext, "아이디 혹은 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
//
//                    }
//                    Log.d("retrofit 로그인", "${result}")
//
//                }
//
//
//                override fun onFailure(call: Call<CalendarPlanResult?>, t: Throwable) {
//                    TODO("Not yet implemented")
//                }
//            })
//
//        }
//    }
//}
//
//interface CalendarService {
//    @GET("auth/calendar")
//    fun CalendarResult(
////        @Query("error") error: String,
//        @Query("date") date: String,
//        @Query("userId") userId: String,
//        @Query("title") title: String,
//        @Query("content") content: String
//    ): Call<CalendarPlanResult>
//}