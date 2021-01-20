package com.example.sigmatest.ui.list

import android.util.Log
import androidx.lifecycle.*
import com.example.sigmatest.data.entity.PostEntity
import com.example.sigmatest.data.entity.PostResponse
import com.example.sigmatest.repository.postRepository
import kotlinx.coroutines.*

import javax.inject.Inject

class ListViewModel @Inject constructor(
    val postRepository: postRepository
):ViewModel ()
{

    private val _postList = MediatorLiveData<List<PostEntity>>()
    val postList: LiveData<List<PostEntity>>
        get() = _postList

    init {
        getPosts()
    }

     fun getPosts() {

         viewModelScope.async  {
            try {
                val posts = postRepository.fetchRemotePosts()
                val postEntities  = ArrayList<PostEntity>()
                posts?.map {
                        post ->
                    var newPost = PostEntity(id = post.id ,body = post.body,title = post.title,userid = post.userId)
                    postEntities.add(newPost)
                }
                deleteLocalPosts()
                insertLocalPosts(postEntities)
                getLocalPosts()
            } catch (throwable : Throwable){
                println("error happens ${throwable.message}")
            }
        }
    }

     fun getLocalPosts(){
         viewModelScope.launch {
             postRepository.fetchLocalPosts().observeForever {
                 _postList.postValue(it)
             }
             postRepository.fetchLocalPosts().removeObserver {}
         }
    }

    fun searchPosts(it:String) {
        viewModelScope.launch {
            postRepository.searchPosts(it).observeForever {
                _postList.postValue(it)
            }
            postRepository.searchPosts(it).removeObserver {}
        }
    }

    private fun deleteLocalPosts(){
        postRepository.deleteLocalPosts()
    }

    private fun insertLocalPosts(posts:List<PostEntity>){
        postRepository.insertLocalPosts(posts)
    }

}