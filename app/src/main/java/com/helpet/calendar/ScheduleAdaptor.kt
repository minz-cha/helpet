package com.helpet.calendar


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.helpet.R

class ScheduleAdapter(private val scheduleList: List<Schedule>) :
    RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.edtTitle)
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

    override fun getItemCount() = scheduleList.size
}