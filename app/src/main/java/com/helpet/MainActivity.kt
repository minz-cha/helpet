package com.helpet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.helpet.databinding.ActivityMainBinding
import com.helpet.login.Login
import com.helpet.login.Register

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainLogin.setOnClickListener{
            val intent= Intent(this, Login::class.java )
            startActivity(intent)
        }

        binding.mainRegister.setOnClickListener {
            val intent = Intent(this,Register::class.java)
            startActivity(intent)
        }
    }
}