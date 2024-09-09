package com.example.mycrudapp

import android.content.Context
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TodoWriteActivity : AppCompatActivity() {
    lateinit var contentEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_write)

        val retrofit =
            Retrofit.Builder().baseUrl("http://mellowcode.org/").addConverterFactory(GsonConverterFactory.create())
                .build()

        val retrofitService = retrofit.create(RetrofitService::class.java)

        contentEditText = findViewById<EditText>(R.id.content_edit_text)

        findViewById<TextView>(R.id.make_todo).setOnClickListener {
            val header = HashMap<String, String>()
            val sp = this.getSharedPreferences("user_info", Context.MODE_PRIVATE)
            val token = sp.getString("token", "")
            header.put("Authorization", "token " + token!!)

            val body = hashMapOf<String, Any>()
            body.put("content", contentEditText.text)
            body.put("is_complete", "False")

            retrofitService.makeTodo(header, body).enqueue(
                object : Callback<Any> {
                    override fun onResponse(call: Call<Any>, response: Response<Any>) {
                        onBackPressedDispatcher.onBackPressed()
                    }

                    override fun onFailure(call: Call<Any>, t: Throwable) {
                        onBackPressedDispatcher.onBackPressed()
                    }
                }
            )
        }
    }
}