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
import androidx.core.content.ContextCompat
import com.helpet.R
import com.helpet.vector.HomeActivity
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.*

class Login : AppCompatActivity() {
    val TAG: String = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val btn_login: Button = findViewById(R.id.btn_success)
//        val btn_register: Button = findViewById(R.id.btn_register)
//        val btn_find_id_pw: Button = findViewById(R.id.btn_find_id_pw)
//        val checkbox_login: CheckBox = findViewById(R.id.checkbox_login)
        val editId: EditText = findViewById(R.id.edit_id)
        val editPw: EditText = findViewById(R.id.edit_pw)




        btn_login.setOnClickListener { // 로그인하기
            val userId = editId.text.toString()
            val password = editPw.text.toString()
            Log.d("test", userId)
            Log.d("test", password)


            val server = RetrofitInterface.retrofit.create(LoginService::class.java)

            server.LoginResult( userId, password).enqueue(object :
                Callback<LogResponseDTO?> {
                override fun onResponse(call: Call<LogResponseDTO?>?, response: Response<LogResponseDTO?>) {
                    val result = response.body()
                    val success : Boolean? = response.body()?.success
                    Log.d("retrofit 로그인 성공 유무", "${result}")
                    if (success.toString() == "true") {
                        val intent = Intent(applicationContext ,HomeActivity::class.java)
                        startActivity(intent)

                        // SharedPreferences에 userId 저장
                        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putString("userId", userId) // 로그인한 userId를 저장

                        // "로그인 유지" 옵션을 선택한 경우, 체크박스 상태를 저장
//                        val isLoginChecked = checkbox_login.isChecked
//                        editor.putBoolean("isLoginChecked", isLoginChecked)
//
                        editor.apply()

                    } else if (success.toString() == "false") {
                        Toast.makeText(applicationContext, "아이디 혹은 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show()

                    }
                    Log.d("retrofit 로그인", "${result}")

                }

                override fun onFailure(call: Call<LogResponseDTO?>?, t: Throwable) {
                    Log.d("에러", t.message!!)

                }
            })

//
//            // 회원가입 버튼
//            btn_register.setOnClickListener {
//                Log.d("test","회원가입")
//                val intent = Intent(this, Register::class.java)
//                startActivity(intent)
//            }
//
//
//            //아이디 비번 찾기 버튼
//            btn_find_id_pw.setOnClickListener {
//                val intent = Intent(this, Find_idpw::class.java)
//                startActivity(intent)
//            }
//
        }



        // 로그인 성공/실패 시 다이얼로그를 띄워주는 메소드
        fun dialog(type: String) {
            var dialog = AlertDialog.Builder(this)

            if (type.equals("success")) {
                dialog.setTitle("로그인 성공")
                dialog.setMessage("로그인 성공")
            } else if (type.equals("fail")) {
                dialog.setTitle("로그인 실패")
                dialog.setMessage("아이디와 비밀번호를 확인해주세요")
            }

            var dialog_listener = object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    when (which) {
                        DialogInterface.BUTTON_POSITIVE ->
                            Log.d(TAG, "")
                    }
                }
            }

            dialog.setPositiveButton("확인", dialog_listener)
            dialog.show()
        }
    }
}


interface LoginService{
    @FormUrlEncoded
    @POST("auth/login")
    fun LoginResult(
        @Field("userId") userId: String,
        @Field("password") password: String
    ): Call<LogResponseDTO?>
}



