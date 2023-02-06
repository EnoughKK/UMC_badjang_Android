package com.umc.badjang.HomeMorePage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.badjang.R
import com.umc.badjang.databinding.MainMoreNationalNewsItemBinding

class NationalNewsAdapter(private val context: Context) :
    RecyclerView.Adapter<NationalNewsAdapter.NationalNewsViewHolder>() {

    private lateinit var viewBinding: MainMoreNationalNewsItemBinding

    var datas = mutableListOf<NationalNewsData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) :
            NationalNewsViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.main_more_national_news_item, parent, false)

        viewBinding = MainMoreNationalNewsItemBinding.bind(view)

        return NationalNewsViewHolder(MainMoreNationalNewsItemBinding.bind(view))
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: NationalNewsViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class NationalNewsViewHolder(private val binding: MainMoreNationalNewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: NationalNewsData) {
            binding.nationalNewsInstitutionLabel.text = item.nationalNewsInstitution // 기관명

            // 전국소식 내용 - 닫힌 버전
            binding.nationalNewsCloseTitle.text = item.nationalNewsTitle             // 전국소식 제목
            binding.nationalNewsCloseImg.setImageBitmap(item.nationalNewsImg)        // 전국소식 이미지

            // 전국소식 내용 - 열린 버전
            binding.nationalNewsOpenTitle.text = item.nationalNewsTitle             // 전국소식 제목
            binding.nationalNewsOpenText.text = item.nationalNewsContent            // 전국소식내용
            binding.nationalNewsOpenImg.setImageBitmap(item.nationalNewsImg)        // 전국소식 이미지

            // 기타 정보
            binding.nationalNewsCommentsNum.text = item.nationalNewsCommentsCnt.toString() // 댓글 수
            binding.nationalNewsViewNum.text = item.nationalNewsViewCnt.toString()         // 조회수

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
}