package com.helpet.vector

import android.annotation.SuppressLint
import com.helpet.R
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.helpet.view.Books.VectList
import com.helpet.databinding.ActivityVectorResultBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class VectorResult : AppCompatActivity() {
    val vecdate2 = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())


    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityVectorResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val namepet  = intent.getStringExtra("namepet")
//        질병 명 , 유증상 확률, 무증상 확률, 진단 이미지, 유저아이디
        val name = intent.getParcelableArrayListExtra<DiseaseName>("name")
        val symptonProbability = intent.getDoubleExtra("symptomProbability", 0.0)
        val asymptomaticProbability = intent.getDoubleExtra("asymptomaticProbability", 0.0)
        val vectimg = intent.getByteArrayExtra("vectImg")
        val resultimg = SerialBitmap.translate(vectimg!!)
        val userId = intent.getStringExtra("value")

//        val namepet = "보이"
//        val tempDiseaseNames = arrayListOf(
//            DiseaseName("결막염"),
//            DiseaseName("백내장"),
//            DiseaseName("녹내장"),
//            DiseaseName("포도막염")
//        )
//        val name :ArrayList<DiseaseName> = ArrayList(tempDiseaseNames)
//        val symptonProbability = 67.0
//        val asymptomaticProbability =  33.0
//        val userId = "ellen"
//        val base64String = "iVBORw0KGgoAAAANSUhEUgAAAZAAAAGQCAIAAAAP3aGbAAAAAXNSR0IArs4c6QAAAANzQklUCAgI2+FP4AAAIABJREFUeJxMvNlyJEmSLaaLbe4RWDKzsrq6qme5whbhB1wRvvEz+I38IYrwDufemZ7p6tqzMgFEuLuZ6cIHDaAHDxAkMhDhbq6mevSco4b/9//1f374+F7crvtlafX7//hPRv74/kNKxcm/+faPv//+6fnTl6U2VaXEdW2c0zSVPuTYP3/6/auv/3C6u1PVXMu0+dvPv9ydVx3z4eGh1prb8te//IcdQ0TO79/fv7tflvry8mTd9+u2X1+++eYbbsnAGWn28esPv3z8+LG0OsG+/ubjTz/99Mtff/rw4YO4tHX50z/8t+//869z3xBs7MfD+3d/+od/+vX3T9vLBcF+//nXdV3/8X/756eXZyIYR//1+5/u7+/v378fLnePD5enLz/85T8/PL47nRcqeb1bn56ePn3/84d374nrcmp//NN3/9///Jf9eau1qsiHj+8fH8/f//jD5fO1tbXv2x+/++bhq/ff//DX/bqNMdZ8+viHrw3AzF4+f/nlp5+//fZP77/6sPfNzMbRf/rhx3/853+qS9n6Zqb9ejmO4/37j1Tysp6fnp5+/Mt/fvvtt0ikBF9//fUPP/xwffry8HDPOeWW23L69OtvPpUAU64fP3687pdPv/3WSkVESvTH777993/9XyklcjT0uw/vzGy/bPPo5NCW+vV33/3444/e5zh67/1P//gPCv708sxMvXfG/O133/365Tcz05e5H9cP337liHOK9PHpbz/98U9//OqbP/z026+F63Ec++X63T98a2a9H7Prb7/9cj6f/vSP3z1/edn3fezTyb/+w4e9b4B5u1y//Pr7n//8Z0GbcxLg3/7z+6+/+sM33/7xh19+bkv57cdfa61fffv19dhldJ1y/fz08PBQ7069dxN4eno6n9fz/V0fAwG+/Prpw4cP7W798vyUqT59+bKW83ff/fG3z7+bixy29e2f//zPv33+TbaB5v2YX33zlQO8XJ6sa+/7+49f5Zo/f3oqpSRHZLp7vPtf//aXJTVuhSrcPz58+tuvCEwM7bSeTsu//uu/fnj8QES5tHcf3v/Pf/kfzPzh/Ud1SSn98OP3a67n8xlKOY5j/3zhTI9ff7XL0Xj55aefSfnbb795Gdfj2L78/OV8Pn/45qvLdnW16/V6v9x98903v/7667Zfnn+/vP/41ftvvvnp55/lupvJ6f7uu3/87n/9j39TVXXLrX777bf//u//Pq7Hw/1dW5av//DV//v//Ett5e7+3kGXU/u3f/u3+9O75bSKjFLaT9//uK7nx6/fb8deqf3lL/929/jw7t3D09OTmT1/fv7w9cfz/d3T01Pfx365fvzDH9tav//++3ZqTy/PHz9+LKn+7W8/Fs7r+byclufr8/PvT6fz+XQ6Lcvyy88/fv7t85/+8Z84F2Z+uV5/+OsPf/7zn4++rev6H3/5677v//3/+O//8R//QYk/ffpUWv32228/f/50HGMc0xz//L//+Yeffrx8fpk211P7+puP3//7X4GpliUVzjk/v3xxzK01hKSqlGtBZBFpre37vu/73XpKKTFza23fN2aec9ZaASCllFImIh1ior3P1tZWamZGRHfXoce+J0zLsqSUHh8fn56e3L21lnMuKaeUEFHEjuMgIiJCxJyriBDRcRyqmnNOKeWc3X27XFtrrdRlWWqt4FpbRkREVFUiEjcRUZ0iAgCAbmYABgBmZgjn85kIAcDd43tizjmjAxHZFGQaou4uInN2IjIEIso555ydEACQac5+vr/jXK7XKyOlVEAh50xEZnYcBwDUWh2NGeecRDT7WJYFEWPp3H2/HgSsrikl0ZFTImZ3Tym11vbjcPfeu5kzs7vHgsS7MXMppR+HmaE5OTDztm2U2B3MrLUlXixzmpmpAIC5mlnvHRFTSkSkqog4p1xfroheSmHmefT4obVWa1VVxoQJmRmZELH3Xae4e61VVVJKAJaQcs6q6ggpJdVpU9zdFBAx57wsi4gwp5TS2xPf9x3Je++tNYzVAXB3M0splVLiZS7qohGKidlEwVxESqmELDp67yLDzID86B1cGXDOiYjxUGqtiHj0rfceEYUId3d3RITmn5++nM9nR2BmAABXIlrbUkpBcwBIxHd3DzlXVQUAkbFfL+aOiGrT3TmhmeWcIwiXZcmvFw/mRLCu6/m8ElHft/vz3bLUy+USW4OZ4xGbwZBpCu4+5xxjqMiYM+I/U0ZydzfRmvL1eo2tCoiqKtMAPV6pZhEniMhIRLQsyxiDmV0VzOfsc86l1Frr2yJfLpfYuRGE1+1lzplqUtWSMpi7eykppdT3vfe+thZX2HtX1ZQSlxzRZWbMjIh97KWUMUZtOWV6eXo6nZeIcBNNxDlnMwOAd+/e5ZwBIGVqpSzL8u7h8XR/x8zqhoin0+n5+RnNzQwxtlsqdV1UdfaRiZdlWZYlpYLoInIcx+VyMdfeOxAuy+n+/tHEUIAcyWltK5iDadz/518+tbS4Gjqp6hgjExJ6ZkrEjJQpjyHHcaSURIQoDZUI7j77MQ5zGWMA2OzHdrmWlBOxqo5t9yFzTlcz0cKJchJVR3M0U0GzXNLd3Z2jDZU5p6q7+1CJrdh7732qeiRNQAO14ziQOeW8LMvS2lRxd2aOcFfVy2VT1XVtZsY5pVLMbAzJlMGwlHJ3d3eMXWwiIiW+ezgP6QAmoycCcwEwcTFwApAx3j8+ppTUFcBVJ5JH1hMRM5s6IugR0Q1VNfJySoU5mxk6rLmiEwC0VpgRkcaQlAoAnE6nUsqcs6ZsZswoMtREwQ0cCCkzEOScEzEA5JzNRcdMzPu+I+LsA8xqqnPOlNJxbGYCJiWnfbvUmscYkfrJISoHEbnrMTo6nNcTAJRS0CESHAAQERGXUlJK27bFfm6lzj6iMIDdElPJteSqUzKnREyAkcJMlAELF51CRJHgiCjSkIhEAWu1RmFAdNUJaO5eSsk5m0ydgkzbtkVtY8brcXWg+/v7lIncyBTdETEynZmp6lJb/MDMbSltKUA+54zfuLsCMucIG1UVkUguRLBtl+fnZwBbT010RFAdxwAnd5xTzQARmTOlXGtlzkSQM7eliKmKqEjOmRimdNO5ro0IUknMfEvlKUXkpJTu7u5SSomZEIlozpkw6ZiZEwGYy93d3ZwTFN5q5FobMEA8BYBSUqkpylJmLInO5zXnzAyIAK4EGOvJzPu+l9JKKZF9EH32HVyPbY8tzAjb5WXfr4hABO7a98NEGSmllFKK6oLoKZG7gsZznIAYgXG5XE7LykgMCGaJkFLJR++IOOcUURNzx5wz5RT5cvaRczbXSEBEMOcUETBEp5xz7JBIeehAgABEieP5ufsYQ93Ub2u6vVwYUFWP43D3WpYogNvlOscouUXFC8D1/PwsIiZjXVdOZDJfXl5UVdVzqrHKcw5EZGYRKbVGdZ1zHscxRRzA0KJi65ScMyAGChi967RSCiKOMbZ9vzy/XK/XtTYRiXf48uXLnOpqER/LslwuFx1zHD2lgoju6qBIHvWty8wti9sx+vXYU0pAHrUI/JaAUkpRtMfoRKQuyBR1BswRUUTA8fHxMdBQSZkc3HXOici///77bccmNBN0iPUEgFLqGAPMkYAImLnWGlDRzJa1revq7u4GAC7OOYmIqvbezQM3gZmZCSckoloroiM6MxNhXEwUZAAY/ag5zzndvdaMiKJzXdf4uMgdY0hgh4if5bSKGxG5W0oJ3WK/RRr67bff4vZFxF0BIK4tHnRUOBONPMWMiGguxFBbdlcAG2MkJGaO+Il3i/0Zy66qzJgzL+fTkOnuUyUREgIRRXW8Xq+g5mazDzNQ1cLJzdw95zRGBzBOiIgPDw+Xy3OEXHxE750Y4oMQsbWiqlGtb4iYwEFviZ4QkUU1pdTaiq9fohpVM9oFIjivJ0QspTy8eywlMfNUEdPS8pAeey2el5kEaGLm0+lkoqqCCIg4pc8uCIDu6L5"
//        // Base64 인코딩된 문자열을 ByteArray로 디코딩
//        val decodedByteArray: ByteArray = Base64.decode(base64String, Base64.DEFAULT)
//        val vectimg: ByteArray = byteArrayOf()

