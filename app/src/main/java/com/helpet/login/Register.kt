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
import com.helpet.login.RetrofitInterface.retrofit
import com.helpet.vector.ResponseDto
import com.helpet.vector.RetrofitApi
import com.helpet.vector.VectorService
import kotlinx.android.synthetic.main.register.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.*



class Register : AppCompatActivity() {

    val TAG: String = "Register"
    var isExistBlank = false
    var isPWSame = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        val username: EditText = findViewById(R.id.edit_name)
        val phone: EditText = findViewById(R.id.edit_phonenum)
        val userId: EditText = findViewById(R.id.edit_id)
        val password: EditText = findViewById(R.id.edit_pw)
        val edit_pwcheck: EditText = findViewById(R.id.edit_pwcheck)
        val nickname: EditText = findViewById(R.id.edit_nick)
        val btn_success: Button = findViewById(R.id.btn_success)


        btn_Dup_check.setOnClickListener {
            //회원정보 DB에 존재하는 ID들과 입력된 ID가 동일하지 않으면 '이용 가능한 아이디입니다.' 입력된 ID가 이미 존재하면 '이미 존재하는 아이디입니다.'라는 메시지
            //추가적으로, 회원가입의 '완료' 버튼을 누르면 항목채우기, pw==pwcheck 말고도 '중복확인'을 필수로 해야한다는 코드 필요
        }

        val server = retrofit.create(RegisterService::class.java)

        btn_success.setOnClickListener {
            val username = username.text.toString()
            val phone = phone.text.toString()
            val userId = userId.text.toString()
            val password = password.text.toString()
            val nickname = nickname.text.toString()

            // 유저가 항목을 다 채우지 않았을 경우
            if (edit_name.text.isBlank() || edit_phonenum.text.isEmpty() || edit_id.text.isEmpty() || edit_pw.text.isEmpty() || edit_pwcheck.text.isEmpty() || edit_nick.text.isEmpty()) {
                isExistBlank = true
            } else {
                if (edit_pw == edit_pwcheck) {
                    isPWSame = true
                }
            }

            server.RegisterResult(username, phone, userId, password, nickname).enqueue(object : Callback<RegResponseDTO?> {
                override fun onResponse(call: Call<RegResponseDTO?>?, response: Response<RegResponseDTO?>) {
                    val result = response.body()
                    Log.d("retrofit 회원가입", "${result}")
                    val intent = Intent(applicationContext, Login::class.java)
                    startActivity(intent)
                }

                override fun onFailure(call: Call<RegResponseDTO?>?, t: Throwable) {
                    Log.d("회원가입", t.message!!)

                }
            })

        }
    }

}

interface RegisterService{
    @FormUrlEncoded
    @POST("auth/register")
    fun RegisterResult(
        @Field("username") username: String,
        @Field("phone") phone: String,
        @Field("userId") userId : String,
        @Field("password") password: String,
        @Field("nickname") nickname: String

    ): Call<RegResponseDTO>
}

