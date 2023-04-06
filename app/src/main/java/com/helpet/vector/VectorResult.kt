package com.helpet.vector

import com.helpet.R


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_vector_result.*

class VectorResult : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vector_result)

        storeVector.setOnClickListener {
            Toast.makeText(this@VectorResult, "저장되었습니다.",  Toast.LENGTH_SHORT).show()
        }


        goBooks.setOnClickListener {
            val intent=Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
        resultImg.clipToOutline=true


    }
}