package com.helpet.vector

import com.helpet.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_pet_register.*

class PetRegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_register)

        cameraAlt.clipToOutline=true

        okButton.setOnClickListener {
            Toast.makeText(this, "저장되었습니다", Toast.LENGTH_SHORT).show()
            val intent=Intent(this, VectorChoicePet::class.java)
            startActivity(intent)
        }

        closeButton.setOnClickListener {
            Toast.makeText(this, "취소되었습니다.", Toast.LENGTH_SHORT).show()
            val intent=Intent(this, VectorChoicePet::class.java)
            startActivity(intent)
        }
    }
}