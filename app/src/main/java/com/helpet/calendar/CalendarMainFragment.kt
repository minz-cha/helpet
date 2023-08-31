package com.helpet.calendar

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import androidx.fragment.app.Fragment
import com.helpet.R
import com.helpet.databinding.FragmentCalendarMainBinding


class CalendarMainFragment : Fragment() {

    private lateinit var userId: String
    var dialog: Dialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //세션 유지_ userId 불러오기
        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        userId = sharedPreferences.getString("userId", "") ?: ""

        val binding = FragmentCalendarMainBinding.inflate(inflater, container, false)

        dialog = Dialog(this.requireContext())    // Dialog 초기화
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        dialog!!.setContentView(R.layout.activity_calendar_dialog); // xml 레이아웃 파일과 연결


        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            Log.d("year", year.toString())
            Log.d("month", (month + 1).toString())
            Log.d("dayOfMonth", dayOfMonth.toString())

            dialog!!.show()

            val registerCal = binding.root.findViewById<Button>(R.id.RegisterCal)
            registerCal.setOnClickListener {
//                val intent = (requireActivity(), Plan)
            }




            
        }



        return binding.root
    }



}

