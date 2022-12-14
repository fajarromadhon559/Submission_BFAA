package com.example.githubusersub2.Repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.githubusersub2.Database.FavoriteDao
import com.example.githubusersub2.Database.FavoriteEntity
import com.example.githubusersub2.Database.FavoriteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(context: Context) {
    private val favDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(context)
        favDao = db!!.favDao()
    }

    fun getAllFavorites(): LiveData<List<FavoriteEntity>> = favDao.getAllFavorite()
    fun getPersonFavoriteById(id: Int): LiveData<List<FavoriteEntity>> =
        favDao.getPersonFavoriteById(id)

    fun insert(fav: FavoriteEntity) {
        executorService.execute { favDao.insert(fav) }
    }

    fun delete(fav: FavoriteEntity) {
        executorService.execute { favDao.delete(fav) }
    }
}