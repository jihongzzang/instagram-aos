package com.example.mycrudapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Gravity
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class InstaLoginActivity : AppCompatActivity() {
    var username: String = ""
    var password: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insta_login)

        val retrofit =
            Retrofit.Builder().baseUrl("http://mellowcode.org/").addConverterFactory(GsonConverterFactory.create())
                .build()

        val retrofitService = retrofit.create(RetrofitService::class.java)

        findViewById<EditText>(R.id.id_input).doAfterTextChanged {
            username = it.toString()
        }

        findViewById<EditText>(R.id.pw_input).doAfterTextChanged {
            password = it.toString()
        }

        findViewById<TextView>(R.id.login_btn).setOnClickListener {
            val user = HashMap<String, Any>()
            user.put("username", username)
            user.put("password", password)

            retrofitService.instaLogin(user).enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        val user = response.body()!!
                        val sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE)
                        val editor: SharedPreferences.Editor = sharedPreferences.edit()
                        editor.putString("token", user.token)
                        editor.putString("user_id", user.id.toString())
                        editor.commit()

                        startActivity(Intent(this@InstaLoginActivity, InstaMainActivity::class.java))
                    } else if (response.code() == 500) {
                        var toast =
                            Toast.makeText(
                                this@InstaLoginActivity,
                                "서버에 오류가 발생했습니다. 잠시 후 다시 시도해주세요.",
                                Toast.LENGTH_SHORT
                            )
                        toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 200) // 위에서 200px 아래로
                        toast.show()
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    var toast =
                        Toast.makeText(this@InstaLoginActivity, "네트워크 오류가 발생했습니다. 잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 200) // 위에서 200px 아래로
                    toast.show()
                }
            })
        }

        findViewById<TextView>(R.id.insta_join).setOnClickListener {
            startActivity(Intent(this, InstaJoinActivity::class.java))
        }
    }
}