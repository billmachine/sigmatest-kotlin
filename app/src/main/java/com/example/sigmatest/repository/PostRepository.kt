package com.example.sigmatest.repository

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cendana2000.sim_pbb_sinjai.Interface.RestApi
import com.example.sigmatest.di.AppDatabase
import com.example.sigmatest.data.entity.PostEntity
import com.example.sigmatest.data.entity.PostResponse
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class postRepository @Inject constructor(
   val db: AppDatabase,
    val post: RestApi
)  {
    init { }

    suspend fun fetchRemotePosts() = post.getPosts()

    fun fetchLocalPosts(): LiveData<List<PostEntity>> = db.postDao().getAll()

    fun insertLocalPosts(post: List<PostEntity?>?) = db.postDao().insertAll(post)

    fun deleteLocalPosts() = db.postDao().deleteAll()

    fun searchPosts(word:String?) = db.postDao().getbyTitle("%${word}%")

}