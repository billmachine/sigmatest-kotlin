package com.cendana2000.sim_pbb_sinjai.Interface

import com.example.sigmatest.data.entity.PostResponse

import io.reactivex.Single
import kotlinx.coroutines.Deferred
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST


interface RestApi {

    @GET("posts")
    suspend fun getPosts(): List<PostResponse>?

}
