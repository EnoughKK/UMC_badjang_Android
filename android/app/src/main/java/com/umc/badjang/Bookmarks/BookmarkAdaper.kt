package com.umc.badjang.Bookmarks

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.badjang.R
import com.umc.badjang.databinding.MainBookmarksPostItemBinding
import com.umc.badjang.databinding.MainBookmarksScholarshipItemBinding

class BookmarkAdapter(
    private val context: Context,
    val onClickScholarshipBookmark: (scholarship: BookmarkScholarshipData) -> Unit,
    private val checkAll: Boolean):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var scholarshipViewBinding: MainBookmarksScholarshipItemBinding
    private lateinit var postViewBinding: MainBookmarksPostItemBinding

    var items = mutableListOf<BookmarkItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        TYPE_SCHOLARSHIP -> {
            val view = LayoutInflater.from(context).inflate(R.layout.main_bookmarks_scholarship_item, parent, false)

            scholarshipViewBinding = MainBookmarksScholarshipItemBinding.bind(view)

            ScholarshipHolder(MainBookmarksScholarshipItemBinding.bind(view))
        }
        TYPE_POST -> {
            val view = LayoutInflater.from(context).inflate(R.layout.main_bookmarks_post_item, parent, false)

            postViewBinding = MainBookmarksPostItemBinding.bind(view)

            PostHolder(MainBookmarksPostItemBinding.bind(view))
        }
        else -> {
            throw IllegalStateException("Not Found ViewHolder Type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ScholarshipHolder -> {
                holder.bind(items[position] as BookmarkScholarshipData)
            }
            is PostHolder -> {
                holder.bind(items[position] as BookmarkPostData)
            }
        }
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = when (items[position]) {
        is BookmarkScholarshipData -> {
            TYPE_SCHOLARSHIP
        }
        is BookmarkPostData -> {
            TYPE_POST
        }
        else -> {
            throw IllegalStateException("Not Found ViewHolder Type")
        }
    }

    inner class ScholarshipHolder(private val binding: MainBookmarksScholarshipItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: BookmarkScholarshipData) {
            if(!checkAll) { // ?????? ???????????? ?????? ????????? '?????????'????????? ?????? ????????? ??? ?????????
                binding.bookmarksScholarshipLabel.visibility = View.GONE
            }

            binding.bookmarksScholarshipInstitutionLabel.text = item.bookmarkScholarshipInstitution // ?????????

            // ????????? ?????? - ?????? ??????
            binding.bookmarksScholarshipCloseTitle.text = item.bookmarkScholarshipTitle             // ???????????? ??????
            if(item.bookmarkScholarshipImg != null)
                binding.bookmarksScholarshipCloseImg.setImageBitmap(item.bookmarkScholarshipImg)        // ???????????? ?????????
            else
                binding.bookmarksScholarshipCloseImg.visibility = View.GONE

            // ????????? ?????? - ?????? ??????
            binding.bookmarksScholarshipOpenTitle.text = item.bookmarkScholarshipTitle             // ???????????? ??????
            binding.bookmarksScholarshipOpenText.text = item.bookmarkScholarshipContent            // ??????????????????
            if(item.bookmarkScholarshipImg != null)
                binding.bookmarksScholarshipOpenImg.setImageBitmap(item.bookmarkScholarshipImg)        // ???????????? ?????????
            else
                binding.bookmarksScholarshipOpenImg.visibility = View.GONE

            // ?????? ??????
            binding.bookmarksScholarshipCommentsNum.text = item.bookmarkScholarshipCommentsCnt.toString() // ?????? ???
            binding.bookmarksScholarshipViewNum.text = item.bookmarkScholarshipViewCnt.toString()         // ?????????

            // ?????? ???????????? ??????????????????
            binding.bookmarksScholarshipBookmarkCheckBtn.visibility = View.GONE
            binding.bookmarksScholarshipBookmarkUncheckBtn.visibility = View.VISIBLE

            // ???????????? ?????? ?????? ?????? ???
            binding.bookmarksScholarshipBookmarkCheckBtn.setOnClickListener {
                binding.bookmarksScholarshipBookmarkCheckBtn.visibility = View.GONE
                binding.bookmarksScholarshipBookmarkUncheckBtn.visibility = View.VISIBLE

                if(item.bookmarkScholarshipIdx != null) onClickScholarshipBookmark(item) // ???????????? ??????/?????? api
            }

            // ???????????? ?????? ?????? ?????? ???
            binding.bookmarksScholarshipBookmarkUncheckBtn.setOnClickListener {
                binding.bookmarksScholarshipBookmarkCheckBtn.visibility = View.VISIBLE
                binding.bookmarksScholarshipBookmarkUncheckBtn.visibility = View.GONE

                if(item.bookmarkScholarshipIdx != null) onClickScholarshipBookmark(item) // ???????????? ??????/?????? api
            }

            // ????????? ?????? ?????? ???
            binding.bookmarksScholarshipOpenBtn.setOnClickListener(View.OnClickListener {
                // ?????? ?????? ????????? ?????? ?????? ????????????
                binding.bookmarksScholarshipCloseContents.visibility = View.GONE
                binding.bookmarksScholarshipOpenContents.visibility = View.VISIBLE

                // ????????? ?????? ????????? ?????? ?????? ????????????
                binding.bookmarksScholarshipCloseBtn.visibility = View.VISIBLE
                binding.bookmarksScholarshipOpenBtn.visibility = View.GONE
            })

            // ?????? ?????? ?????? ???
            binding.bookmarksScholarshipCloseBtn.setOnClickListener(View.OnClickListener {
                // ?????? ?????? ????????? ?????? ?????? ????????????
                binding.bookmarksScholarshipCloseContents.visibility = View.VISIBLE
                binding.bookmarksScholarshipOpenContents.visibility = View.GONE

                // ?????? ?????? ????????? ????????? ?????? ????????????
                binding.bookmarksScholarshipCloseBtn.visibility = View.GONE
                binding.bookmarksScholarshipOpenBtn.visibility = View.VISIBLE
            })
        }
    }

    inner class PostHolder(private val binding: MainBookmarksPostItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: BookmarkPostData) {
            // ????????? ?????????
            binding.bookmarksPostProfileImg.setImageBitmap(item.bookmarkPostProfileImg) // ????????? ?????????
            binding.bookmarksPostProfileNickname.text = item.bookmarkPostNickname       // ?????????
            binding.bookmarksPostProfileDate.text = item.bookmarkPostDate               // ?????????

            // ????????? ??????
            binding.bookmarksPostContentTitle.text = item.bookmarkPostTitle             // ????????? ??????
            binding.bookmarksPostContentText.text = item.bookmarkPostContent            // ????????? ??????
            if(item.bookmarkPostImg != null)
                binding.bookmarksPostContentImg.setImageBitmap(item.bookmarkPostImg)        // ????????? ?????????
            else
                binding.bookmarksPostContentImg.visibility = View.GONE

            // ?????? ??????
            binding.bookmarksPostCommentsNum.text = item.bookmarkPostCommentsCnt.toString() // ?????? ???
            binding.bookmarksPostViewNum.text = item.bookmarkPostViewCnt.toString()         // ?????????
            binding.bookmarksPostGoodNum.text = item.bookmarkPostGoodCnt.toString()         // ????????? ???
        }
    }


    fun addItems(item: BookmarkItem) {
        this.items.add(item)
        this.notifyDataSetChanged()
    }

    companion object {
        private const val TYPE_SCHOLARSHIP = 0
        private const val TYPE_POST = 1
    }
}