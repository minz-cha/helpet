package com.helpet.login

import android.content.Context
import androidx.appcompat.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.helpet.R
import com.helpet.vector.HomeActivity

class Login : AppCompatActivity() {
    val TAG: String = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val btn_login : Button = findViewById(R.id.btn_login)
        val btn_register : Button = findViewById(R.id.btn_register)
        val edit_id : EditText = findViewById(R.id.edit_id)
        val edit_pw : EditText = findViewById(R.id.edit_pw)
        val btn_find_id_pw : Button = findViewById(R.id.btn_find_id_pw)
        val checkbox_login : CheckBox = findViewById(R.id.checkbox_login)




        btn_login.setOnClickListener { // 로그인하기
            val intent= Intent(this, HomeActivity::class.java)
            startActivity(intent)

            var id = edit_id.text.toString()
            var pw = edit_pw.text.toString()

            val sharedPreference = getSharedPreferences("file name", Context.MODE_PRIVATE)
            val savedId = sharedPreference.getString("id", "")
            val savedPw = sharedPreference.getString("pw", "")

            // 유저가 입력한 id, pw값과 쉐어드로 불러온 id, pw값 비교
            if(id == savedId && pw == savedPw){
                dialog("success")
            }
            else{
                dialog("fail")
            }


        }

        // 회원가입 버튼
        btn_register.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }


        //아이디 비번 찾기 버튼
        btn_find_id_pw.setOnClickListener {
            val intent = Intent(this, Find_idpw::class.java)
            startActivity(intent)
        }

        //로그인 정보 저장 체크박스
        checkbox_login.setOnCheckedChangeListener { button, isChecked ->  }

    }
    
    

    // 로그인 성공/실패 시 다이얼로그를 띄워주는 메소드
    fun dialog(type: String){
        var dialog = AlertDialog.Builder(this)


        if(type.equals("success")){
            dialog.setTitle("로그인 성공")
            dialog.setMessage("로그인 성공")
        }
        else if(type.equals("fail")){
            dialog.setTitle("로그인 실패")
            dialog.setMessage("아이디와 비밀번호를 확인해주세요")
        }

        var dialog_listener = object: DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                when(which){
                    DialogInterface.BUTTON_POSITIVE ->
                        Log.d(TAG, "")
                }
            }
        }

        dialog.setPositiveButton("확인",dialog_listener)
        dialog.show()
    }
}
