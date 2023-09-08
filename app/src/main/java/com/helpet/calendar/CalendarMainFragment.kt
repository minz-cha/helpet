package com.helpet.calendar

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.helpet.R
import com.helpet.databinding.FragmentCalendarMainBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.*
import java.util.*



class CalendarMainFragment : Fragment() {

    private lateinit var userId: String
    private var dialog: Dialog? = null
    private lateinit var calAdapter: CalendarAdapter

    private lateinit var scheduleList: MutableList<Schedule>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        //세션 유지_ userId 불러오기
        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        userId = sharedPreferences.getString("userId", "") ?: ""

        val binding = FragmentCalendarMainBinding.inflate(inflater, container, false)

        dialog = Dialog(requireContext())    // Dialog 초기화
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE) // 타이틀 제거
        dialog!!.setContentView(R.layout.activity_calendar_dialog) // xml 레이아웃 파일과 연결

        // CalendarView의 OnDateChangeListener 설정
        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.set(year, month, dayOfMonth)

            // 현재 선택된 날짜의 년도 가져오기
            val selectedYear = selectedCalendar.get(Calendar.YEAR)
            // 현재 선택된 날짜의 월 가져오기 (0부터 시작하므로 1을 더해줌)
            val selectedMonth = selectedCalendar.get(Calendar.MONTH) + 1
            // 현재 선택된 날짜의 일 가져오기
            val selectedDay = selectedCalendar.get(Calendar.DAY_OF_MONTH)

            val totalDay =
                "$selectedYear-${String.format("%02d", selectedMonth)}-${String.format("%02d", selectedDay)}"

            val mserver = CalRetrofitInterface.retrofit3.create(CalendarMonthService::class.java)
            mserver.selMonthSchedule(userId, selectedMonth)
                .enqueue(object : Callback<MonthlyScheduleDTO> {
                    override fun onResponse(
                        call: Call<MonthlyScheduleDTO>,
                        response: Response<MonthlyScheduleDTO>
                    ) {
                        Log.d("해당 월별", response.body().toString())
                        val monthlyScheduleDTO = response.body()
                        val monthlySchedules = monthlyScheduleDTO?.result

                        val dateTitle = dialog!!.findViewById<TextView>(R.id.textView7)
                        val textView6 = dialog!!.findViewById<TextView>(R.id.textView6)
                        val noCal = dialog!!.findViewById<TextView>(R.id.noCal)
                        scheduleList = mutableListOf() // scheduleListA 초기화

                        monthlySchedules?.let {
                            // result 배열을 date 값의 오름차순으로 정렬
                            Collections.sort(it) { a, b -> a.startdate.compareTo(b.startdate) }
                            // 선택된 날짜와 같은 날짜의 값만 리스트에 보여줌
                            for (schedule in monthlySchedules) {
                                Log.d("Date", schedule.startdate)
                                Log.d("totalday", totalDay)
                                // 시작일과 종료일 사이에 현재 선택된 날짜가 있는지 확인
                                if (totalDay >= schedule.startdate && totalDay <= schedule.enddate) {
                                    Log.d("해당 날짜에 대한 값 추출", schedule.title)
                                    val scheduleItem =
                                        Schedule(schedule.startdate, schedule.enddate, schedule.title, schedule.description, schedule.cal_idx)
                                    scheduleList.add(scheduleItem)
                                } else {
                                    Log.d("date 다름", "error")
                                }
                            }
                            dialog!!.show()

                            dateTitle.text = "${year}년 ${month + 1}월 ${dayOfMonth}일"
                            if (scheduleList.isEmpty()) {
                                textView6.isVisible = true
                                noCal.isVisible = true
                            } else {
                                textView6.isVisible = false
                                noCal.isVisible = false
                            }

                            val recyclerview = dialog!!.findViewById<RecyclerView>(R.id.dialogRecyclerView)

                            calAdapter = CalendarAdapter(
                                scheduleList.map { it.startDate!! },
                                scheduleList.map { it.endDate!! },
                                scheduleList.map { it.title },
                                scheduleList.map { it.description },
                                scheduleList.map { it.cal_idx }
                            )
                            recyclerview.layoutManager = LinearLayoutManager(dialog!!.context)
                            recyclerview.adapter = calAdapter

                            calAdapter.itemClick = object : CalendarAdapter.ItemClick {
                                override fun onClick(view: View) {
                                   dialog!!.dismiss()
                                }
                            }

                            val registerCal =
                                dialog!!.findViewById<Button>(R.id.RegisterCal)  // Dialog의 뷰에서 버튼 찾기
                            registerCal.setOnClickListener {
                                val intent = Intent(requireActivity(), PlanMemo::class.java)
                                intent.putExtra("selectedDay", totalDay)
                                startActivityForResult(intent, 200)
                                dialog!!.dismiss()
                            }
                        }
                    }

                    override fun onFailure(call: Call<MonthlyScheduleDTO>, t: Throwable) {
                        Log.d("에러", t.message!!)
                    }
                })
        }

        return binding.root
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 200 && resultCode == AppCompatActivity.RESULT_OK) {

//            if (data != null) {
//                val startDate = data.getStringExtra("startDate")
//                val endDate = data.getStringExtra("endDate")
//                val title = data.getStringExtra("title")
//                val description = data.getStringExtra("description")
//
//                val textView6 = dialog!!.findViewById<TextView>(R.id.textView6)
//                val noCal = dialog!!.findViewById<TextView>(R.id.noCal)
//
//                textView6.isVisible = false
//                noCal.isVisible = false
//
//                val addItem = Schedule(startDate, endDate, title!!, description!!)
//                scheduleList.add(addItem)
//                val insertedPosition = scheduleList.size - 1 // 추가된 아이템의 위치
//                calAdapter.notifyItemInserted(insertedPosition)
        }
    }


}





