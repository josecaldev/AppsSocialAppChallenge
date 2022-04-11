package com.juanca.reto1.view

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import com.juanca.reto1.databinding.FragmentProfileBinding
import java.io.File

class ProfileFragment : Fragment() {

    private lateinit var _binding: FragmentProfileBinding
    private val binding get() = _binding

    private lateinit var file: File
    private lateinit var profileUri: String
    private var loggedUser = LoginActivity.loggedUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        val launcherGallery = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), ::onGalleryResult)
        val launcherCamera = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), ::onCameraResult)

        binding.usernameET.setText(loggedUser?.username)

        binding.galleryIV.setOnClickListener {
            val i = Intent(Intent.ACTION_GET_CONTENT)
            i.type = "image/*"
            launcherGallery.launch(i)
        }

        binding.cameraIV.setOnClickListener {
            val i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            file = File("${activity?.getExternalFilesDir(null)}/photo.png")
            var uri = FileProvider.getUriForFile(activity?.applicationContext!!, "com.juanca.reto1", file)
            i.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            launcherCamera.launch(i)
        }

        binding.updateProfileBtn.setOnClickListener {
            loggedUser?.username = binding.usernameET.text.toString()
            loggedUser?.profileImageUri = profileUri
            Toast.makeText(activity, "Perfil actualizado", Toast.LENGTH_SHORT).show()
        }

        binding.signOutBtn.setOnClickListener{
            val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
            LoginActivity.loggedUser = null
            sharedPref?.edit()?.remove("currentLoginState")?.commit()

            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        return binding.root
    }

    private fun onGalleryResult(result: ActivityResult?) {
        if(result?.resultCode == RESULT_OK){
            val uri = result?.data?.data
            profileUri = uri.toString()
            val path = UtilDomi.getPath(requireContext(), uri!!)
            val bitmap = BitmapFactory.decodeFile(path)
            val scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.width/2, bitmap.height/2, true)

            binding.profileIV.setImageBitmap(scaledBitmap)

        }else if(result?.resultCode == Activity.RESULT_CANCELED){
            Toast.makeText(activity?.applicationContext, "Imagen no seleccionada", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onCameraResult(result: ActivityResult?) {
        if(result?.resultCode == RESULT_OK){
            val uri = file.path.toUri()
            profileUri = uri.toString()

            val bitmap = BitmapFactory.decodeFile(file.path)
            val scaleBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.width/2, bitmap.height/2, true)

            binding.profileIV.setImageBitmap(scaleBitmap)

        }else if(result?.resultCode == Activity.RESULT_CANCELED){
            Toast.makeText(activity?.applicationContext, "Captura cancelada", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}