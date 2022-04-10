package com.juanca.reto1.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.juanca.reto1.R
import com.juanca.reto1.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


}