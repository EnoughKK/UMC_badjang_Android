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
            if(!checkAll) { // 전체 즐겨찾기 탭이 아니면 '장학금'이라고 라벨 나오는 거 없애기
                binding.bookmarksScholarshipLabel.visibility = View.GONE
            }

            binding.bookmarksScholarshipInstitutionLabel.text = item.bookmarkScholarshipInstitution // 기관명

            // 장학금 내용 - 닫힌 버전
            binding.bookmarksScholarshipCloseTitle.text = item.bookmarkScholarshipTitle             // 전국소식 제목
            if(item.bookmarkScholarshipImg != null)
                binding.bookmarksScholarshipCloseImg.setImageBitmap(item.bookmarkScholarshipImg)        // 전국소식 이미지
            else
                binding.bookmarksScholarshipCloseImg.visibility = View.GONE

            // 장학금 내용 - 열린 버전
            binding.bookmarksScholarshipOpenTitle.text = item.bookmarkScholarshipTitle             // 전국소식 제목
            binding.bookmarksScholarshipOpenText.text = item.bookmarkScholarshipContent            // 전국소식내용
            if(item.bookmarkScholarshipImg != null)
                binding.bookmarksScholarshipOpenImg.setImageBitmap(item.bookmarkScholarshipImg)        // 전국소식 이미지
            else
                binding.bookmarksScholarshipOpenImg.visibility = View.GONE

            // 기타 정보
            binding.bookmarksScholarshipCommentsNum.text = item.bookmarkScholarshipCommentsCnt.toString() // 댓글 수
            binding.bookmarksScholarshipViewNum.text = item.bookmarkScholarshipViewCnt.toString()         // 조회수

            // 모두 즐겨찾기 되어있으므로
            binding.bookmarksScholarshipBookmarkCheckBtn.visibility = View.GONE
            binding.bookmarksScholarshipBookmarkUncheckBtn.visibility = View.VISIBLE

            // 즐겨찾기 체크 버튼 선택 시
            binding.bookmarksScholarshipBookmarkCheckBtn.setOnClickListener {
                binding.bookmarksScholarshipBookmarkCheckBtn.visibility = View.GONE
                binding.bookmarksScholarshipBookmarkUncheckBtn.visibility = View.VISIBLE

                if(item.bookmarkScholarshipIdx != null) onClickScholarshipBookmark(item) // 즐겨찾기 추가/취소 api
            }

            // 즐겨찾기 해제 버튼 선택 시
            binding.bookmarksScholarshipBookmarkUncheckBtn.setOnClickListener {
                binding.bookmarksScholarshipBookmarkCheckBtn.visibility = View.VISIBLE
                binding.bookmarksScholarshipBookmarkUncheckBtn.visibility = View.GONE

                if(item.bookmarkScholarshipIdx != null) onClickScholarshipBookmark(item) // 즐겨찾기 추가/취소 api
            }

            // 더보기 버튼 선택 시
            binding.bookmarksScholarshipOpenBtn.setOnClickListener(View.OnClickListener {
                // 접힌 내용 숨기고 펼친 내용 보여주기
                binding.bookmarksScholarshipCloseContents.visibility = View.GONE
                binding.bookmarksScholarshipOpenContents.visibility = View.VISIBLE

                // 더보기 버튼 숨기고 접기 버튼 보여주기
                binding.bookmarksScholarshipCloseBtn.visibility = View.VISIBLE
                binding.bookmarksScholarshipOpenBtn.visibility = View.GONE
            })

            // 접기 버튼 선택 시
            binding.bookmarksScholarshipCloseBtn.setOnClickListener(View.OnClickListener {
                // 펼친 내용 숨기고 접힌 내용 보여주기
                binding.bookmarksScholarshipCloseContents.visibility = View.VISIBLE
                binding.bookmarksScholarshipOpenContents.visibility = View.GONE

                // 접기 버튼 숨기고 더보기 버튼 보여주기
                binding.bookmarksScholarshipCloseBtn.visibility = View.GONE
                binding.bookmarksScholarshipOpenBtn.visibility = View.VISIBLE
            })
        }
    }

    inner class PostHolder(private val binding: MainBookmarksPostItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: BookmarkPostData) {
            // 작성자 프로필
            binding.bookmarksPostProfileImg.setImageBitmap(item.bookmarkPostProfileImg) // 프로필 이미지
            binding.bookmarksPostProfileNickname.text = item.bookmarkPostNickname       // 닉네임
            binding.bookmarksPostProfileDate.text = item.bookmarkPostDate               // 작성일

            // 게시글 내용
            binding.bookmarksPostContentTitle.text = item.bookmarkPostTitle             // 인기글 제목
            binding.bookmarksPostContentText.text = item.bookmarkPostContent            // 인기글 내용
            if(item.bookmarkPostImg != null)
                binding.bookmarksPostContentImg.setImageBitmap(item.bookmarkPostImg)        // 인기글 이미지
            else
                binding.bookmarksPostContentImg.visibility = View.GONE

            // 기타 정보
            binding.bookmarksPostCommentsNum.text = item.bookmarkPostCommentsCnt.toString() // 댓글 수
            binding.bookmarksPostViewNum.text = item.bookmarkPostViewCnt.toString()         // 조회수
            binding.bookmarksPostGoodNum.text = item.bookmarkPostGoodCnt.toString()         // 좋아요 수
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