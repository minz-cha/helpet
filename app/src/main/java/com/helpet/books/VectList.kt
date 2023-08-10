package com.helpet.books

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.helpet.Hospital.HospitalApiFragment
import com.helpet.Hospital.HospitalListFragment
import com.helpet.R
import com.helpet.databinding.ActivityVectListBinding

class VectList : AppCompatActivity() {
    var fragment0: Fragment? = null
    var fragment1: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityVectListBinding.inflate(layoutInflater)
        setContentView(binding.root)


        fragment0 = VectSubDogList()
        fragment1 = VectSubCatList()


        supportFragmentManager.beginTransaction().add(R.id.vectBooksFrameLayout, fragment0!!).commit()

        binding.booksTabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val position = tab.position
                var selected: Fragment? = null
                if (position == 0) {
                    selected = fragment0
                }
                else if (position == 1) {
                    selected = fragment1
                }
                supportFragmentManager.beginTransaction().replace(R.id.vectBooksFrameLayout, selected!!).commit()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

    }
}