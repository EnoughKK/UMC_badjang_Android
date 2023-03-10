package com.umc.badjang.ScholarshipPage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.umc.badjang.R
import com.umc.badjang.ScholarshipPage.Model.ScholarshipCommentsDTO
import com.umc.badjang.databinding.RvScholarshipCommentsBinding
import com.umc.badjang.databinding.RvScholarshipMycommentsBinding


class CommentsRVAdapter(private val context: Context):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var datas = ArrayList<ScholarshipCommentsDTO>()

    private lateinit var mItemClickListener: CommentsRVAdapter.OnClickInterface

    interface OnClickInterface{
        fun onItemLongClick(view: View, position: Int)
    }

    override fun getItemViewType(position: Int): Int {
        return datas[position].viewType
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {

        val view: View?

        return when(viewType) {
            1 -> {
                view = LayoutInflater.from(context).inflate(R.layout.rv_scholarship_mycomments, parent, false)
                MyCommentsHolder(RvScholarshipMycommentsBinding.bind(view))
            }
            else -> {
                view = LayoutInflater.from(context).inflate(R.layout.rv_scholarship_comments, parent, false)
                CommentsHolder(RvScholarshipCommentsBinding.bind(view))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when(datas[position].viewType) {
            1 -> {
                (holder as MyCommentsHolder).bind(datas[position])
                holder.setIsRecyclable(false)
                holder.itemView.setOnLongClickListener(View.OnLongClickListener {
                    mItemClickListener.onItemLongClick(it, position)
                    true
                })
            }
            else -> {
                (holder as CommentsHolder).bind(datas[position])
                holder.setIsRecyclable(false)
                holder.itemView.setOnLongClickListener {
                    mItemClickListener.onItemLongClick(it, position)
                    true
                }
            }
        }


    }

    override fun getItemCount(): Int = datas.size

    inner class CommentsHolder(private val binding: RvScholarshipCommentsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ScholarshipCommentsDTO) {
            binding.userid.text = item.user_name
            binding.comments.text = item.scholarship_comment_content

            Glide.with(context)
                .load(item.user_profileimage_url)
                .circleCrop()
                .into(binding.userProfile)

        }
    }

    inner class MyCommentsHolder(private val binding: RvScholarshipMycommentsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ScholarshipCommentsDTO) {
            binding.comments.text = item.scholarship_comment_content
        }
    }

    fun setItemClickListener(onItemLongClickListener: CommentsRVAdapter.OnClickInterface){
        mItemClickListener = onItemLongClickListener
    }
}