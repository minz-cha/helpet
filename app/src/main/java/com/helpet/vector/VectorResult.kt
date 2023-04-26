package com.helpet.vector

import android.annotation.SuppressLint
import android.content.Context
import com.helpet.R


import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import com.helpet.login.result
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
        val vecImg= intent.getParcelableExtra<Bitmap>("vecImg")
//        val name2 = intent.getStringExtra("name2")
//        val symptonProbability2 = intent.getDoubleExtra("symptomProbability", 0.0)
//        val asymptomaticProbability2 = intent.getDoubleExtra("asymptomaticProbability", 0.0)

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

//
//        for (i in 1..2) {
//            var sympton = 0.0
//            if(i ==1){
//                 sympton =  symptonProbability
//            }
//            else{
//                 sympton= symptonProbability2
//            }
//            Log.d("sympton", sympton.toString())
//
//            // i가 1일 때는 symptonProbability, i가 2일 때는 symptonProbability2를 가져옵니다.
//            val vecName= if (i == 1) vectorName else vectorName2
//            val vecNameR = if (i==1) name else name2
//            val vecProgress = if (i==1) vectorResultPro else vectorResultPro2
//
//            if (sympton.toInt() >= 50) {
//                val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
//                vectorTitle.text = "안구 진단 결과 \n2가지의 이상징후가 있습니다 !"
//                vectorSubT.text = "몽이의 눈은 세심한 관리가 필요해요:) \n"
//                vectorCheck.text = "체크관리일: $date\""
//                vecName.text = "진단결과: $vecNameR"
//                vecProgress.progressDrawable.setLevel(sympton.toInt() * 10000 / maxValue)
//            } else {
//                val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
//                vectorTitle.text = "안구 진단 결과 \n이상징후가 없습니다 !"
//                vectorSubT.text = "몽이의 눈은 아주 잘 관리되고 있어요:) \n"
//                vectorCheck.text = "체크관리일: $date\""
//                vecName.text = "진단결과: $vecNameR"
//                vecProgress.progressDrawable.setLevel(sympton.toInt() * 10000 / maxValue)
//            }
//        }
            if (symptonProbability.toInt() >= 50) {
                val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                val originalText = "안구 진단 결과 \n이상징후가 있습니다 !"
                val targetText = "이상징후가 있습니다!"
                val start = originalText.indexOf(targetText)
                val end = originalText.length // 끝 위치
                val spannable = SpannableString(originalText)
//                spannable.setSpan(ForegroundColorSpan(Color.rgb(238,121,117)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                vectorTitle.text = spannable
//                vectorTitle.text = "안구 진단 결과 \n이상징후가 있습니다 !"
                vectorSubT.text = "몽이의 눈은 세심한 관리가 필요해요:) "
                VectorDate.text = "진단 날짜\n $date "
                vectorName.text = "진단결과: $name"
                vectorResultPro.progressDrawable.setLevel(symptonProbability.toInt() * 10000 / maxValue)
                resultImg.setImageBitmap(vecImg)
                // 수치에 맞게 ProgressBar와 TextView를 연결합니다.
                progressText.text = "$symptonProbability%"
                if (name=="결막염"){
                    vectorContent.setText("결막염이란 눈꺼풀 안쪽에서 안구를 보호하는 막에 염증이 생기는 상태를 말합니다. 강아지 눈꺼풀을 살짝 들었을 때 흰자가 붉게 충혈되었으며, 눈에서 녹색 혹은 노란색의 분비물이 나오기도 합니다.\n결막염 원인으로는 바이러스 혹은 세균에 의한 감염, 강아지 알레르기, 박테리아 감염, 안구건조 등이 있습니다.  ")
                }
                if (name=="백내장"){
                    vectorContent.setText("백내장이란 눈의 수정체가 흐려지거나 불투명해져 시력에 장애를 주는 질환입니다. 강아지 눈의 수정체는 빛을 망막에 집중시켜 강아지가 선명하게 볼 수 있도록 하는데, 이 수정체에 백내장이 생기면 망막에 도달하는 빛을 산란시켜 시야가 흐려지거나 막히게됩니다.\n백내장 원인으로는 유전적 소인, 노화, 당뇨병, 눈 부상또는 염증, 영양결핍 등이  있습니다. ")
                }

            } else {
                val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                val originalText = "안구 진단 결과 \n이상징후가 없습니다 !"
                val targetText = "이상징후가 있습니다!"
                val start = originalText.indexOf(targetText)
                val end = originalText.length // 끝 위치
                val spannable = SpannableString(originalText)
//                spannable.setSpan(ForegroundColorSpan(Color.rgb(238,121,117)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                vectorTitle.text = spannable
//                vectorTitle.text = "안구 진단 결과 \n이상징후가 없습니다 !"
                vectorSubT.text = "꼬깜이의 눈은 아주 잘 관리되고 있어요:) "
                VectorDate.text = "   진단 날짜\n $date "
                 vectorName.text = "진단결과: $name"
                vectorResultPro.progressDrawable.setLevel(symptonProbability.toInt() * 10000 / maxValue)
                resultImg.setImageBitmap(vecImg)
                progressText.text = "$symptonProbability%"
                if (name=="결막염"){
                    vectorContent.setText("결막염이란 눈꺼풀 안쪽에서 안구를 보호하는 막에 염증이 생기는 상태를 말합니다. 강아지 눈꺼풀을 살짝 들었을 때 흰자가 붉게 충혈되었으며, 눈에서 녹색 혹은 노란색의 분비물이 나오기도 합니다.\n결막염 원인으로는 바이러스 혹은 세균에 의한 감염, 강아지 알레르기, 박테리아 감염, 안구건조 등이 있습니다.  ")
                }
                if (name=="백내장"){
                    vectorName.setText("백내장이란 눈의 수정체가 흐려지거나 불투명해져 시력에 장애를 주는 질환입니다. 강아지 눈의 수정체는 빛을 망막에 집중시켜 강아지가 선명하게 볼 수 있도록 하는데, 이 수정체에 백내장이 생기면 망막에 도달하는 빛을 산란시켜 시야가 흐려지거나 막히게됩니다.\n백내장 원인으로는 유전적 소인, 노화, 당뇨병, 눈 부상또는 염증, 영양결핍 등이  있습니다. ")
                }
            }
    }
}


