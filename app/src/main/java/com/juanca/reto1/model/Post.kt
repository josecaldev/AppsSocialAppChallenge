package com.juanca.reto1.model

import android.location.Location
import android.media.Image
import java.util.*

class Post {
    var id: String
    var thumbnail: Image
    var username: String
    var userImage: Image
    var title: String
    var date: String
    var location: String

    constructor(id: String, thumbnail: Image, username: String,
                userImage: Image, title: String,
                date: String, location: String) {
        this.id = UUID.randomUUID().toString()
        this.username = username
        this.userImage = userImage
        this.thumbnail = thumbnail
        this.title = title
        this.location = location
        this.date = date
    }
}