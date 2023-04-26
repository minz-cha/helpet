package com.helpet.vector

import com.helpet.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_vector_choice_pet.*

class VectorChoicePet : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vector_choice_pet)

        petRegister.setOnClickListener {
            val intent= Intent(this, PetRegisterActivity::class.java  )
            startActivity(intent)
        }
        petChoice.setOnClickListener {
            val intent=Intent(this,VectorCamera::class.java)
            startActivity(intent)
        }
//        ex1.setOnClickListener {
//            val intent= Intent(this, VectorCamera::class.java  )
//            startActivity(intent)
//        }

//        petPlus.setOnClickListener {
//            val intent=Intent(this, PetRegisterActivity::class.java)
//            startActivity(intent)
//        }
    }
}