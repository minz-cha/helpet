package com.helpet.Hospital

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.helpet.R
import com.naver.maps.map.NaverMap

class HospitalApiFragment : Fragment(), com.naver.maps.map.OnMapReadyCallback {
    private lateinit var naverMap: NaverMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_hospital_api, container, false)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as com.naver.maps.map.MapFragment?
            ?: com.naver.maps.map.MapFragment.newInstance().also {
                childFragmentManager.beginTransaction().add(R.id.hospitalLayout, it).commit()
            }

        mapFragment.getMapAsync(this)

        return rootView
    }
    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap

        // 맵 초기화 작업 수행
        // ...
    }
}