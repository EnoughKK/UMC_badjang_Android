package com.umc.badjang.ScholarshipPage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.badjang.R
import com.umc.badjang.ScholarshipPage.Model.ScholarshipCommentsDTO
import com.umc.badjang.databinding.RvScholarshipCommentsBinding
import com.umc.badjang.databinding.RvScholarshipMycommentsBinding
import com.umc.badjang.mConnectUserId
import kotlin.properties.Delegates

class CommentsRVAdapter(private val context: Context):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var datas = ArrayList<ScholarshipCommentsDTO>()

    private var userIdx by Delegates.notNull<Int>()

    private lateinit var viewBinding1: RvScholarshipCommentsBinding
    private lateinit var viewBinding2: RvScholarshipMycommentsBinding
    private lateinit var mItemClickListener: CommentsRVAdapter.OnClickInterface

    interface OnClickInterface{
        fun onItemLongClick(view: View, position: Int)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {

        val view1 = LayoutInflater.from(context).inflate(R.layout.rv_scholarship_comments, parent, false)   // 다른 사람 댓글
        val view2 = LayoutInflater.from(context).inflate(R.layout.rv_scholarship_mycomments, parent, false) // 내 댓글

        viewBinding1 = RvScholarshipCommentsBinding.bind(view1)
        viewBinding2 = RvScholarshipMycommentsBinding.bind(view2)

        userIdx = datas[0].user_idx!!

        if (userIdx == mConnectUserId) {
            return MyCommentsHolder(RvScholarshipMycommentsBinding.bind(view2))
        } else {
            return CommentsHolder(RvScholarshipCommentsBinding.bind(view1))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (userIdx == mConnectUserId){
            (holder as MyCommentsHolder).bind(datas[position])
        } else {
            (holder as CommentsHolder).bind(datas[position])
        }

        holder.itemView.setOnClickListener {
            mItemClickListener.onItemLongClick(it, position)
        }
    }

    override fun getItemCount(): Int = datas.size

    inner class CommentsHolder(private val binding: RvScholarshipCommentsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ScholarshipCommentsDTO) {
            binding.comments.text = item.scholarship_comment_content
        }
    }

    inner class MyCommentsHolder(private val binding: RvScholarshipMycommentsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ScholarshipCommentsDTO) {
            binding.comments.text = item.scholarship_comment_content
        }
    }

    fun setItemClickListener(itemClickListener: CommentsRVAdapter.OnClickInterface){
        mItemClickListener = itemClickListener
    }
}