package com.example.projetsession

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText = findViewById<EditText>(R.id.editTextText)
        val button = findViewById<Button>(R.id.button)
        val container = findViewById<LinearLayout>(R.id.container)

        button.setOnClickListener {
            val text = editText.text.toString()

            val userMessage = TextView(this)
            userMessage.text = text

            userMessage.textSize = 16f
            userMessage.setTextColor(ContextCompat.getColor(this@MainActivity, android.R.color.black))
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 8, 0, 8)
            userMessage.layoutParams = params

            container.addView(userMessage)

            val content = Content("user",text)
            val request = Request("openai/gpt-4o",1024,listOf(content))

            val call = ApiService.createMessage(request)
            call.enqueue(object : Callback<Reponse>{
                override fun onResponse(call: Call<Reponse>, response: Response<Reponse>) {
                    if(response.isSuccessful){
                        val response = response.body()
                        val assistantMessage = TextView(this@MainActivity)
                        assistantMessage.text = (response?.choices?.get(0)?.message?.content?: container.addView(assistantMessage)).toString()

                        assistantMessage.setPadding(32,32,32,32)
                        assistantMessage.textSize=16f
                        assistantMessage.setTextColor(ContextCompat.getColor(this@MainActivity, android.R.color.black))
                        assistantMessage.setBackgroundResource(android.R.drawable.dialog_holo_light_frame)

                        val params = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                        params.setMargins(0, 8, 0, 8)
                        assistantMessage.layoutParams = params

                        container.addView(assistantMessage)
                        editText.text.clear()
                        Log.d("Retrofit","Successful operation")
                    }
                    else{
                        Log.d("Retrofit","Get Request not successful: ${response.code()}")
                    }
                }
                override fun onFailure(call: Call<Reponse>, t: Throwable) {
                    Log.d("Retrofit","Get Failure: ${t.message}")
                }
            })
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)){ v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
