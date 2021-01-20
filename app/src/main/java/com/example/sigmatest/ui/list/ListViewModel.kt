package com.example.sigmatest.ui.list

import androidx.lifecycle.*
import com.example.sigmatest.data.entity.PostEntity
import com.example.sigmatest.repository.PostRepository
import kotlinx.coroutines.*

import javax.inject.Inject

class ListViewModel @Inject constructor(
    val PostRepository: PostRepository
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
                val posts = PostRepository.fetchRemotePosts()
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
             PostRepository.fetchLocalPosts().observeForever {
                 _postList.postValue(it)
             }
             PostRepository.fetchLocalPosts().removeObserver {}
         }
    }

    fun searchPosts(it:String) {
        viewModelScope.launch {
            PostRepository.searchPosts(it).observeForever {
                _postList.postValue(it)
            }
            PostRepository.searchPosts(it).removeObserver {}
        }
    }

    private fun deleteLocalPosts(){
        PostRepository.deleteLocalPosts()
    }

    private fun insertLocalPosts(posts:List<PostEntity>){
        PostRepository.insertLocalPosts(posts)
    }

}