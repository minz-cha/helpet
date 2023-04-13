package com.helpet.calendar


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.helpet.R
import kotlinx.android.synthetic.main.activity_calendar.*
import java.text.SimpleDateFormat
import java.util.*

class CalendarMain : AppCompatActivity() {
    private val scheduleList = mutableListOf<Schedule>()
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        recyclerView = findViewById(R.id.calendarRecyclerview)
        recyclerView.adapter = ScheduleAdapter(scheduleList)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("MM월 dd일", Locale.getDefault())
        val currentDate = dateFormat.format(calendar.time)

        val tvtodayDate = findViewById<TextView>(R.id.tvtodayDate)
        tvtodayDate.text = currentDate

        calendar_DialogButton.setOnClickListener {
            val intent = Intent(this, PlanMemo::class.java)
            intent.putExtra("date", currentDate)
            Log.d("test", "id : ")
            startActivityForResult(intent, 100)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == RESULT_OK) {
            // 갱신하기
            Log.d("test", "DB 갱신")
            Log.d("test", "title : ${data!!.getStringExtra("title")}")
            // recycleView 추가
        }
    }
}