package com.umc.badjang.Searchpage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.badjang.HomeMorePage.PopularPostData
import com.umc.badjang.R
import com.umc.badjang.databinding.PostContentItemBinding

class Search4RVAdapter (private val context: Context):
    RecyclerView.Adapter<Search4RVAdapter.PopularPostViewHolder>() {

    private lateinit var viewBinding: PostContentItemBinding

    var datas = mutableListOf<PopularPostData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) :
            PopularPostViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.post_content_item, parent, false)

        viewBinding = PostContentItemBinding.bind(view)

        return PopularPostViewHolder(PostContentItemBinding.bind(view))
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: PopularPostViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class PopularPostViewHolder(private val binding: PostContentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PopularPostData) {
            // 작성자 프로필
            binding.popularPostProfileImg.setImageBitmap(item.popularPostProfileImg) // 프로필 이미지
            binding.popularPostProfileNickname.text = item.popularPostNickname       // 닉네임
            binding.popularPostProfileDate.text = item.popularPostDate               // 작성일

            // 인기글 내용
            binding.popularPostContentTitle.text = item.popularPostTitle             // 인기글 제목
            binding.popularPostContentText.text = item.popularPostContent            // 인기글 내용
            if(item.popularPostImg != null)
                binding.popularPostContentImg.setImageBitmap(item.popularPostImg)        // 인기글 이미지
            else
                binding.popularPostContentImg.visibility = View.GONE

            // 기타 정보
            binding.popularPostCommentsNum.text = item.popularPostCommentsCnt.toString() // 댓글 수
            binding.popularPostViewNum.text = item.popularPostViewCnt.toString()         // 조회수
            binding.popularPostGoodNum.text = item.popularPostGoodCnt.toString()         // 좋아요 수
        }
    }
}