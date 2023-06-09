package com.helpet.login

import android.content.ContentValues
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color.red
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.CheckResult
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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

        val server1 = retrofit.create(IDCheckService::class.java)

        btn_Dup_check.setOnClickListener {
            val userId = userId.text.toString()
            server1.checkIDResult(userId).enqueue(object : Callback<IdCheckResult?> {
                override fun onResponse(call: Call<IdCheckResult?>?, response: Response<IdCheckResult?>) {
                    val result = response.body()
                    val success : Boolean? = response.body()?.success
                    Log.d("retrofit ID 중복 확인", "${result}")
                    if (success.toString() == "true") {
                        id_check_result.setTextColor(ContextCompat.getColor(applicationContext, R.color.blue))
                        id_check_result.text = "이용 가능한 아이디입니다."
                    } else if (success.toString() == "false") {
                        id_check_result.setTextColor(ContextCompat.getColor(applicationContext, R.color.red))
                        id_check_result.text = "이미 존재하는 아이디입니다."
                    }
                }

                override fun onFailure(call: Call<IdCheckResult?>?, t: Throwable) {
                    Log.d("중복 확인", t.message!!)

                }
            })
        }

        val server2 = retrofit.create(NicknameCheckService::class.java)

        btn_Dup_check2.setOnClickListener {
            val nickname = nickname.text.toString()
            server2.checkNicknameResult(nickname).enqueue(object : Callback<NickCheckResult?> {
                override fun onResponse(call: Call<NickCheckResult?>?, response: Response<NickCheckResult?>) {
                    val result = response.body()
                    val success : Boolean? = response.body()?.success
                    Log.d("retrofit Nick 중복 확인", "${result}")
                    if (success.toString() == "true") {
                        nick_check_result.setTextColor(ContextCompat.getColor(applicationContext, R.color.blue))
                        nick_check_result.text = "이용 가능한 닉네임입니다."
                    } else if (success.toString() == "false") {
                        nick_check_result.setTextColor(ContextCompat.getColor(applicationContext, R.color.red))
                        nick_check_result.text = "이미 존재하는 닉네임입니다."
                    }
                }

                override fun onFailure(call: Call<NickCheckResult?>?, t: Throwable) {
                    Log.d("닉네임 중복 확인", t.message!!)

                }
            })
            //회원정보 DB에 존재하는 ID들과 입력된 ID가 동일하지 않으면 '이용 가능한 아이디입니다.' 입력된 ID가 이미 존재하면 '이미 존재하는 아이디입니다.'라는 메시지
            //추가적으로, 회원가입의 '완료' 버튼을 누르면 항목채우기, pw==pwcheck 말고도 '중복 확인'을 필수로 해야한다는 코드 필요
        }

        val server3 = retrofit.create(RegisterService::class.java)

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

            server3.RegisterResult(username, phone, userId, password, nickname).enqueue(object : Callback<RegResponseDTO?> {
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

interface IDCheckService {
    @FormUrlEncoded
    @POST("auth/id-check")
    fun checkIDResult(
        @Field("id") id: String
    ): Call<IdCheckResult>
}

interface NicknameCheckService {
    @FormUrlEncoded
    @POST("auth/nickname-check")
    fun checkNicknameResult(
        @Field("nickname") nickname: String
    ): Call<NickCheckResult>
}

