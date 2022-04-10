package com.juanca.reto1.viewmodel

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.juanca.reto1.R
import com.juanca.reto1.databinding.PostItemBinding

class PostView(itemView: View): RecyclerView.ViewHolder(itemView) {

    private lateinit var binding: PostItemBinding

    //UI Elements
    var thumbnail: ImageView = itemView.findViewById(R.id.rowThumbnailIV)
    var username: TextView = itemView.findViewById(R.id.rowUsernameTV)
    var userImage: ImageView = itemView.findViewById(R.id.rowProfileIV)
    var title: TextView = itemView.findViewById(R.id.rowTitleTV)
    var date: TextView = itemView.findViewById(R.id.rowDateTV)
    var location: TextView = itemView.findViewById(R.id.rowLocationTV)

    init {

    }


}