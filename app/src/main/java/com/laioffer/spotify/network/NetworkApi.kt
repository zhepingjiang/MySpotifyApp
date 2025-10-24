package com.laioffer.spotify.network

import com.laioffer.spotify.datamodel.Playlist
import com.laioffer.spotify.datamodel.Section
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface NetworkApi {

    @GET("/feed")
    fun getHomeFeed(): Call<List<Section>>

    @GET("/playlist/{id}")
    fun getPlaylist(@Path("id") id: Int): Call<Playlist>

    @POST("/favorite")
    fun markAsFavorite(@Body id: Int): Call<Boolean>
}

//    @GET("/playlists")
//    fun getPlayLists(): List<Playlist>