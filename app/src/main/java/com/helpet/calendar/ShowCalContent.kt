package com.helpet.calendar

import android.app.Dialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.util.Pair
import com.google.android.material.datepicker.MaterialDatePicker
import com.helpet.R
import com.helpet.databinding.ActivityShowCalContentBinding
import com.helpet.databinding.FragmentCalendarMainBinding
import com.helpet.vector.HomeActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create
import java.text.SimpleDateFormat
import java.util.Calendar

class ShowCalContent : AppCompatActivity() {

    private lateinit var newTitleText : String
    private lateinit var newMemoText : String

//    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
//        override fun handleOnBackPressed() {
//            val intent= Intent(this@ShowCalContent, HomeActivity::class.java)
//            startActivity(intent)
//            Log.e(ContentValues.TAG, "뒤로가기 클릭")
//            // 뒤로가기 시 실행할 코드
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityShowCalContentBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        this.onBackPressedDispatcher.addCallback(this, onBackPressedCallback) //위에서 생성한 콜백 인스턴스 붙여주기


        //세션 유지_ userId 불러오기
        val sharedPreferences = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("userId", "") ?: ""

        val title = intent.getStringExtra("title")
        var startDate = intent.getStringExtra("startDate")
        var endDate = intent.getStringExtra("endDate")
        val description = intent.getStringExtra("description")
        val calIdx = intent.getIntExtra("calIdx", 0 )

        newTitleText = title!!
        newMemoText= description!!

        binding.ShowCalDate.text = "${startDate}~${endDate}"
        binding.showEdtTitle.setText(title)
        binding.ShowCalMemo.setText(description)
        val titletextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // 변경 전 텍스트 관찰
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 텍스트 변경 중 관찰
//                val newText = s.toString()
                // 변경된 텍스트를 처리하는 코드를 여기에 추가
            }

            override fun afterTextChanged(s: Editable?) {
                // 변경 후 텍스트 관찰
                newTitleText = s.toString()

            }
        }
        binding.showEdtTitle.addTextChangedListener(titletextWatcher)

        val memotextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // 변경 전 텍스트 관찰
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 텍스트 변경 중 관찰
                val newText = s.toString()
                // 변경된 텍스트를 처리하는 코드를 여기에 추가
            }

            override fun afterTextChanged(s: Editable?) {
                // 변경 후 텍스트 관찰
                newMemoText = s.toString()
            }
        }
        binding.showEdtTitle.addTextChangedListener(memotextWatcher)

// startDate를 파싱하고 1일을 더하여 다음 날로 설정
        val startDateCalendar = Calendar.getInstance()
        startDateCalendar.time = SimpleDateFormat("yyyy-MM-dd").parse(startDate!!)
        startDateCalendar.add(Calendar.DAY_OF_MONTH, 1) // 1일 더하기

// endDate를 파싱하고 1일을 더하여 다음 날로 설정
        val endDateCalendar = Calendar.getInstance()
        endDateCalendar.time = SimpleDateFormat("yyyy-MM-dd").parse(endDate!!)
        endDateCalendar.add(Calendar.DAY_OF_MONTH, 1) // 1일 더하기

        val startDateInMillis = startDateCalendar.timeInMillis
        val endDateInMillis = endDateCalendar.timeInMillis


        binding.showSelectCal.setOnClickListener {
            val dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("등록할 기간을 선택해주세요.")
                .setSelection(
                    Pair(
                        MaterialDatePicker.thisMonthInUtcMilliseconds(),
                        MaterialDatePicker.todayInUtcMilliseconds()
                    )
                )
                .setSelection(
                    Pair(
                        startDateInMillis,
                        endDateInMillis
                    )
                )
                .build()

            dateRangePicker.show(supportFragmentManager, "date_picker")
            dateRangePicker.addOnPositiveButtonClickListener { selection ->
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = selection?.first ?: 0
                startDate = SimpleDateFormat("yyyy-MM-dd").format(calendar.time).toString()
                Log.d("start", startDate!!)

                calendar.timeInMillis = selection?.second ?: 0
                endDate = SimpleDateFormat("yyyy-MM-dd").format(calendar.time).toString()
                Log.d("end", endDate!!)

                binding.ShowCalDate.text = dateRangePicker.headerText
//                                    getInstance(customerNumString, startDate, endDate, itemNumstring)
            }
        }


        val updateServer = CalRetrofitInterface.retrofit3.create(CalendarEditService::class.java)
        val deleteServer = CalRetrofitInterface.retrofit3.create(CalendarDeleteService::class.java)

        binding.showCalDelete.setOnClickListener {
            Log.d("cal_idx", calIdx.toString())
            deleteServer.calendarDelete(calIdx).enqueue(object :Callback<DeleteScheduleDTO>{
                override fun onResponse(
                    call: Call<DeleteScheduleDTO>,
                    response: Response<DeleteScheduleDTO>
                ) {
                    Log.d("일정 삭제", response.body().toString())
                    Toast.makeText(this@ShowCalContent, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
//                    onBackPressedDispatcher.onBackPressed()
                    onBackPressedDispatcher.onBackPressed()


                }

                override fun onFailure(call: Call<DeleteScheduleDTO>, t: Throwable) {
                    Log.d("에러", t.message.toString())
                }

            })
        }


        binding.showCalEdit.setOnClickListener {
            updateServer.CalendarUpdate(calIdx, userId, startDate, endDate, newTitleText , newMemoText).enqueue(object : Callback<UpdateScheduleDTO?>{
                override fun onResponse(
                    call: Call<UpdateScheduleDTO?>,
                    response: Response<UpdateScheduleDTO?>
                ) {
                    val result = response.body()
                    Log.d("result", result.toString())
                    if (result?.status == true ){
                        Toast.makeText(this@ShowCalContent, "수정 완료되었습니다.", Toast.LENGTH_SHORT).show()
//                        onBackPressedDispatcher.onBackPressed()
                        onBackPressedDispatcher.onBackPressed()

                    }
                }
                override fun onFailure(call: Call<UpdateScheduleDTO?>, t: Throwable) {
                    Log.d("에러", t.message!!)

                }
            })
        }

    }
}