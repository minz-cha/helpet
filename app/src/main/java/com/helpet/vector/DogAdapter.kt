package com.helpet.vector

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.helpet.R
import java.io.ByteArrayInputStream
import java.util.Base64

class DogAdapter(private val dataList: List<Result>) : RecyclerView.Adapter<DogAdapter.DogViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.activity_sub_pet, parent, false)
        return DogViewHolder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        val currentItem = dataList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class DogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // 아이템 뷰 내에서 UI 요소를 findViewById로 찾습니다.
        private val choicePetName: TextView = itemView.findViewById(R.id.choicePetName)
        private val choicePetImg: ImageView = itemView.findViewById(R.id.choicePetImg)
        private val choicePetAge: TextView = itemView.findViewById(R.id.choicePetAge)
        private val choicePetBirth: TextView = itemView.findViewById(R.id.choicePetBirth)
        private val choicePetGender: ImageView = itemView.findViewById(R.id.choicePetGender)

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(result: Result) {
            // 데이터를 UI에 설정
            Log.d("species", result.petSpecies)
                choicePetName.text = result.petName
                choicePetImg.setImageBitmap(stringToBitmap(result.petImg))
                choicePetAge.text = "나이 : ${result.petAge}살"
                choicePetBirth.text = "생일 : ${result.petBirth}"
                if (result.petGender == "남자") {
                    choicePetGender.setImageResource(R.drawable.baseline_male_24)
                } else {
                    choicePetGender.setImageResource(R.drawable.baseline_female_24)
                }

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, VectorCamera::class.java)
                intent.putExtra("namepet", result.petName)
                intent.putExtra("birthpet", result.petBirth)
                intent.putExtra("speciespet", result.petSpecies)
                intent.putExtra("genderpet", result.petGender)
                intent.putExtra("agepet", result.petAge)

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
