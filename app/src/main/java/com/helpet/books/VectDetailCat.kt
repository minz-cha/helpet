package com.helpet.books

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.helpet.R
import com.helpet.databinding.ActivityVectDetailCatBinding

class VectDetailCat : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityVectDetailCatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("name")
        val symptoms = intent.getStringExtra("symptoms")
        val cause = intent.getStringExtra("cause")
        val treats = intent.getStringExtra("treats")

        binding.detailDisease.text= name
        binding.detailSymptoms.text = symptoms
        binding.detailCauses.text = cause
        binding.detailTreats.text = treats

        binding.otherBooks.setOnClickListener {
            val intent= Intent(this, VectList::class.java)
            startActivity(intent)
        }
    }

}