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
import androidx.lifecycle.lifecycleScope
import androidx.multidex.MultiDex
import com.helpet.R
import com.helpet.databinding.FragmentChoiceMyPetBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.*


class ChoiceMyPetF : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        MultiDex.install(requireContext())
        val binding = FragmentChoiceMyPetBinding.inflate(layoutInflater)

        val sharedPreferences = activity?.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val value = sharedPreferences?.getString("userId", "null")

        val textuser = value.toString()
        Log.d("value", value!!)

        lifecycleScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    RetrofitApi2.retrofit2.create(GetPetService::class.java).getPetRegister(textuser)
                }

                response.body()?.let { petListResponse ->
                    Log.d("반려동물 리스트", petListResponse.result.toString())
                    Log.d("개수", petListResponse.result.size.toString())

                    for (pet in petListResponse.result) {
                        val agepet = pet.petAge
                        val birthpet = pet.petBirth
                        val imgpet = pet.petImg
                        val namepet = pet.petName
                        val genderpet = pet.petGender

                        val layoutpet = createLayout(imgpet, namepet, agepet, birthpet, genderpet)
                        binding.mypetLayout.addView(layoutpet)
                    }
                }
            } catch (e: Exception) {
                Log.d("에러", e.message!!)
            }
        }

        binding.mypetRegister.setOnClickListener {
            val intent = Intent(requireContext(), PetRegisterActivity::class.java)
            startActivity(intent)
        }

        return binding.root
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

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("InflateParams", "SetTextI18n")
    fun createLayout(imgpet: String, namepet: String, agepet :Int, birthpet:String,genderpet:String ) :View{
        val inflater = requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layout = inflater.inflate(R.layout.activity_sub_pet2, null) as LinearLayout


        val mychoicePetImg = layout.findViewById<ImageView>(R.id.mychoicePetImg)
        val mychoicePetName = layout.findViewById<TextView>(R.id.mychoicePetName)
        val mychoicePetAge = layout.findViewById<TextView>(R.id.mychoicePetAge)
        val mychoicePetBirth = layout.findViewById<TextView>(R.id.mychoicePetBirth)
//        val mychoicePet = layout.findViewById<ImageView>(R.id.mychoicePet)


        mychoicePetImg.setImageBitmap(stringToBitmap(imgpet))
        mychoicePetName.text = namepet
        mychoicePetAge.text = "나이 : $agepet 살"
        mychoicePetBirth.text = "생일: $birthpet "
//        mychoicePet.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
//        mychoicePet.setImageResource(R.drawable.petchoice)
//        mychoicePet.setPadding(0, 0, 0, 0)

        layout.setOnClickListener {
            val intent = Intent(context, PetInfActivity::class.java)
            intent.putExtra("imgpet", imgpet)
            intent.putExtra("namepet", namepet)
            intent.putExtra("agepet", agepet)
            intent.putExtra("birthpet", birthpet)
            intent.putExtra("genderpet", genderpet)
            startActivity(intent)
        }

        return layout

    }

}