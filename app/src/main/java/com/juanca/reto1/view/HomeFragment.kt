package com.juanca.reto1.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.juanca.reto1.databinding.FragmentHomeBinding
import com.juanca.reto1.model.Post

class HomeFragment : Fragment(), NewPostFragment.OnNewPostListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    //State
    private val adapter = PostAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val postRecycler = binding.postRecycler
        postRecycler.setHasFixedSize(true)
        postRecycler.layoutManager = LinearLayoutManager(activity)
        postRecycler.adapter = adapter

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance() = HomeFragment()

    }

    override fun onPause() {
        super.onPause()
        val json = Gson().toJson()
    }

    override fun newPost(post: Post) {
        adapter.addPost(post)
    }
}