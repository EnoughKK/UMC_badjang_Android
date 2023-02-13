package com.umc.badjang.SearchMorePage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.badjang.R
import com.umc.badjang.databinding.PostContentItemBinding


class SearchMorePostAdapter(private val context: Context) :
    RecyclerView.Adapter<SearchMorePostAdapter.SearchMorePostViewHolder>() {

    private lateinit var viewBinding: PostContentItemBinding

    var datas = mutableListOf<SearchMorePostData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) :
            SearchMorePostViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.post_content_item, parent, false)

        viewBinding = PostContentItemBinding.bind(view)

        return SearchMorePostViewHolder(PostContentItemBinding.bind(view))
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: SearchMorePostViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class SearchMorePostViewHolder(private val binding: PostContentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SearchMorePostData) {
            // 작성자 프로필
            binding.popularPostProfileImg.setImageBitmap(item.searchPostProfileImg) // 프로필 이미지
            binding.popularPostProfileNickname.text = item.searchPostNickname       // 닉네임
            binding.popularPostProfileDate.text = item.searchPostDate               // 작성일

            // 인기글 내용
            binding.popularPostContentTitle.text = item.searchPostTitle             // 게시글 제목
            binding.popularPostContentText.text = item.searchPostContent            // 게시글 내용
            if(item.searchPostImg != null)
                binding.popularPostContentImg.setImageBitmap(item.searchPostImg)        // 게시글 이미지
            else
                binding.popularPostContentImg.visibility = View.GONE

            // 기타 정보
            binding.popularPostCommentsNum.text = item.searchPostCommentsCnt.toString() // 댓글 수
            binding.popularPostViewNum.text = item.searchPostViewCnt.toString()         // 조회수
            binding.popularPostGoodNum.text = item.searchPostGoodCnt.toString()         // 좋아요 수
        }
    }
}