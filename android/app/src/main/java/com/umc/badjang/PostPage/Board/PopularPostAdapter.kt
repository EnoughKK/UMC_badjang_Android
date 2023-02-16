package com.umc.badjang.PostPage.Board

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.umc.badjang.Bookmarks.BookmarkPostData
import com.umc.badjang.MainActivity
import com.umc.badjang.MyPage.Noti.DetailNotiFragment
import com.umc.badjang.PostPage.Detail.DetailPostFragment
import com.umc.badjang.PostPage.PopularPostData
import com.umc.badjang.R
import com.umc.badjang.databinding.*

class PopularPostAdapter(private val dataSet: ArrayList<PopularPostData>, var context :Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var binding: RvPopularPostBinding
    //private lateinit var postViewBinding:

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = RvPopularPostBinding.inflate(LayoutInflater.from(parent.context),parent, false)
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

    inner class ViewHolder(private val binding: RvPopularPostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PopularPostData) {
            binding.popularPostCl.setOnClickListener {
                var fragment = DetailPostFragment()
                val bundle = Bundle()
                bundle.putInt("post_idx", item.post_idx)
                bundle.putString("board_name", item.post_category)
                fragment.arguments = bundle
                (context as MainActivity).changeFragment(fragment)
            }
            // 작성자 프로필
            Glide.with(context).load(item.user_profileimage_url).into(binding.userImage)
            if(item.post_anonymity == "N"){
                binding.userName.text = item.user_name
            }
            else{
                binding.userName.text = "익명"
            }
            binding.writeDate.text = item.post_createAt.substring(0,4)+"."+item.post_createAt.substring(5,7)+"."+item.post_createAt.substring(8,10)
            binding.postTitle.text = item.post_name
            binding.postContents.text = item.post_content
            binding.postGood.text = item.post_recommend.toString()
            binding.postViews.text = item.post_view.toString()
            binding.postCategory.text = item.post_category

        }
    }


}