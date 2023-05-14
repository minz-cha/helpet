package com.helpet.Hospital

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.helpet.R
import com.naver.maps.map.NaverMap
import kotlinx.android.synthetic.main.activity_hospital.*
import kotlinx.android.synthetic.main.fragment_hospital_api.*
import org.jetbrains.annotations.Nullable


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HospitalApiFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
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