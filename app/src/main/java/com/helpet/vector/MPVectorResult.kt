package com.helpet.vector

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.helpet.R
import kotlinx.android.synthetic.main.activity_mpvector_result.*

class MPVectorResult : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mpvector_result)

        mpBack.setOnClickListener {
            val intent = Intent(this, PetInfActivity::class.java)
            startActivity(intent)
        }
    }
}