// 최소값과 최대값을 설정합니다.
        val minValue = 0
        val maxValue = 10000

// 현재 값과 최소값, 최대값을 설정합니다.
// 위에서 minValue, maxValue, symptonProbability, asymptomaticProbability를 설정했다고 가정합니다.

        val symptomLevel = (symptonProbability * 10000 / maxValue).toInt()
        Log.d("symptomLevel", symptomLevel.toString())
        val aSymptomLevel = (asymptomaticProbability * 10000 / maxValue).toInt()
        Log.d("aSymptomLevel", aSymptomLevel.toString())


        binding.symptom.min = 0
        binding.symptom.max = 100
        binding.symptom.progress = symptomLevel

        binding.aSymptom.min = 0
        binding.aSymptom.max = 100
        binding.aSymptom.progress = aSymptomLevel

        binding.symptom.progressDrawable!!.level = (symptonProbability * 100).toInt()
        binding.aSymptom.progressDrawable!!.level = (asymptomaticProbability * 100).toInt()


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
        binding.symptonText.text = "${symptonProbability.toInt()}%"
        binding.asymptonText.text = "${asymptomaticProbability.toInt()}%"

        if (symptonProbability >= 50.0)   {
            binding.vectorSubT.text = "${namepet}의 눈은 꼼꼼한 관리가 필요해요!"
            binding.vectorCheck.text = "동물병원 방문을 추천드려요."
            val adapter = ResultAdapter(name!!, supportFragmentManager)
            binding.resultRecyclerView.adapter = adapter
            val layoutManager = LinearLayoutManager(this)
            binding.resultRecyclerView.layoutManager = layoutManager

        }
        else{
            binding.textView4.isVisible = false
            binding.vectorSubT.text = "${namepet}의 눈은 건강하게 관리되고 있어요!"
            binding.vectorCheck.text = "정기적으로 안구 검진을 부탁드려요."
            binding.resultRecyclerView.isVisible = false
            binding.diseaseExplain.text  = "진단 결과, 가능성 있는 질환이 나타나지 않습니다.\n\n다른 질환들이 궁금하시다면\n질병백과를 통해 확인해주세요:) "
            binding.resultBooks.setOnClickListener{
                val intent = Intent(applicationContext, VectList::class.java)
                startActivity(intent)
            }
        }

        binding.resultImg.setImageBitmap(resultimg)
