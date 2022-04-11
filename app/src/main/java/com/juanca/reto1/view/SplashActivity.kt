package com.juanca.reto1.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.juanca.reto1.R


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Thread.sleep(5000)

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

}