package com.helpet.vector

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.helpet.R
import com.helpet.databinding.ActivityMpvectorResultBinding
import java.io.ByteArrayInputStream
import java.text.SimpleDateFormat
import java.util.*

class MPVectorResult : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMpvectorResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            val intent = Intent(this, PetInfActivity::class.java)
            startActivity(intent)
        }

        val vectimg = intent.getStringExtra("vectImg")
        val vectdate = intent.getStringExtra("vectdate")
        val vectname = intent.getParcelableArrayListExtra<DiseaseName>("vectname")
        val vectprob = intent.getDoubleExtra("vectprob", 0.0)
        val nonvectprob = 100 - vectprob!!.toInt()
        val petname = intent.getStringExtra("petname")
        val petage = intent.getIntExtra("petage" , 0)
        val petbirth = intent.getStringExtra("petbirth")

        val adapter = ResultAdapter(vectname!!, supportFragmentManager)
        binding.resultRecyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(this)
        binding.resultRecyclerView.layoutManager = layoutManager


        // 최소값과 최대값을 설정합니다.
        val minValue = 0
        val maxValue = 100

        // 현재 값과 최소값, 최대값을 설정합니다.
        val symptomLevel = (vectprob.toInt() * 10000 / maxValue)
        Log.d("symptomLevel", symptomLevel.toString())
        val aSymptomLevel = (nonvectprob * 10000 / maxValue)
        Log.d("aSymptomLevel", aSymptomLevel.toString())


        binding.symptom.min = 0
        binding.symptom.max = 100
        binding.symptom.progress = vectprob.toInt()

        binding.aSymptom.min = 0
        binding.aSymptom.max = 100
        binding.aSymptom.progress = nonvectprob

        binding.symptom.progressDrawable!!.level = symptomLevel
        binding.aSymptom.progressDrawable!!.level = aSymptomLevel


        // 직선 모양의 ProgressBar의 progressDrawable을 설정
        val progressBarDrawable = ContextCompat.getDrawable(this, R.drawable.progress_line)
        val progressBarDrawable2 = ContextCompat.getDrawable(this, R.drawable.progress_line_gray)

        binding.symptom.progressDrawable = progressBarDrawable
        binding.aSymptom.progressDrawable = progressBarDrawable2

        // ProgressBar의 진행바 색상 설정
        binding.symptom.progressTintMode = PorterDuff.Mode.SRC_IN
        binding.symptom.progressTintList = ContextCompat.getColorStateList(this, R.color.progressline)

        // ProgressBar의 두번째 진행바 (aSymptom) 색상 설정
        binding.aSymptom.progressTintMode = PorterDuff.Mode.SRC_IN
        binding.aSymptom.progressTintList = ContextCompat.getColorStateList(this, R.color.lightgray)


        // 수치에 맞게 ProgressBar와 TextView를 연결합니다.
        binding.symptonText.text = "${vectprob.toInt()}%"
        binding.asymptonText.text = "${nonvectprob}%"

        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        if (symptomLevel >= 50.0)   {
            binding.vectorSubT.text = "${petname}의 눈은 꼼꼼한 관리가 필요해요!"
            binding.vectorCheck.text = "동물병원 방문을 추천드려요."
        }
        else{
            binding.vectorSubT.text = "${petname}의 눈은 건강하게 관리되고 있어요!"
            binding.vectorCheck.text = "정기적으로 안구 검진을 부탁드려요."
        }

//        binding.resultImg.setImageBitmap(stringToBitmap(vectimg))
//                binding.VectorDate.text = "진단 날짜\n $date "
//                binding.vectorName.text = "진단결과: $name"


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