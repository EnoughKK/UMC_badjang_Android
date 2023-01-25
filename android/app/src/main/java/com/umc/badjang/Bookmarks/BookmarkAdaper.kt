package com.umc.badjang.Bookmarks

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.badjang.R
import com.umc.badjang.databinding.MainMoreNationalNewsItemBinding
import com.umc.badjang.databinding.PostContentItemBinding

class BookmarkAdapter(private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var scholarshipViewBinding: MainMoreNationalNewsItemBinding
    private lateinit var postViewBinding: PostContentItemBinding

    var items = mutableListOf<BookmarkItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        TYPE_SCHOLARSHIP -> {
            val view = LayoutInflater.from(context).inflate(R.layout.main_more_national_news_item, parent, false)

            scholarshipViewBinding = MainMoreNationalNewsItemBinding.bind(view)

            ScholarshipHolder(MainMoreNationalNewsItemBinding.bind(view))
        }
        TYPE_POST -> {
            val view = LayoutInflater.from(context).inflate(R.layout.post_content_item, parent, false)

            postViewBinding = PostContentItemBinding.bind(view)

            PostHolder(PostContentItemBinding.bind(view))
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

    class ScholarshipHolder(private val binding: MainMoreNationalNewsItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: BookmarkScholarshipData) {
            binding.nationalNewsInstitutionLabel.text = item.bookmarkScholarshipInstitution // 기관명

            // 전국소식 내용 - 닫힌 버전
            binding.nationalNewsCloseTitle.text = item.bookmarkScholarshipTitle             // 전국소식 제목
            binding.nationalNewsCloseImg.setImageBitmap(item.bookmarkScholarshipImg)        // 전국소식 이미지

            // 전국소식 내용 - 열린 버전
            binding.nationalNewsOpenTitle.text = item.bookmarkScholarshipTitle             // 전국소식 제목
            binding.nationalNewsOpenText.text = item.bookmarkScholarshipContent            // 전국소식내용
            binding.nationalNewsOpenImg.setImageBitmap(item.bookmarkScholarshipImg)        // 전국소식 이미지

            // 기타 정보
            binding.nationalNewsCommentsNum.text = item.bookmarkScholarshipCommentsCnt.toString() // 댓글 수
            binding.nationalNewsViewNum.text = item.bookmarkScholarshipViewCnt.toString()         // 조회수

            // 즐겨찾기 체크 버튼 선택 시
            binding.nationalNewsBookmarkCheckBtn.setOnClickListener {
                binding.nationalNewsBookmarkCheckBtn.visibility = View.GONE
                binding.nationalNewsBookmarkUncheckBtn.visibility = View.VISIBLE
            }

            // 즐겨찾기 해제 버튼 선택 시
            binding.nationalNewsBookmarkUncheckBtn.setOnClickListener {
                binding.nationalNewsBookmarkCheckBtn.visibility = View.VISIBLE
                binding.nationalNewsBookmarkUncheckBtn.visibility = View.GONE
            }

            // 더보기 버튼 선택 시
            binding.nationalNewsOpenBtn.setOnClickListener(View.OnClickListener {
                // 접힌 내용 숨기고 펼친 내용 보여주기
                binding.nationalNewsCloseContents.visibility = View.GONE
                binding.nationalNewsOpenContents.visibility = View.VISIBLE

                // 더보기 버튼 숨기고 접기 버튼 보여주기
                binding.nationalNewsCloseBtn.visibility = View.VISIBLE
                binding.nationalNewsOpenBtn.visibility = View.GONE
            })

            // 접기 버튼 선택 시
            binding.nationalNewsCloseBtn.setOnClickListener(View.OnClickListener {
                // 펼친 내용 숨기고 접힌 내용 보여주기
                binding.nationalNewsCloseContents.visibility = View.VISIBLE
                binding.nationalNewsOpenContents.visibility = View.GONE

                // 접기 버튼 숨기고 더보기 버튼 보여주기
                binding.nationalNewsCloseBtn.visibility = View.GONE
                binding.nationalNewsOpenBtn.visibility = View.VISIBLE
            })
        }
    }

    class PostHolder(private val binding: PostContentItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: BookmarkPostData) {
            // 작성자 프로필
            binding.popularPostProfileImg.setImageBitmap(item.bookmarkPostProfileImg) // 프로필 이미지
            binding.popularPostProfileNickname.text = item.bookmarkPostNickname       // 닉네임
            binding.popularPostProfileDate.text = item.bookmarkPostDate               // 작성일

            // 게시글 내용
            binding.popularPostContentTitle.text = item.bookmarkPostTitle             // 인기글 제목
            binding.popularPostContentText.text = item.bookmarkPostContent            // 인기글 내용
            binding.popularPostContentImg.setImageBitmap(item.bookmarkPostImg)        // 인기글 이미지

            // 기타 정보
            binding.popularPostCommentsNum.text = item.bookmarkPostCommentsCnt.toString() // 댓글 수
            binding.popularPostViewNum.text = item.bookmarkPostViewCnt.toString()         // 조회수
            binding.popularPostGoodNum.text = item.bookmarkPostGoodCnt.toString()         // 좋아요 수
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