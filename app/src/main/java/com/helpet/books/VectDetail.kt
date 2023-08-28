package com.helpet.books

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.helpet.R
import com.helpet.databinding.ActivityVectDetailBinding

class VectDetail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityVectDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}