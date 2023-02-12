package com.umc.badjang.HomeMorePage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.badjang.R
import com.umc.badjang.databinding.MainMoreNationalNewsItemBinding

class NationalNewsAdapter(
    private val context: Context,
    val onClickScholarshipBookmark: (scholarshipIdx: Int) -> Unit,
    val onClickSupportBookmark: (position: Int) -> Unit?) :
    RecyclerView.Adapter<NationalNewsAdapter.NationalNewsViewHolder>() {

    private lateinit var viewBinding: MainMoreNationalNewsItemBinding

    var datas = mutableListOf<NationalNewsDataBitmap>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) :
            NationalNewsViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.main_more_national_news_item, parent, false)

        viewBinding = MainMoreNationalNewsItemBinding.bind(view)

        return NationalNewsViewHolder(MainMoreNationalNewsItemBinding.bind(view))
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: NationalNewsViewHolder, position: Int) {
        holder.bind(datas[position], position)
    }

    inner class NationalNewsViewHolder(private val binding: MainMoreNationalNewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: NationalNewsDataBitmap, position: Int) {

            if(item.nationalNewsInstitution != null)
                binding.nationalNewsInstitutionLabel.text = item.nationalNewsInstitution // 기관명
            else
                binding.nationalNewsInstitutionLabel.visibility = View.GONE // 기관명이 없는 경우

            // 전국소식 내용
            binding.nationalNewsCloseTitle.text = item.nationalNewsTitle             // 전국소식 제목 닫힌 버전
            binding.nationalNewsOpenTitle.text = item.nationalNewsTitle              // 전국소식 제목 열린 버전

            if(item.nationalNewsImg != null) {
                // 전국소식 이미지 닫힌 버전
                binding.nationalNewsCloseImg.setImageBitmap(item.nationalNewsImg)
                binding.nationalNewsCloseImg.visibility = View.VISIBLE

                // 전국소식 이미지 열린 버전
                binding.nationalNewsOpenImg.setImageBitmap(item.nationalNewsImg)
                binding.nationalNewsOpenImg.visibility = View.VISIBLE
            }
            else { // 이미지가 없는 경우
                binding.nationalNewsCloseImg.visibility = View.GONE
                binding.nationalNewsOpenImg.visibility = View.GONE
            }

            if(item.nationalNewsContent != null) {
                binding.nationalNewsOpenText.text = item.nationalNewsContent         // 전국소식 내용
                binding.nationalNewsOpenText.visibility = View.VISIBLE
            }
            else
                binding.nationalNewsOpenText.visibility = View.GONE // 내용이 없는 경우

            // 기타 정보
            binding.nationalNewsCommentsNum.text = item.nationalNewsCommentsCnt.toString() // 댓글 수
            binding.nationalNewsViewNum.text = item.nationalNewsViewCnt.toString()         // 조회수


            if(item.bookmarkCheck) {
                binding.nationalNewsBookmarkCheckBtn.visibility = View.GONE
                binding.nationalNewsBookmarkUncheckBtn.visibility = View.VISIBLE
            }

            else {
                binding.nationalNewsBookmarkCheckBtn.visibility = View.VISIBLE
                binding.nationalNewsBookmarkUncheckBtn.visibility = View.GONE
            }

            // 즐겨찾기 체크 버튼 선택 시
            binding.nationalNewsBookmarkCheckBtn.setOnClickListener {
                if(item.scholarshipIdx != null) onClickScholarshipBookmark(position)
                //else if(item.supportIdx != null) onClickSupportBookmark(item.supportIdx)
            }

            // 즐겨찾기 해제 버튼 선택 시
            binding.nationalNewsBookmarkUncheckBtn.setOnClickListener {
                if(item.scholarshipIdx != null) onClickScholarshipBookmark(position)
                //else if(item.supportIdx != null) onClickSupportBookmark(item.supportIdx)
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
}