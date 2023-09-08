package com.helpet.books

import android.content.Context
import android.content.Intent
import android.telecom.DisconnectCause
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
        private val diseaseImg : ImageView = itemView.findViewById(R.id.diseaseImg)

        fun bind(disease: Disease) {
            Log.d("disease", disease.toString())
            diseaseNameTextView.text = disease.name

            when(disease.name){
                "결막염" -> diseaseImg.setImageResource(R.drawable.vect1)
                "백내장" -> diseaseImg.setImageResource(R.drawable.vect2)
                "각막궤양" -> diseaseImg.setImageResource(R.drawable.vect3)
                "유루증" -> diseaseImg.setImageResource(R.drawable.vect4)
                "안검내반증" -> diseaseImg.setImageResource(R.drawable.vect5)
                "안검염" -> diseaseImg.setImageResource(R.drawable.vect6)
                "궤양성각막질환" -> diseaseImg.setImageResource(R.drawable.vect7)
                "비궤양성각막질환" -> diseaseImg.setImageResource(R.drawable.vect8)
                "색소침착성각막염" -> diseaseImg.setImageResource(R.drawable.vect9)
                "핵경화" -> diseaseImg.setImageResource(R.drawable.vect10)
                "각막부골편" -> diseaseImg.setImageResource(R.drawable.vect11)
                "비궤양성각막염" -> diseaseImg.setImageResource(R.drawable.vect12)

            }

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