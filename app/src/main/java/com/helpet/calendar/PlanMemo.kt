package com.helpet.calendar


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.helpet.R
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class PlanMemo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plan_memo)

        val tvtodayDate = findViewById<TextView>(R.id.tvtodayDate)
        val date = intent.getStringExtra("date")
        val btnClose = findViewById<Button>(R.id.btnClose)

        tvtodayDate.text = date
        setResult(RESULT_CANCELED)

        btnClose.setOnClickListener {
            finish()
        }

        val edtTitle: EditText = findViewById(R.id.edtTitle)
        val edtPlan: EditText = findViewById(R.id.edtPlan)
        val btnSave: Button = findViewById(R.id.btnSave)

        btnSave.setOnClickListener {
            val title = edtTitle.text.toString()
            val description = edtPlan.text.toString()

            // Post로 Login ID, Date, Title, Description
            val dateFormat = SimpleDateFormat("YYYY.MM.DD", Locale.getDefault())
            val currentDate = dateFormat.format(Date())

            val url = URL("http://localhost:3000/auth/register_process")
            val postData = "LoginID=id & date=currentDate & title=title & description=description"

            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "POST"
            conn.doOutput = true
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
            conn.setRequestProperty("Content-Length", postData.length.toString())
            conn.useCaches = false

            DataOutputStream(conn.outputStream).use { it.writeBytes(postData) }
            BufferedReader(InputStreamReader(conn.inputStream)).use { br ->
                var line: String?
                while (br.readLine().also { line = it } != null) {
                    println(line)
                }
            }

            var result = true

            if (result) {
                var intent = Intent()
                intent.putExtra("title", title)
                setResult(RESULT_OK, intent)
            }
            finish()
        }
    }
}