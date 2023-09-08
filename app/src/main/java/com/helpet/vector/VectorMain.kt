package com.helpet.vector

import android.app.Dialog
import com.helpet.R
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.helpet.Hospital.HospitalActivity
import com.helpet.books.VectList
import com.helpet.databinding.FragmentVectorMainBinding


class VectorMain : Fragment(), View.OnClickListener {

    private var dialog: Dialog? = null
    private lateinit var binding : FragmentVectorMainBinding

    private lateinit var adapter : ViewPagerAdapter

    // 3초마다 자동 슬라이드 시작
    val handler = Handler()
    private val runnable = object : Runnable {
        override fun run() {
            val currentItem = binding.viewPager2.currentItem
            val nextItem = if (currentItem < adapter.itemCount - 1) currentItem + 1 else 0
            binding.viewPager2.currentItem = nextItem
            handler.postDelayed(this, 3000) // 3초마다 실행
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentVectorMainBinding.inflate(layoutInflater)
//        val hospital1 = binding.root.findViewById<ImageButton>(R.id.hospital)

        adapter = ViewPagerAdapter(requireActivity())
        binding.viewPager2.adapter = adapter

        handler.postDelayed(runnable, 3000)

        binding.vectorEye.setOnClickListener {
            val intent= Intent(requireContext(), VectorChoicePet::class.java)
            startActivity(intent)
        }
//
//        hospital1.setOnClickListener {
//            val intent = Intent(requireContext(), HospitalActivity::class.java)
//            startActivity(intent)
//        }
        binding.mainBooks.setOnClickListener {
            val intent = Intent(requireContext(), VectList::class.java)
            startActivity(intent)
        }

        binding.camGuide.setOnClickListener {
            dialog = Dialog(requireContext())    // Dialog 초기화
            dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE) // 타이틀 제거
            dialog!!.setContentView(R.layout.activity_cam_guide) // xml 레이아웃 파일과 연결

            dialog!!.show()
        }



        return binding.root
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.vector_eye -> {
                val intent = Intent(requireActivity(), VectorChoicePet::class.java)
                startActivity(intent)
            }
//            R.id.vector_eye ->{
//                val intent =Intent(activity, VectorChoicePet::class.java)
//                startActivity(intent)
//            }

        }
    }

    override fun onPause() {
        super.onPause()
        Log.d("pause", "pause")
        handler.removeCallbacks(runnable)
    }
}





