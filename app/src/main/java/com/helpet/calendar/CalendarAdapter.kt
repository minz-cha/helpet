package com.helpet.calendar

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.helpet.R
class CalendarAdapter(
    private val startDate: List<String>,
    private val endDate: List<String>,
    private val title: List<String>,
    private val description: List<String>,
    private val calIdx : List<Int>
) : RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textTitle: TextView = itemView.findViewById(R.id.textTitle)
        val dialogDate: TextView = itemView.findViewById(R.id.dialogDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.activity_dialog_recycler, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val startdateItem = startDate[position]
        val enddateItem = endDate[position]
        val titleItem = title[position]
        val descriptionItem = description[position]
        val calIdxItem = calIdx[position]
        if (startdateItem == enddateItem) {
            holder.dialogDate.text = startdateItem
        } else {
            holder.dialogDate.text = "${startdateItem}~\n${enddateItem}"
        }
        holder.textTitle.text = titleItem


        holder.textTitle.setOnClickListener {
            val intent = Intent(it.context, ShowCalContent::class.java )
            intent.putExtra("title", titleItem)
            intent.putExtra("startDate", startdateItem)
            intent.putExtra("endDate", enddateItem)
            intent.putExtra("description", descriptionItem)
            intent.putExtra("calIdx", calIdxItem)
            Log.d("cal_idx", calIdxItem.toString())
            it.context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        return startDate.size
    }


}
