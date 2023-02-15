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
import com.umc.badjang.PostPage.PopularPostData
import com.umc.badjang.R
import com.umc.badjang.databinding.*

class PopularPostBoardAdapter(private val dataSet: ArrayList<PopularPostBoardData>, var context :Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var binding: PostContentItemBinding
    //private lateinit var postViewBinding:

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = PostContentItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
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

    inner class ViewHolder(private val binding: PostContentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PopularPostBoardData) {

            binding.postContentCl.setOnClickListener {
//                var fragment = DetailNotiFragment()
//                val bundle = Bundle()
//                bundle.putInt("idx", item.)
//                fragment.arguments = bundle
//                (context as MainActivity).changeFragment(fragment)
            }
            // 작성자 프로필
            if(item.post_anonymity == "N"){
                binding.popularPostProfileNickname.text = item.user_name
            }
            else{
                binding.popularPostProfileNickname.text = "익명"
            }
            Glide.with(context).load(item.user_profileimage_url).into(binding.popularPostProfileImg)
            binding.popularPostProfileDate.text = item.post_createAt.substring(0,4)+"."+item.post_createAt.substring(5,7)+"."+item.post_createAt.substring(8,10)
            binding.popularPostContentTitle.text = item.post_name
            binding.popularPostContentText.text = item.post_content
            binding.popularPostGoodNum.text = item.post_recommend.toString()
            binding.popularPostViewNum.text = item.post_view.toString()
            binding.popularPostCommentsNum.text = item.post_comment.toString()

            if(item.post_image == "" || item.post_image == null){
                binding.popularPostContentImg.visibility = View.GONE
            }else{
                Glide.with(context).load(item.post_image).into(binding.popularPostContentImg)
            }
        }
    }

}