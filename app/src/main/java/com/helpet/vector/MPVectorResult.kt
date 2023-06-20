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
import androidx.annotation.RequiresApi
import com.helpet.R
import kotlinx.android.synthetic.main.activity_mpvector_result.*
import kotlinx.android.synthetic.main.activity_vector_result.*
import java.io.ByteArrayInputStream
import java.util.*

class MPVectorResult : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mpvector_result)

        mpclose.setOnClickListener {
            val intent = Intent(this, PetInfActivity::class.java)
            startActivity(intent)
        }

// 최소값과 최대값을 설정합니다.
        val minValue = 0
        val maxValue = 100

// 현재 값과 최소값, 최대값을 설정합니다.
        val currentValue = 50
        mpvectorResultPro.min = minValue
        mpvectorResultPro.max = maxValue
        mpvectorResultPro.progress = currentValue

        val mpvectimg = intent.getStringExtra("vectimg")
        val mpvectdate = intent.getStringExtra("vectdate")
        val mpvectname = intent.getStringExtra("vectname")
        val mpvectprob = intent.getDoubleExtra("vectprob", 0.0)
        val mppetname = intent.getStringExtra("petname")
        val mppetage = intent.getIntExtra("petage", 0)
        val mppetbirth = intent.getStringExtra("getbirth")

//        stringToBitmap(mpvectimg)
        mpresultImg.setImageBitmap(stringToBitmap(mpvectimg))
        mpVectTitle.text = "안구 체크 결과 : $mpvectname"
        mpvectorName.text= mpvectname
        mpprogressText.text= " $mpvectprob %"
        mpVectorDate.text = "진단 날짜\n $mpvectdate"
        mpvectorResultPro.progressDrawable.setLevel(mpvectprob.toInt() * 10000 / maxValue)

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