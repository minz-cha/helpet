package com.helpet.login

import android.content.ContentValues
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.helpet.R
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread


class Register : AppCompatActivity() {

    val TAG: String = "Register"
    var isExistBlank = false
    var isPWSame = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        val edit_name : EditText = findViewById(R.id.edit_name)
        val edit_phonenum : EditText = findViewById(R.id.edit_phonenum)
        val edit_id : EditText = findViewById(R.id.edit_id)
        val edit_pw : EditText = findViewById(R.id.edit_pw)
        val edit_pwcheck : EditText = findViewById(R.id.edit_pwcheck)
        val edit_nick : EditText = findViewById(R.id.edit_nick)
        val btn_success : Button = findViewById(R.id.btn_success)




        btn_success.setOnClickListener {
            // 유저가 항목을 다 채우지 않았을 경우
            if (edit_name.text.isBlank() || edit_phonenum.text.isEmpty() || edit_id.text.isEmpty() || edit_pw.text.isEmpty() || edit_pwcheck.text.isEmpty() || edit_nick.text.isEmpty()) {
                isExistBlank = true
            } else {
                if (edit_pw == edit_pwcheck) {
                    isPWSame = true
                }
            }


            if (!isExistBlank && isPWSame) {

                // 회원가입 성공 토스트 메세지 띄우기
                Toast.makeText(this, "회원가입 성공", Toast.LENGTH_SHORT).show()

                // 유저가 입력한 id, pw를 쉐어드에 저장한다.
                val sharedPreference = getSharedPreferences("file name", Context.MODE_PRIVATE)
                val editor = sharedPreference.edit()
                editor.putString("id", edit_id.toString())
                editor.putString("pw", edit_pw.toString())
                editor.apply()

                // 로그인 화면으로 이동
                val intent = Intent(this, Login::class.java)
                startActivity(intent)

            } else {

                // 상태에 따라 다른 다이얼로그 띄워주기
                if (isExistBlank) {   // 작성 안한 항목이 있을 경우
                    dialog("blank")
                } else if (!isPWSame) { // 입력한 비밀번호가 다를 경우
                    dialog("not same")
                }
            }

            val url = URL("http://localhost:3000/auth/register_process")
            val postData = "name=test1 & phonenum=test2 & id=test3 & pw=test4 & nick=test5"

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

            val input = BufferedInputStream(conn.inputStream)
            val buffer = ByteArray(1024)
            val stringBuilder = StringBuilder()
            var read = input.read(buffer, 0, 1024)
            while (read >= 0) {
                stringBuilder.append(String(buffer, 0, read))
                read = input.read(buffer, 0, 1024)
            }
            val response = JSONObject(stringBuilder.toString())
            val result = response.getString("result")

        }

    }
    fun dialog(type: String){
        val dialog = AlertDialog.Builder(this)

        // 작성 안한 항목이 있을 경우
        if(type.equals("blank")){
            dialog.setTitle("회원가입 실패")
            dialog.setMessage("입력란을 모두 작성해주세요")
        }
        // 입력한 비밀번호가 다를 경우
        if(type.equals("not same")){
            dialog.setTitle("회원가입 실패")
            dialog.setMessage("비밀번호가 다릅니다")
        }

        val dialog_listener = object: DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                when(which){
                    DialogInterface.BUTTON_POSITIVE ->
                        Log.d(ContentValues.TAG, "다이얼로그")
                }
            }
        }

        dialog.setPositiveButton("확인",dialog_listener)
        dialog.show()
    }
}


