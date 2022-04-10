package com.juanca.reto1.view

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.juanca.reto1.databinding.FragmentNewPostBinding
import com.juanca.reto1.model.Post
import java.io.File

class NewPostFragment : Fragment() {

    private var _binding: FragmentNewPostBinding? = null
    private val binding get() = _binding!!

    //Listener
    var listener: OnNewPostListener? = null

    private lateinit var file: File

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNewPostBinding.inflate(inflater, container, false)

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

        }

        return binding.root
    }


    private fun onGalleryResult(activityResult: ActivityResult?) {
        val uri = activityResult?.data?.data
        val path = UtilDomi.getPath(requireContext(), uri!!)
        val bitmap = BitmapFactory.decodeFile(path)
        //val scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.width/2, bitmap.height/2, true)

        binding.thumbnailIV.setImageBitmap(bitmap)
    }

    private fun onCameraResult(activityResult: ActivityResult?) {
        val bitmap = BitmapFactory.decodeFile(file.path)

        val scaleBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.width/2, bitmap.height/2, true)

        binding.thumbnailIV.setImageBitmap(scaleBitmap)
    }

    companion object {
        @JvmStatic
        fun newInstance() = NewPostFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding =  null
    }

    interface OnNewPostListener{
        fun newPost(post: Post)
    }
}