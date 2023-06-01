package com.helpet.calendar

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.helpet.databinding.FragmentCalendarMainBinding
import kotlinx.android.synthetic.main.activity_calendar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.*
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CalendarMainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CalendarMainFragment : Fragment() {

    private val scheduleList: MutableList<Schedule> = mutableListOf()
    private lateinit var binding: FragmentCalendarMainBinding
    private lateinit var adapter: ScheduleAdapter
    private lateinit var userId: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //세션 유지_ userId 불러오기
        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        userId = sharedPreferences.getString("userId", "") ?: ""

        binding = FragmentCalendarMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ScheduleAdapter(scheduleList)
        binding.calendarRecyclerview.adapter = adapter
        binding.calendarRecyclerview.layoutManager = LinearLayoutManager(requireContext())

        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
        val currentDate = dateFormat.format(calendar.time)

        //서버로부터 날짜 불러오기
        fun fetchMonthlySchedules() {
            val server = CalRetrofitInterface.retrofit.create(CalendarDateService::class.java)
            server.getMonthlySchedule(userId).enqueue(object : Callback<MonthlyScheduleDTO> {
                override fun onResponse(call: Call<MonthlyScheduleDTO>?, response: Response<MonthlyScheduleDTO>) {
                    if (response.isSuccessful) {
                        val monthlyScheduleDTO = response.body()
                        val monthlySchedules = monthlyScheduleDTO?.result ?: emptyList()
                        // 월별 일정 데이터 처리
                        adapter.updateData(monthlySchedules)
                    } else {
                        // 요청 실패 처리
                        Toast.makeText(requireContext(), "일정을 불러오지 못했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: retrofit2.Call<MonthlyScheduleDTO>?,t: Throwable) {
                    Log.d("에러", t.message!!)
                }
            })
        }


        //달력에 접속 요청
        fetchMonthlySchedules()

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



    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == AppCompatActivity.RESULT_OK) {
            val title = data?.getStringExtra("title")
            Log.d("log2", "ok")

            // RecyclerView에 데이터를 추가하는 로직을 구현
            val plan = Schedule("","","", title!!,"")
            Log.d("log3", "ok")
            scheduleList.add(plan)
            Log.d("log4", "ok")
            if (::adapter.isInitialized) {
                adapter.notifyDataSetChanged()
            }
        }
    }
}

interface CalendarDateService {
    @FormUrlEncoded
    @POST("api/calendar/")
    fun getMonthlySchedule(
        @Field("userId") userId: String
    ): Call<MonthlyScheduleDTO>
}
