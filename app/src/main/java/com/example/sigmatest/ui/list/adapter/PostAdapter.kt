package com.example.sigmatest.ui.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sigmatest.R
import com.example.sigmatest.data.entity.PostEntity
import kotlinx.android.synthetic.main.item_post.view.*

class PostAdapter : ListAdapter<PostEntity, PostAdapter.PostHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PostEntity>() {
            override fun areItemsTheSame(oldItem: PostEntity, newItem: PostEntity): Boolean {
                return false
            }
            override fun areContentsTheSame(oldItem: PostEntity, newItem: PostEntity): Boolean {
                return false
//                return oldItem.title == newItem.title && oldItem.body == newItem.body
//                        && oldItem.id == newItem.id
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostHolder(itemView)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) = holder.bind(getItem(position),position)

    inner class PostHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(getItem(position))
                }
            }
        }
        fun bind(data: PostEntity,pos:Int) = itemView?.run {
            tv_title.text = data.title
            tv_body.text = data.body
            tv_id.text = "#${data.id}"
        }
    }

    interface OnItemClickListener {
        fun onItemClick(note: PostEntity)
    }
    var listener: OnItemClickListener? = null

}