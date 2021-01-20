package com.example.sigmatest.data.protocol

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.FtsOptions.Order
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sigmatest.data.entity.PostEntity


@Dao
interface PostDao{

    @Query("SELECT * FROM tb_post order by id asc")
    fun getAll(): LiveData<List<PostEntity>>

    @Query("delete FROM tb_post")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(postEntities: List<PostEntity?>?)

    @Query("SELECT * FROM tb_post where title LIKE :search")
    fun getbyTitle(search: String?): LiveData<List<PostEntity>>

//    @Delete
//    fun delete(todo: PostEntity)

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insert(vararg todo: PostEntity)
}