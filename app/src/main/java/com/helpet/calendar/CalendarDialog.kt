package com.helpet.calendar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.helpet.R
import com.helpet.databinding.ActivityCalendarDialogBinding

class CalendarDialog : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityCalendarDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("hi", "hi")
        binding.RegisterCal.setOnClickListener {
            val intent = Intent(this, PlanMemo::class.java)
            startActivity(intent)
        }






    }
}