package com.juanca.reto1.model

import android.location.Location
import android.media.Image
import java.util.*

class Post {
    var id: String
    var thumbnailUri: String
    var username: String
    //var userImageUri: String
    var title: String
    var date: String
    var location: String

    constructor(thumbnailUri: String,
                username: String,
                //userImageUri: String,
                title: String,
                date: String,
                location: String) {

        this.id = UUID.randomUUID().toString()
        this.username = username
        //this.userImageUri = userImageUri
        this.thumbnailUri = thumbnailUri
        this.title = title
        this.location = location
        this.date = date
    }
}