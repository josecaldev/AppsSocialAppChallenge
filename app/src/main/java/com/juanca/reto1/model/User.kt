package com.juanca.reto1.model

import android.media.Image
import android.widget.ImageView
import com.google.gson.Gson

class User(var username: String, var password: String) {

    var email: String = "$username@gmail.com"
    lateinit var profileImageUri: String
}