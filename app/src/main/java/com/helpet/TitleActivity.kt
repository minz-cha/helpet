package com.helpet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.helpet.vector.HomeActivity

class TitleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.MyApplication)
//        installSplashScreen()
        super.onCreate(savedInstanceState)

        val intent= Intent(this, MainActivity::class.java)
        startActivity(intent)
//        setContentView(R.layout.activity_title)
    }
}