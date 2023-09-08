package com.helpet.vector

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.helpet.R
import com.helpet.books.DBHelper
import java.io.ByteArrayInputStream
import java.util.Base64


class ChoicePetAdapter(private val ageList : List<Int>,private val  birthList: List<String>,private val  imgList: List<String>,private val  nameList :List<String>,private val  genderList : List<String>) :
    RecyclerView.Adapter<ChoicePetAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_sub_pet2, parent, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ageitem = ageList[position]
        val birthitem = birthList[position]
        val imgitem = imgList[position]
        val nameitem = nameList[position]
        val genderitem = genderList[position]
        holder.bind(ageitem, birthitem, imgitem, nameitem, genderitem)
    }

    override fun getItemCount(): Int {
        return ageList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val petImg = itemView.findViewById<ImageView>(R.id.mychoicePetImg)
        private val petAge = itemView.findViewById<TextView>(R.id.mychoicePetAge)
        private val petName = itemView.findViewById<TextView>(R.id.mychoicePetName)
        private val petBirth = itemView.findViewById<TextView>(R.id.mychoicePetBirth)



        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(ageList: Int, birthList: String, imgList: String, nameList: String, genderList: String) {

            petImg.setImageBitmap(stringToBitmap(imgList))
            petAge.text = "나이: ${ageList}살"
            petName.text = nameList
            petBirth.text = "생일: ${birthList}"

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, PetInfActivity::class.java)
                intent.putExtra("namepet", nameList)
                intent.putExtra("agepet", ageList)
                intent.putExtra("birthpet", birthList)
                intent.putExtra("genderpet", genderList)
                itemView.context.startActivity(intent)

            }
        }
    }

    //string 을  bitmap 형태로 변환하는 메서드
    @RequiresApi(Build.VERSION_CODES.O)
    fun stringToBitmap(data: String?): Bitmap? {
        var bitmap: Bitmap? = null
        val byteArray: ByteArray = Base64.getDecoder().decode(data)
        val stream = ByteArrayInputStream(byteArray)
        bitmap = BitmapFactory.decodeStream(stream)
        return bitmap
    }

}