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
            val server = CalRetrofitInterface.retrofit3.create(CalendarService::class.java)
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
//    @FormUrlEncoded
//    @POST("calendar/add")
//    fun CalendarResult(
//        @Field("date") date: String?,
//        @Field("userId") userId: String,
//        @Field("title") title: String,
//        @Field("description") description: String
//    ): Call<CalendarPlanResultDTO?>
//}
