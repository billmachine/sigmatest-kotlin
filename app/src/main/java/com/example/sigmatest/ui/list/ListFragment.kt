package com.example.sigmatest.ui.list

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.observe
import com.example.sigmatest.R
import com.example.sigmatest.ui.BaseFragment
import com.example.sigmatest.ui.list.adapter.PostAdapter
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_list.*
import java.util.concurrent.TimeUnit


class ListFragment : BaseFragment() {

    val viewModel: ListViewModel? by viewModels{ viewModelFactory}
    val postAdapter : PostAdapter by lazy {
        val postAdapter = PostAdapter()
        rv_posts.adapter = postAdapter
        postAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel!!.postList.observeForever {
                postAdapter.submitList(it)
        }
        bt_search.setOnClickListener { viewModel?.searchPosts(et_search.text.toString())  }
        bt_clear.setOnClickListener {
            viewModel?.getLocalPosts()
            et_search.text.clear()
        }
    }

}