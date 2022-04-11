package com.juanca.reto1.view

import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.juanca.reto1.R
import com.juanca.reto1.model.Post
import com.juanca.reto1.viewmodel.PostView

class PostAdapter: RecyclerView.Adapter<PostView>() {

    private val posts = ArrayList<Post>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostView {
        var inflater = LayoutInflater.from(parent.context)
        val post = inflater.inflate(R.layout.post_item, parent, false)
        val postView = PostView(post)

        return postView
    }

    override fun onBindViewHolder(holder: PostView, position: Int) {
        val post = posts[position]
        holder.thumbnail.setImageURI(post.thumbnailUri.toUri())
        holder.username.text = post.username
       // holder.userImage.setImageURI(post.userImageUri.toUri())
        holder.title.text = post.title
        holder.date.text = post.date
        holder.location.text = post.location
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    fun addPost(post: Post){
        posts.add(post)
    }

    fun onPause(sharedPref: SharedPreferences){
        val json = Gson().toJson(posts)
        sharedPref.edit().putString("postsAdapter", json).apply()
    }

    fun onResume(sharedPref: SharedPreferences){
        val json = sharedPref.getString("postAdapter", "NO_DATA")
        if(json != "NO_DATA"){
            var oldPosts = Gson().fromJson(json, ArrayList<Post>::class.java)
        }
    }



}
