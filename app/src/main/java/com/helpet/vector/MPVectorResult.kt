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
import com.helpet.R
import com.helpet.databinding.ActivityMpvectorResultBinding
import java.io.ByteArrayInputStream
import java.util.*

class MPVectorResult : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMpvectorResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mpclose.setOnClickListener {
            val intent = Intent(this, PetInfActivity::class.java)
            startActivity(intent)
        }

// 최소값과 최대값을 설정합니다.
        val minValue = 0
        val maxValue = 100

// 현재 값과 최소값, 최대값을 설정합니다.
        val currentValue = 50
        binding.mpvectorResultPro.min = minValue
        binding.mpvectorResultPro.max = maxValue
        binding.mpvectorResultPro.progress = currentValue

        val mpvectimg = intent.getByteArrayExtra("vectimg")
        val mpvectdate = intent.getStringExtra("vectdate")
        val mpvectname = intent.getStringExtra("vectname")
        val mpvectprob = intent.getDoubleExtra("vectprob", 0.0)
        val mppetname = intent.getStringExtra("petname")
        val mppetage = intent.getIntExtra("petage", 0)
        val mppetbirth = intent.getStringExtra("petbirth")

        binding.mpresultImg.setImageBitmap(SerialBitmap.translate(mpvectimg!!))
        binding.mpVectTitle.text = "안구 체크 결과 : $mpvectname"
        binding.mpvectorName.text= mpvectname
        binding.mpprogressText.text= " $mpvectprob %"
        binding.mpVectorDate.text = "진단 날짜\n $mpvectdate"
        binding.mpvectorResultPro.progressDrawable.setLevel(mpvectprob.toInt() * 10000 / maxValue)

//        if (mpvectprob in 50.0..80.0){
//            vectorResultPro.progressDrawable.setLevel(mpvectprob.toInt() * 10000 / maxValue)
//            val progressDrawable = vectorResultPro.indeterminateDrawable
//            progressDrawable.setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN)
//            mpvectorResultPro.indeterminateDrawable = progressDrawable
//        }
//        else if (mpvectprob < 50){
//            vectorResultPro.progressDrawable.setLevel(mpvectprob.toInt() * 10000 / maxValue)
//            val progressDrawable = vectorResultPro.indeterminateDrawable
//            progressDrawable.setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN)
//            mpvectorResultPro.indeterminateDrawable = progressDrawable
//        }
//        else{
//            vectorResultPro.progressDrawable.setLevel(mpvectprob.toInt() * 10000 / maxValue)
//            val progressDrawable = vectorResultPro.indeterminateDrawable
//            progressDrawable.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN)
//            mpvectorResultPro.indeterminateDrawable = progressDrawable
//        }
//

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