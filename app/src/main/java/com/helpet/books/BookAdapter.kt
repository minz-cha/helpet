package com.helpet.books

import android.content.Context
import android.content.Intent
import android.telecom.DisconnectCause
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.helpet.R

class BookAdapter(private val context: Context, private val diseases: List<Disease>) : RecyclerView.Adapter<BookAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.activity_vect_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val disease = diseases[position]
        holder.bind(disease)
    }

    override fun getItemCount(): Int = diseases.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val diseaseNameTextView: TextView = itemView.findViewById(R.id.bookDiseaseName)

        fun bind(disease: Disease) {
            Log.d("disease", disease.toString())
            diseaseNameTextView.text = disease.name

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, VectDetailCat::class.java)
                intent.putExtra("name", disease.name)
                intent.putExtra("symptoms", disease.symptoms)
                intent.putExtra("cause", disease.cause)
                intent.putExtra("treats", disease.treats)

                Log.d("cause", disease.cause)
                itemView.context.startActivity(intent)
            }
        }
    }
}