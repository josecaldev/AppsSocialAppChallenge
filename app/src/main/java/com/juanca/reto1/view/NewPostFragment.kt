package com.juanca.reto1.view

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
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
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import com.juanca.reto1.R
import com.juanca.reto1.databinding.FragmentNewPostBinding
import com.juanca.reto1.model.Post
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class NewPostFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private var _binding: FragmentNewPostBinding? = null
    private val binding get() = _binding!!

    //Listener
    var newPostListener: OnNewPostListener? = null

    private lateinit var file: File
    private lateinit var thumbnailUri: String
    private lateinit var location: String
    private var loggedUser = LoginActivity.loggedUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentNewPostBinding.inflate(inflater, container, false)

        ArrayAdapter.createFromResource(
            activity?.applicationContext!!,
            R.array.cities_array,
            android.R.layout.simple_spinner_item
        ).also{adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.locationSpinner.adapter = adapter
        }

        val launcherGallery = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), ::onGalleryResult)
        val launcherCamera = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), ::onCameraResult)


        binding.galleryBtn.setOnClickListener {
            val i = Intent(Intent.ACTION_GET_CONTENT)
            i.type = "image/*"
            launcherGallery.launch(i)
        }

        binding.cameraBtn.setOnClickListener {
            val i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            file = File("${activity?.getExternalFilesDir(null)}/photo.png")
            val uri = FileProvider.getUriForFile(activity?.applicationContext!!, "com.juanca.reto1", file)
            i.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            launcherCamera.launch(i)
        }

        binding.publishBtn.setOnClickListener {
            val sdf = SimpleDateFormat("dd/mm/yyyy")
            val currentDate = sdf.format(Date()).toString()
            val post = Post(
                thumbnailUri,
                loggedUser?.username.toString(),
                //loggedUser?.profileImageUri.toString(),
                binding.titleET.text.toString(),
                currentDate,
                binding.locationSpinner.selectedItem.toString()
            )

            newPostListener.let {
                it?.newPost(post)
            }


        }

        return binding.root
    }


    private fun onGalleryResult(result: ActivityResult?) {
        if(result?.resultCode == RESULT_OK){
            val uri = result?.data?.data
            thumbnailUri = uri.toString()
            val path = UtilDomi.getPath(requireContext(), uri!!)
            val bitmap = BitmapFactory.decodeFile(path)
            val scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.width/2, bitmap.height/2, true)

            binding.thumbnailIV.setImageBitmap(scaledBitmap)

        }else if(result?.resultCode == RESULT_CANCELED){
            Toast.makeText(activity?.applicationContext, "Imagen no seleccionada", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onCameraResult(result: ActivityResult?) {
        if(result?.resultCode == RESULT_OK){
            val uri = file.path.toUri()
            thumbnailUri = uri.toString()

            val bitmap = BitmapFactory.decodeFile(file.path)
            val scaleBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.width/2, bitmap.height/2, true)

            binding.thumbnailIV.setImageBitmap(scaleBitmap)

        }else if(result?.resultCode == RESULT_CANCELED){
            Toast.makeText(activity?.applicationContext, "Captura cancelada", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = NewPostFragment()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding =  null
    }

    interface OnNewPostListener{
        fun newPost(post: Post)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        location = parent?.getItemAtPosition(pos).toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        //callback?
    }

}