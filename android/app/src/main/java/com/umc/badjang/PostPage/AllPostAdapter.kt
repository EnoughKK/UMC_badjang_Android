package com.umc.badjang.PostPage

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
import com.umc.badjang.PostPage.Board.PostBoardFragment
import com.umc.badjang.R
import com.umc.badjang.databinding.*

class AllPostAdapter(private val dataSet: ArrayList<AllPostData>, var context :Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var binding: RvAllPostBinding
    //private lateinit var postViewBinding:

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = RvAllPostBinding.inflate(LayoutInflater.from(parent.context),parent, false)
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

    inner class ViewHolder(private val binding: RvAllPostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AllPostData) {
            binding.allPostBoardName.text =  item.board_name

            binding.allPostCl.setOnClickListener {
                var fragment = PostBoardFragment()
                val bundle = Bundle()
                bundle.putString("name", item.board_name)
                fragment.arguments = bundle
                (context as MainActivity).changeFragment(fragment)
            }
            // 작성자 프로필
//            Glide.with(context).load(item.user_profileimage_url).into(binding.boardWritePostProfileImg)
//            binding.boardWritePostProfileNickname.text = item.user_name
//            binding.boardWritePostProfileDate.text = item.post_createAt.substring(0,10)
//            binding.boardWritePostContentTitle.text = item.post_name
//            binding.boardWritePostContentText.text = item.post_content
//            binding.boardWritePostGoodNum.text = item.post_recommend.toString()
//            binding.boardWritePostViewNum.text = item.post_view.toString()
//            binding.boardWritePostCommentsNum.text = item.post_comment.toString()
//            if(item.post_image == ""){
//                binding.boardWritePostContentImg.visibility = View.GONE
//            }else{
//                Glide.with(context).load(item.post_image).into(binding.boardWritePostContentImg)
//            }
//            binding.boardWritePostLabel.text = item.post_category
//            binding.boardWritePostProfileNickname.text = item.post_anonymity
        }
    }


}