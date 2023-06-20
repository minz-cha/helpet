package com.helpet.vector

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.helpet.R
import kotlinx.android.synthetic.main.activity_sub_pet2.*
import kotlinx.android.synthetic.main.activity_vector_choice_pet.*
import kotlinx.android.synthetic.main.fragment_choice_my_pet.*
import retrofit2.Call
import retrofit2.Response
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.*


class ChoiceMyPetF : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_choice_my_pet, container, false)
        // view 객체를 반환하고, 여기서 필요한 뷰를 findViewById()를 통해 찾을 수 있습니다.

        // SharedPreferences 객체 생성
        val sharedPreferences = activity?.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        // 유저아이디 데이터 읽기
        val value = sharedPreferences?.getString("userId", "null")

        Log.d("value",value!!)

           //유저가 이미 저장해둔 반려동물 정보 가져오는 데이터 값들
        val textuser = value.toString()
        val server3=  RetrofitApi2.retrofit2.create(GetPetService::class.java)

        server3.getPetRegister(textuser).enqueue(object :retrofit2.Callback<petListResponseDTO>{
            @SuppressLint("SetTextI18n")
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(call: Call<petListResponseDTO?>?, response: Response<petListResponseDTO?>){
                Log.d("반려동물 리스트", "" + response.body().toString())
                Log.d("개수", response.body()?.result?.size!!.toString())

                // 서버에서 가져온 데이터의 개수만큼 반복문을 실행합니다
                for (i in 0 until (response.body()?.result?.size!!)) {


                    val agepet = response.body()?.result?.get(i)?.petAge
                    val birthpet = response.body()?.result?.get(i)?.petBirth
                    val imgpet = response.body()?.result?.get(i)?.petImg
                    Log.d("imgpet", imgpet.toString())
//                  val imgpet2 = stringToBitmap(imgpet!!)
                    val namepet = response.body()?.result?.get(i)?.petName
                    val genderpet = response.body()?.result?.get(i)?.petGender

                    val layoutpet = createLayout(namepet!!, agepet!!, birthpet!!, genderpet!!)
                    mypetLayout.addView(layoutpet)

//                    //리스트가 눌리면 해당 동물 종이 고양이인지 강아지인지 같이 넘겨줌
//                    mypetLayout.setOnClickListener {
//                        val intent = Intent(requireContext(), PetInfActivity::class.java)
//                        intent.putExtra("namepet", namepet)
//                        intent.putExtra("agepet", agepet)
//                        intent.putExtra("birthpet", birthpet)
//                        intent.putExtra("genderpet", genderpet)
//
//                        startActivity(intent)
//                    }


                }
                mypetRegister.setOnClickListener {
                    Log.d("hi","hi")
                    val intent= Intent(requireContext(), PetRegisterActivity::class.java  )
                    startActivity(intent)
                }
            }
            override fun onFailure(call: Call<petListResponseDTO>, t: Throwable) {
                Log.d("에러", t.message!!)
            }
        })

        return view
    }
    //bitmap 을  string 형태로 변환하는 메서드 (이렇게 string 으로 변환된 데이터를 mysql 에서 longblob 의 형태로 저장하는식으로 사용가능)
    @RequiresApi(Build.VERSION_CODES.O)
    fun bitmapToString(bitmap: Bitmap): String? {
        var image = ""
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val byteArray = stream.toByteArray()
        image = Base64.getEncoder().encodeToString(byteArray)
        return image
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

    @SuppressLint("InflateParams", "SetTextI18n")
    fun createLayout(namepet: String, agepet :Int, birthpet:String,genderpet:String ) :View{
        val inflater = requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
        val layout = inflater?.inflate(R.layout.activity_sub_pet2, null) as LinearLayout


        val mychoicePetImg = layout.findViewById<ImageView>(R.id.mychoicePetImg)
        val mychoicePetName = layout.findViewById<TextView>(R.id.mychoicePetName)
        val mychoicePetAge = layout.findViewById<TextView>(R.id.mychoicePetAge)
        val mychoicePetBirth = layout.findViewById<TextView>(R.id.mychoicePetBirth)
        val mychoicePet = layout.findViewById<ImageView>(R.id.mychoicePet)


        mychoicePetImg.setImageResource(R.drawable.ex2)
        mychoicePetName.text = namepet
        mychoicePetAge.text = "나이 : $agepet 살"
        mychoicePetBirth.text = "생일: $birthpet "
        mychoicePet.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
        mychoicePet.setImageResource(R.drawable.petchoice)
        mychoicePet.setPadding(0, 0, 0, 0)

        layout.setOnClickListener {
            val intent = Intent(context, PetInfActivity::class.java)
            intent.putExtra("namepet", namepet)
            intent.putExtra("agepet", agepet)
            intent.putExtra("birthpet", birthpet)
            intent.putExtra("genderpet", genderpet)
            startActivity(intent)
        }

        return layout

    }

}