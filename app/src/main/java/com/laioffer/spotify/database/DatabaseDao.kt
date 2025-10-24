package com.laioffer.spotify.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.laioffer.spotify.datamodel.Album
import kotlinx.coroutines.flow.Flow

@Dao
interface DatabaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun favoriteAlbum(album: Album)

    @Delete
    fun unFavoriteAlbum(album: Album)

    @Query("SELECT EXISTS (SELECT * FROM Album WHERE id = :id)")
    fun isFavoriteAlbum(id: Int): Flow<Boolean>

    @Query("select * from Album")
    fun fetchFavoriteAlbums(): Flow<List<Album>>
}
