package com.laioffer.spotify.datamodel

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Album(
    val id: Int,
    @SerializedName("album")
    val name: String,
    val year: String,
    val cover: String,
    val artists: String,
    val description: String
) : Serializable
