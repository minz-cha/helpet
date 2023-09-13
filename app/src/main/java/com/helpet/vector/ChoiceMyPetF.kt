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
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.multidex.MultiDex
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.card.MaterialCardView
import com.helpet.R
import com.helpet.books.DBHelper
import com.helpet.databinding.FragmentChoiceMyPetBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.FileInputStream
import java.io.IOException
import java.util.*
import android.util.Base64


class ChoiceMyPetF : Fragment() {

    private lateinit var Cadapter : ChoicePetAdapter
    private lateinit var dbHelper: PetHelper
    private var imgList : MutableList<String> = mutableListOf()
    private var nameList : MutableList<String> = mutableListOf()
    private var ageList : MutableList<Int> = mutableListOf()
    private var birthList : MutableList<String> = mutableListOf()
    private var genderList : MutableList<String> = mutableListOf()

    private lateinit var binding : FragmentChoiceMyPetBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dbHelper = PetHelper(context) // DBHelper 인스턴스를 생성합니다.
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        MultiDex.install(requireContext())
        binding = FragmentChoiceMyPetBinding.inflate(layoutInflater)

        val sharedPreferences = activity?.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val value = sharedPreferences?.getString("userId", "null")

        Log.d("value", value!!)

        fetchData(value)



        binding.petPlus.setOnClickListener { view->
            val popupMenu = PopupMenu(requireContext(), view) // 팝업 메뉴 생성

            // 메뉴 아이템 추가
            popupMenu.menuInflater.inflate(R.menu.pet_plus, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.plusPet -> {
                        // 우리 아이 등록하기
                        val intent = Intent(requireContext(), PetRegisterActivity::class.java)
                        startActivity(intent)
                        true // 클릭 처리 완료
                    }

                    else -> false
                }
            }
            popupMenu.show() // 팝업 메뉴 표시
        }


        return binding.root
    }

    fun fetchData(userId: String){
        if (!isAdded) {
            return
        }
        //유저가 이미 저장해둔 반려동물 정보 가져오는 데이터 값들
        val server3=  RetrofitApi2.retrofit2.create(GetPetService::class.java)

        server3.getPetRegister(userId).enqueue(object :retrofit2.Callback<petListResponseDTO>{
            @SuppressLint("SetTextI18n")
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(call: Call<petListResponseDTO?>, response: Response<petListResponseDTO?>){
                Log.d("반려동물 리스트", "" + response.body().toString())
                Log.d("개수", response.body()?.result?.size!!.toString())

                // 서버에서 가져온 데이터의 개수만큼 반복문을 실행합니다
                for (i in 0 until (response.body()?.result?.size!!)) {

                    val agepet = response.body()?.result!![i].petAge
                    val birthpet = response.body()?.result!![i].petBirth
                    val imgpet = response.body()?.result!![i].petImg
                    val namepet = response.body()?.result!![i].petName
                    val genderpet = response.body()?.result!![i].petGender
                    ageList.add(agepet)
                    birthList.add(birthpet)
                    imgList.add(imgpet)
                    nameList.add(namepet)
                    genderList.add(genderpet)

                    // 내장 데이터베이스에 데이터 저장
                    dbHelper.insertPetData(namepet, agepet, birthpet, genderpet, convertImageStringToByteArray(imgpet)!!)


                }
                Cadapter = ChoicePetAdapter(ageList, birthList, imgList, nameList, genderList)
                val layoutManager = LinearLayoutManager(requireContext())
                binding.choicePetRecycler.layoutManager = layoutManager
                binding.choicePetRecycler.adapter = Cadapter

//                Log.d("db",dbHelper.getAllPets().toString() )
            }

            override fun onFailure(call: Call<petListResponseDTO>, t: Throwable) {
                Log.d("에러", t.message!!)
            }
        })

    }

    fun convertImageStringToByteArray(imageString: String): ByteArray? {
        return try {
            Base64.decode(imageString, Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


}