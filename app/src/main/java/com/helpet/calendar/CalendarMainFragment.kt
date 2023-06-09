package com.helpet.calendar

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.helpet.databinding.FragmentCalendarMainBinding
import kotlinx.android.synthetic.main.activity_calendar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.*
import java.text.SimpleDateFormat
import java.util.*


class CalendarMainFragment : Fragment() {

    private val scheduleList: MutableList<Schedule> = mutableListOf()
    private lateinit var binding: FragmentCalendarMainBinding
    private lateinit var adapter: ScheduleAdapter
    private lateinit var userId: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //세션 유지_ userId 불러오기
        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        userId = sharedPreferences.getString("userId", "") ?: ""

        binding = FragmentCalendarMainBinding.inflate(inflater, container, false)


        adapter = ScheduleAdapter(scheduleList)
        binding.calendarRecyclerview.adapter = adapter
        binding.calendarRecyclerview.layoutManager = LinearLayoutManager(requireContext())

        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
        val currentDate = dateFormat.format(calendar.time)

        //달력에 접속 요청
        fetchMonthlySchedules(userId)
        //당일 날짜
        binding.tvtodayDate.text = currentDate

        //일정추가버튼_플로팅버튼
        binding.calendarDialogButton.setOnClickListener {
            val intent = Intent(requireContext(), PlanMemo::class.java)
            intent.putExtra("date", currentDate)
            intent.putExtra("userId", userId)
            startActivityForResult(intent, 100)
        }
        //recyclerview item 클릭 시, 해당 일정 추가 창으로 이동
        adapter.setOnItemClickListener(object:ScheduleAdapter.OnItemClickListener {
            override fun onItemClick(view: View?, position: Int) {
                val schedule = scheduleList[position]

                val intent = Intent(requireContext(), PlanMemo::class.java)
                intent.putExtra("schedule", schedule)
                startActivityForResult(intent, 200)
            }
        })

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

            val totalDay = "$selectedYear.${String.format("%02d", selectedMonth)}.${
                String.format(
                    "%02d",
                    selectedDay
                )
            }"
            //당일 날짜
            binding.tvtodayDate.text = totalDay

            val mserver = CalRetrofitInterface.retrofit3.create(CalendarMonthService::class.java)
            mserver.selMonthSchedule(userId, selectedMonth)
                .enqueue(object : Callback<MonthlyScheduleDTO> {
                    override fun onResponse(
                        call: Call<MonthlyScheduleDTO>?,
                        response: Response<MonthlyScheduleDTO>
                    ) {
                        Log.d("해당 월별", response.body().toString())
                        val monthlyScheduleDTO = response.body()
                        val monthlySchedules = monthlyScheduleDTO?.result

                        monthlySchedules?.let {
                            // result 배열을 date 값의 오름차순으로 정렬
                            Collections.sort(it) { a, b -> a.date.compareTo(b.date) }
                            //선택된 날짜와 같은 날짜의 값만 리스트에 보여줌
                            for (schedule in monthlySchedules) {
                                Log.d("Date", schedule.date)
                                Log.d("totalday", totalDay)
                                if (schedule.date.equals(totalDay)) {
                                    Log.d("해당 날짜에 대한 값 추출", schedule.title)
                                } else {
                                    Log.d("date 다름", "error")
                                }
                            }

                            adapter.updateData(monthlySchedules, totalDay)
                        }
                    }

                    override fun onFailure(call: Call<MonthlyScheduleDTO>?, t: Throwable) {
                        Log.d("에러", t.message!!)
                    }
                })
        }
        return binding.root
    }
    //서버로부터 날짜 불러오기
    private fun fetchMonthlySchedules(userid:String) {
        val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
        val currentDate = dateFormat.format(Date())

        val server = CalRetrofitInterface.retrofit3.create(CalendarDateService::class.java)
        Log.d("userId", userid)
        server.getMonthlySchedule(userid).enqueue(object : Callback<MonthlyScheduleDTO> {
            override fun onResponse(call: Call<MonthlyScheduleDTO>?, response: Response<MonthlyScheduleDTO>) {
                Log.d("해당 월별", response.body().toString())
                val monthlyScheduleDTO = response.body()
                val monthlySchedules = monthlyScheduleDTO?.result

                monthlySchedules?.let {
                    // result 배열을 date 값의 오름차순으로 정렬
                    Collections.sort(it) { a, b -> a.date.compareTo(b.date) }

                    for (schedule in monthlySchedules) {
                        Log.d("Date", schedule.date)
                        Log.d("totalday", currentDate)
                        if (schedule.date.equals(currentDate)) {
                            Log.d("해당 날짜에 대한 값 추출", schedule.title)
                        } else {
                            Log.d("date 다름", "error")
                        }
                    }

                    adapter.updateData(monthlySchedules, currentDate)
                }
            }
            override fun onFailure(call: Call<MonthlyScheduleDTO>?,t: Throwable) {
                Log.d("에러", t.message!!)
            }
        })
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == 100 && resultCode == AppCompatActivity.RESULT_OK) {
//            val title = data?.getStringExtra("title")
//            // RecyclerView에 데이터를 추가하는 로직을 구현
//            val plan = Schedule("","", title!!,"")
//            scheduleList.add(plan)
//            if (::adapter.isInitialized) {
//                adapter.notifyDataSetChanged()
//            }
//        }
//    }


}