//                binding.VectorDate.text = "진단 날짜\n $date "
//                binding.vectorName.text = "진단결과: $name"

//        val adapter = ResultAdapter(name!!, supportFragmentManager)
//        binding.resultRecyclerView.adapter = adapter
//        val layoutManager = LinearLayoutManager(this)
//        binding.resultRecyclerView.layoutManager = layoutManager

        Log.d("symptonProbability", symptonProbability.toString())


        binding.storeVector.setOnClickListener {
            VectorResultUpdate(vectimg, userId!!,namepet!!, vecdate2, name!! ,symptonProbability,"값이 없습니다.")
        }

        binding.reVector.setOnClickListener {
            val intent = Intent(this, VectorChoicePet::class.java)
            startActivity(intent)
            finish()
        }

        binding.resultBooks.setOnClickListener {
            val intent = Intent(this, VectList::class.java)
            startActivity(intent)
        }

    }

    private val vectserver = RetrofitApi2.retrofit2.create(VectResultService::class.java)
    fun VectorResultUpdate(decodedByteArray:ByteArray, userId:String, namepet:String, vecDate2 :String ,name:ArrayList<DiseaseName>, symptonProbability:Double,vectContent:String) {
        val fileBody = RequestBody.create("image/*".toMediaTypeOrNull(), decodedByteArray)
        val multipartBody: MultipartBody.Part? =
            MultipartBody.Part.createFormData("vectImg", "vectImg.jpeg", fileBody)
        val userIdR = userId.toRequestBody("text/plain".toMediaTypeOrNull())
        val namepetR = namepet.toRequestBody("text/plain".toMediaTypeOrNull())
        val vectDateR = vecDate2.toRequestBody("text/plain".toMediaTypeOrNull())
        val names = name.map { it.name } // Extracting 'name' property from each DiseaseName object
        val namesCombined = names.joinToString(", ") // Combining 'name' values into a single string
        val nameR = namesCombined.toRequestBody("text/plain".toMediaTypeOrNull())
//        val nameR = name.toRequestBody("text/plain".toMediaTypeOrNull())
        val vectContentR = vectContent.toRequestBody("text/plain".toMediaTypeOrNull())

        vectserver.vectResultService(multipartBody!!,userIdR, namepetR, vectDateR, nameR, symptonProbability,vectContentR ).enqueue(object : Callback<VectResultResponseDTO?> {
                override fun onResponse(
                    call: Call<VectResultResponseDTO?>,
                    response: Response<VectResultResponseDTO?>
                ) {
                    Log.d("진단 결과 저장", "" + response.body()?.status)
                    Toast.makeText(this@VectorResult, "저장되었습니다.", Toast.LENGTH_SHORT).show()
//                    intent = Intent(applicationContext, HomeActivity::class.java)
//                    startActivity(intent)

                }

                override fun onFailure(call: Call<VectResultResponseDTO?>, t: Throwable) {
                    Toast.makeText(applicationContext, "통신 실패", Toast.LENGTH_SHORT).show()
                    Log.d("에러", t.message!!)
                }
            })
        }

}



