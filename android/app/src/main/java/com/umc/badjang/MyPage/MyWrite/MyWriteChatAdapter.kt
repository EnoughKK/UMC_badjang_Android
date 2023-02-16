package com.umc.badjang.MyPage.MyWrite

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.umc.badjang.Bookmarks.BookmarkPostData
import com.umc.badjang.R
import com.umc.badjang.databinding.*

class MyWriteChatAdapter(private val dataSet: ArrayList<MyChatData>, var context :Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var binding: ItemMyWriteChatBinding
    //private lateinit var postViewBinding:

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = ItemMyWriteChatBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ViewHolder -> holder.bind(dataSet[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    inner class ViewHolder(private val binding: ItemMyWriteChatBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MyChatData) {
            // 작성자 프로필
            //binding.popularPostProfileImg
            //binding.bookmarksPostProfileDate.text = item.
            binding.itemWriteChatTvTitle.text = item.post_name
            binding.itemWriteChatTvBoard.text = item.post_category
            binding.itemWriteChatTvChatContent.text = item.comment_content
        }
    }



}