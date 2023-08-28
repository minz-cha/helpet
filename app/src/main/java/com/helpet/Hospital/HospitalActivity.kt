package com.helpet.Hospital

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.helpet.R
import com.helpet.databinding.ActivityHospitalBinding
import com.helpet.vector.HomeActivity
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class HospitalActivity : AppCompatActivity() {
    var fragment0: Fragment? = null
    var fragment1: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityHospitalBinding.inflate(layoutInflater)
        setContentView(binding.root)


        /* 키 해시 얻기*/try {
            val info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("키해시는 :", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }


        binding.back.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        fragment0 = HospitalApiFragment()
        fragment1 = HospitalListFragment()

        supportFragmentManager.beginTransaction().add(R.id.hospitalLayout, fragment0!!).commit()

        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val position = tab.position
                var selected: Fragment? = null
                if (position == 0) {
                    selected = fragment0
                } else if (position == 1) {
                    selected = fragment1
                }
                supportFragmentManager.beginTransaction().replace(R.id.hospitalLayout, selected!!)
                    .commit()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

    }
}