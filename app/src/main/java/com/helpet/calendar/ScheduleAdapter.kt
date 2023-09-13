package com.helpet.calendar

import android.annotation.SuppressLint
import android.content.Context
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
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_planlist, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val schedule = scheduleList[position]
        holder.titleTextView.text = schedule.title
    }

    override fun getItemCount(): Int {
        return scheduleList.size
    }

    // 일정 데이터 업데이트 함수

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(calendarPlanList: List<CalendarPlanDTO>, totalDay: String) {
        scheduleList.clear() // 기존 데이터 제거
        val filteredScheduleList = convertToSchedule(calendarPlanList).filter { it.startDate == totalDay }
        scheduleList.addAll(filteredScheduleList) // 새로운 데이터 추가
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearData() {
        scheduleList.clear()
        notifyDataSetChanged()
    }
    private fun convertToSchedule(calendarPlanList: List<CalendarPlanDTO>): List<Schedule> {

        val scheduleList : MutableList<Schedule> = mutableListOf()
        for (calendarPlan in calendarPlanList) {
            val schedule = Schedule(
                startDate = calendarPlan.startdate,
                endDate = calendarPlan.enddate,
                title = calendarPlan.title,
                description = calendarPlan.description,
                cal_idx = calendarPlan.cal_idx,
            )
            scheduleList.add(schedule)
        }
        return scheduleList
    }

}