package com.helpet.vector

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.helpet.R
import com.helpet.databinding.FragmentTotalPetBinding
import retrofit2.Call
import retrofit2.Response


class TotalPet : Fragment() {

    private lateinit var binding: FragmentTotalPetBinding
    val gson = Gson()
    val bundle = Bundle()
    var result : petListResponseDTO? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTotalPetBinding.inflate(layoutInflater)

        // SharedPreferences 객체 생성
        val sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        // 유저아이디 데이터 읽기
        val value = sharedPreferences.getString("userId", "null")
        Log.d("value", value!!)


        //유저가 이미 저장해둔 반려동물 정보 가져오는 데이터 값들
        val server3 = RetrofitApi2.retrofit2.create(GetPetService::class.java)

        server3.getPetRegister(value).enqueue(object : retrofit2.Callback<petListResponseDTO> {
            @SuppressLint("SetTextI18n")
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(call: Call<petListResponseDTO>, response: Response<petListResponseDTO>) {
                Log.d("반려동물 리스트", "" + response.body().toString())
                Log.d("개수", response.body()?.result?.size!!.toString())


                result = response.body()

                // 어댑터 생성 및 연결
                val adapter = PetAdapter(result!!.result)
                binding.totalRecyclerView.adapter = adapter

            }

            override fun onFailure(call: Call<petListResponseDTO>, t: Throwable) {
                Log.d("에러", t.message!!)
            }
        })


        return binding.root

    }

}