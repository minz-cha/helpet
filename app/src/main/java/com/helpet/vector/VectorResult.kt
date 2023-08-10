package com.helpet.vector

import android.annotation.SuppressLint
import com.helpet.R
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.SpannableString
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import com.helpet.books.VectList
import com.helpet.databinding.ActivityVectorResultBinding
import com.helpet.login.result
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

class VectorResult : AppCompatActivity() {
    val vecdate2 = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

    val binding = ActivityVectorResultBinding.inflate(layoutInflater)

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val namepet  = intent.getStringExtra("name")
        //질병 명 , 유증상 확률, 무증상 확률, 진단 이미지, 유저아이디
        val name = intent.getStringExtra("name")
        val symptonProbability = intent.getDoubleExtra("symptomProbability", 0.0)
        val vectcontent = intent.getStringExtra("vectContent")
        val vectimg = intent.getByteArrayExtra("vectImg")
        val resultimg = SerialBitmap.translate(vectimg!!)
        val userId = intent.getStringExtra("value")

        if (name != null) {
            Log.d("name", name)
        }
        Log.d("symptonProbability", symptonProbability.toString())

// 최소값과 최대값을 설정합니다.
        val minValue = 0
        val maxValue = 100

// 현재 값과 최소값, 최대값을 설정합니다.
        val currentValue = 50
        binding.vectorResultPro.min = minValue
        binding.vectorResultPro.max = maxValue
        binding.vectorResultPro.progress = currentValue

            if (symptonProbability.toInt() >= 30) {
                val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                val originalText = "안구 진단 결과 \n이상징후가 있습니다 !"
                val targetText = "이상징후가 있습니다!"
                val start = originalText.indexOf(targetText)
                val end = originalText.length // 끝 위치
                val spannable = SpannableString(originalText)
                binding.vectorTitle.text = spannable
                binding.vectorSubT.text = "눈은 세심한 관리가 필요해요:) "
                binding.resultImg.setImageBitmap(resultimg)
                binding.VectorDate.text = "진단 날짜\n $date "
                binding.vectorName.text = "진단결과: $name"
                if (symptonProbability in 50.0..80.0){
                    binding.vectorResultPro.progressDrawable.setLevel(symptonProbability.toInt() * 10000 / maxValue)
                    val progressDrawable = binding.vectorResultPro.indeterminateDrawable
                    progressDrawable.setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN)
                    binding.vectorResultPro.indeterminateDrawable = progressDrawable
                }
                else if (symptonProbability < 50){
                    binding.vectorResultPro.progressDrawable.setLevel(symptonProbability.toInt() * 10000 / maxValue)
                    val progressDrawable = binding.vectorResultPro.indeterminateDrawable
                    progressDrawable.setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN)
                    binding.vectorResultPro.indeterminateDrawable = progressDrawable
                }
                else{
                    binding.vectorResultPro.progressDrawable.setLevel(symptonProbability.toInt() * 10000 / maxValue)
                    val progressDrawable = binding.vectorResultPro.indeterminateDrawable
                    progressDrawable.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN)
                    binding.vectorResultPro.indeterminateDrawable = progressDrawable
                }

//                resultImg.setImageBitmap(resultImg)
                // 수치에 맞게 ProgressBar와 TextView를 연결합니다.
                binding.progressText.text = "$symptonProbability%"
                binding.vectorContent.text = "$vectcontent"

            } else {
                val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                val originalText = "안구 진단 결과 \n이상징후가 없습니다 !"
                val targetText = "이상징후가 있습니다!"
                val start = originalText.indexOf(targetText)
                val end = originalText.length // 끝 위치
                val spannable = SpannableString(originalText)
                binding.vectorTitle.text = spannable
                binding.vectorSubT.text = "눈은 아주 잘 관리되고 있어요:) "
                binding.resultImg.setImageBitmap(resultimg)
                binding.VectorDate.text = "   진단 날짜\n $date "
                binding.vectorName.text = "진단결과: $name"
                if (symptonProbability in 50.0..80.0){
                    binding.vectorResultPro.progressDrawable.setLevel(symptonProbability.toInt() * 10000 / maxValue)
                    val progressDrawable = binding.vectorResultPro.indeterminateDrawable
                    progressDrawable.setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN)
                    binding.vectorResultPro.indeterminateDrawable = progressDrawable
                }
                else if (symptonProbability < 50){
                    binding.vectorResultPro.progressDrawable.setLevel(symptonProbability.toInt() * 10000 / maxValue)
                    val progressDrawable = binding.vectorResultPro.indeterminateDrawable
                    progressDrawable.setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN)
                    binding.vectorResultPro.indeterminateDrawable = progressDrawable
                }
                else{
                    binding.vectorResultPro.progressDrawable.setLevel(symptonProbability.toInt() * 10000 / maxValue)
                    val progressDrawable = binding.vectorResultPro.indeterminateDrawable
                    progressDrawable.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN)
                    binding.vectorResultPro.indeterminateDrawable = progressDrawable
                }
//                resultImg.setImageBitmap(vecimg)
                binding.progressText.text = "$symptonProbability%"
                binding.vectorContent.text = "$vectcontent"
            }

        binding.storeVector.setOnClickListener {
            VectorResultUpdate(vectimg,userId!!,namepet!!, vecdate2, name!!,symptonProbability,vectcontent!!)
        }
        binding.goBooks.setOnClickListener {
            val intent = Intent(this, VectList::class.java)
            startActivity(intent)
        }

    }

    private val vectserver = RetrofitApi2.retrofit2.create(VectResultService::class.java)
    fun VectorResultUpdate(vectImg:ByteArray,userId:String, namepet:String, vecDate2 :String ,name:String,symptonProbability:Double,vectContent:String) {
        val fileBody = RequestBody.create("image/*".toMediaTypeOrNull(), vectImg)
        val multipartBody: MultipartBody.Part? =
            MultipartBody.Part.createFormData("vectImg", "vectImg.jpeg", fileBody)
        val userIdR = userId.toRequestBody("text/plain".toMediaTypeOrNull())
        val namepetR = namepet.toRequestBody("text/plain".toMediaTypeOrNull())
        val vectDateR = vecDate2.toRequestBody("text/plain".toMediaTypeOrNull())
        val nameR = name.toRequestBody("text/plain".toMediaTypeOrNull())
        val vectContentR = vectContent.toRequestBody("text/plain".toMediaTypeOrNull())

        vectserver.vectResultService(multipartBody!!,userIdR, namepetR, vectDateR, nameR, symptonProbability,vectContentR ).enqueue(object : Callback<VectResultResponseDTO?> {
                override fun onResponse(call: Call<VectResultResponseDTO?>?, response: Response<VectResultResponseDTO?>) {
                    Log.d("진단 결과 저장", "" + response.body().toString())
                    Toast.makeText(this@VectorResult, "저장되었습니다.", Toast.LENGTH_SHORT).show()
//                    intent = Intent(applicationContext, HomeActivity::class.java)
//                    startActivity(intent)

                }

                override fun onFailure(call: Call<VectResultResponseDTO?>?, t: Throwable) {
                    Toast.makeText(applicationContext, "통신 실패", Toast.LENGTH_SHORT).show()
                    Log.d("에러", t.message!!)
                }
            })
        }

}



