package com.umc.badjang.MyPage.QNA

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.umc.badjang.Bookmarks.BookmarkPostData
import com.umc.badjang.R
import com.umc.badjang.databinding.ItemMyBoardWriteBinding
import com.umc.badjang.databinding.MainBookmarksPostItemBinding
import com.umc.badjang.databinding.MainBookmarksScholarshipItemBinding
import com.umc.badjang.databinding.PostContentItemBinding

class QAdapter(private val dataSet: ArrayList<QData>, var context :Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var myWriteBinding: ItemMyBoardWriteBinding
    //private lateinit var postViewBinding:

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        myWriteBinding = ItemMyBoardWriteBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(myWriteBinding)
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

    inner class ViewHolder(private val binding: ItemMyBoardWriteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: QData) {
            // 작성자 프로필
            Glide.with(context).load(item.user_profileimage_url).into(binding.boardWritePostProfileImg)
            binding.boardWritePostProfileNickname.text = item.user_name
            binding.boardWritePostProfileDate.text = item.post_createAt.substring(0,10)
            binding.boardWritePostContentTitle.text = item.post_name
            binding.boardWritePostContentText.text = item.post_content
            binding.boardWritePostGoodNum.text = item.post_recommend.toString()
            binding.boardWritePostViewNum.text = item.post_view.toString()
            binding.boardWritePostCommentsNum.text = item.post_comment.toString()
            if(item.post_image == ""){
                binding.boardWritePostContentImg.visibility = View.GONE
            }else{
                Glide.with(context).load(item.post_image).into(binding.boardWritePostContentImg)
            }
            binding.boardWritePostLabel.text = item.post_category
            binding.boardWritePostProfileNickname.text = item.post_anonymity
        }
    }


}