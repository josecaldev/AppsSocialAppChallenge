package com.juanca.reto1.view

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.edit
import com.google.gson.Gson
import com.juanca.reto1.R
import com.juanca.reto1.databinding.ActivityLoginBinding
import com.juanca.reto1.model.User

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    companion object{
        var loggedUser: User? = null
    }

    private var user1 = User("alfa", "aplicacionesmoviles")
    private var user2 = User("beta", "aplicacionesmoviles")
    private var allowedPermissions: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(loggedUser != null) {
            toMain()
        }

        requestPermissions(arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        ),1)

        binding.loginBtn.setOnClickListener {

            if(validUser(user1) || validUser(user2)){
                if(allowedPermissions){
                    toMain()
                }
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 1){

            for (result in grantResults){
                if(result == PackageManager.PERMISSION_DENIED){
                    allowedPermissions = false
                }
            }

            if(allowedPermissions == false){
                Toast.makeText(this, "Para continuar debe aceptar todos los permisos"
                    ,Toast.LENGTH_LONG).show()
            }
        }
    }

    fun toMain(){
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("loggedUser", Gson().toJson(loggedUser))
        startActivity(intent)
        finish()
    }

    fun validUser(userTest: User): Boolean{
        var wrongEmail = false
        var errorMsg = ""
        if(binding.emailET.text.toString() != userTest.email) {
            errorMsg = "Correo incorrecto"
            wrongEmail = true
        }
        if(binding.passwordET.text.toString() != userTest.password){
            if(wrongEmail){
                errorMsg = "Correo o contraseña incorrecta"
            }else{
                errorMsg = "Contraseña incorrecta"
            }
        }
        if(errorMsg.isNotEmpty()){
            Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show()
        }else{
            loggedUser = userTest
        }
        return errorMsg.isEmpty()
    }

    override fun onPause() {
        super.onPause()
        val json = Gson().toJson(loggedUser)
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        sharedPref.edit().putString("currentLoginState", json).apply()
    }

    override fun onResume() {
        super.onResume()
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        val json = sharedPref.getString("currentLoginState", "NO_DATA")
        if(json != "NO_DATA"){
            var pastUser = Gson().fromJson(json, User::class.java)
            //Log.e(">>>", pastUser.username)
            loggedUser = pastUser
            if(loggedUser != null) {
                toMain()
            }
        }
    }

}
