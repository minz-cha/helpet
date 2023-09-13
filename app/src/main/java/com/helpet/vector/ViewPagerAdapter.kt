package com.helpet.vector

import androidx.fragment.app.Fragment
import com.helpet.R
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    private val imagePaths  = listOf(
        "android.resource://${fragmentActivity.packageName}/${R.drawable.viewpager3}",
        "android.resource://${fragmentActivity.packageName}/${R.drawable.viewpager4}",
        "android.resource://${fragmentActivity.packageName}/${R.drawable.viewpager5}",
        "android.resource://${fragmentActivity.packageName}/${R.drawable.viewpager6}",
        "android.resource://${fragmentActivity.packageName}/${R.drawable.viewpager7}"
    )

    override fun getItemCount(): Int {
        return imagePaths.size
    }

    override fun createFragment(position: Int): Fragment {
        return ImageFragment.newInstance(imagePaths[position])
    }
}