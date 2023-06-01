package com.helpet.calendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.helpet.R
import kotlinx.coroutines.NonDisposableHandle.parent

class ScheduleAdapter(private val scheduleList: MutableList<Schedule>) :
    RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {

    private var listener: OnItemClickListener? = null


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val titleTextView: TextView = itemView.findViewById(R.id.plan)

        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(view: View) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener?.onItemClick(itemView, position)
            }
        }
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

    interface OnItemClickListener  {
        fun onItemClick(view: View?, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_planlist, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val schedule = scheduleList[position]
        holder.titleTextView.text = schedule.title
    }

    override fun getItemCount(): Int {
        return scheduleList.size
    }
//
//    // 월별 일정 데이터를 Schedule로 변환하는 함수
//     fun convertToSchedule(calendarPlans: List<CalendarPlanDTO>): List<Schedule> {
//        val scheduleList : MutableList<Schedule> = mutableListOf()
//        for (calendarPlan in calendarPlans) {
//            val schedule = Schedule(
//                calIdx = calendarPlan.calIdx,
//                userId = calendarPlan.userId,
//                date = calendarPlan.date,
//                title = calendarPlan.title,
//                description = calendarPlan.description,
//                month = calendarPlan.month
//            )
//            scheduleList.add(schedule)
//        }
//        return scheduleList
//    }

    // 일정 데이터 업데이트 함수
    fun updateData(calendarPlanList: List<CalendarPlanDTO>) {
//        val convertedScheduleList = convertToSchedule(calendarPlanList)
        scheduleList.clear()
        scheduleList.addAll(convertToSchedule(calendarPlanList))
        notifyDataSetChanged()
    }

    private fun convertToSchedule(calendarPlanList: List<CalendarPlanDTO>): List<Schedule> {

        val scheduleList : MutableList<Schedule> = mutableListOf()
        for (calendarPlan in calendarPlanList) {
            val schedule = Schedule(
                calIdx = calendarPlan.calIdx.toString(),
                userId = calendarPlan.userId,
                date = calendarPlan.date,
                title = calendarPlan.title,
                description = calendarPlan.description,
                month = calendarPlan.month.toString()
            )
            scheduleList.add(schedule)
        }
        return scheduleList
    }
//
//    private fun processMonthlySchedules(monthlySchedules: List<CalendarPlanDTO>) {
//        // RecyclerView나 다른 방식을 사용하여 변환된 일정 데이터를 화면에 표시
//        adapter.updateData(monthlySchedules)
//    }

//    // processMonthlySchedules 함수 내에서 convertToSchedule을 사용하여 변환 후 adapter에 전달
//    private fun processMonthlySchedules(monthlySchedules: List<CalendarPlanDTO>) {
//        val convertedScheduleList = convertToSchedule(monthlySchedules)
//        // RecyclerView나 다른 방식을 사용하여 변환된 일정 데이터를 화면에 표시
//        val adapter = ScheduleAdapter(convertedScheduleList)
//        recyclerView.adapter = adapter
//    }


}