package com.helpet.vector

import android.annotation.SuppressLint
import android.content.Context
import com.helpet.R


import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_vector_result.*
import java.text.SimpleDateFormat
import java.util.*

class VectorResult : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vector_result)

        storeVector.setOnClickListener {
            Toast.makeText(this@VectorResult, "저장되었습니다.", Toast.LENGTH_SHORT).show()
        }


        goBooks.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
//        resultImg.clipToOutline=true

        val name = intent.getStringExtra("name")
        val symptonProbability = intent.getDoubleExtra("symptomProbability", 0.0)
        val asymptomaticProbability = intent.getDoubleExtra("asymptomaticProbability", 0.0)
        val name2 = intent.getStringExtra("name2")
        val symptonProbability2 = intent.getDoubleExtra("symptomProbability", 0.0)
        val asymptomaticProbability2 = intent.getDoubleExtra("asymptomaticProbability", 0.0)

        if (name != null) {
            Log.d("name", name)
        }
        Log.d("symptonProbability", symptonProbability.toString())



// 최소값과 최대값을 설정합니다.
        val minValue = 0
        val maxValue = 100

// 현재 값과 최소값, 최대값을 설정합니다.
        val currentValue = 50
        vectorResultPro.min = minValue
        vectorResultPro.max = maxValue
        vectorResultPro.progress = currentValue

// 수치를 나타내는 TextView의 ID를 가져옵니다.
//        val progressText = findViewById<TextView>(R.id.progressText)

// 수치에 맞게 ProgressBar와 TextView를 연결합니다.
//        progressText.text = "$currentValue / $maxValue"


        for (i in 1..2) {
            var sympton = 0.0
            if(i ==1){
                 sympton =  symptonProbability
            }
            else{
                 sympton= symptonProbability2
            }
            Log.d("sympton", sympton.toString())

            // i가 1일 때는 symptonProbability, i가 2일 때는 symptonProbability2를 가져옵니다.
            val vecName= if (i == 1) vectorName else vectorName2
            val vecNameR = if (i==1) name else name2
            val vecProgress = if (i==1) vectorResultPro else vectorResultPro2

            if (sympton.toInt() <= 50) {
                val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                vectorTitle.text = "안구 진단 결과 \n2가지의 이상징후가 있습니다 !"
                vectorSubT.text = "몽이의 눈은 세심한 관리가 필요해요:) \n"
                vectorCheck.text = "체크관리일: $date\""
                vecName.text = "진단결과: $vecNameR"
                vecProgress.progressDrawable.setLevel(sympton.toInt() * 10000 / maxValue)
            } else {
                val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                vectorTitle.text = "안구 진단 결과 \n이상징후가 없습니다 !"
                vectorSubT.text = "몽이의 눈은 아주 잘 관리되고 있어요:) \n"
                vectorCheck.text = "체크관리일: $date\""
                vecName.text = "진단결과: $vecNameR"
                vecProgress.progressDrawable.setLevel(sympton.toInt() * 10000 / maxValue)
            }
        }

    }
}


