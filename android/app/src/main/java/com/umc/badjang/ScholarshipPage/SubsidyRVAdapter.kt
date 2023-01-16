package com.umc.badjang.ScholarshipPage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.badjang.R
import com.umc.badjang.databinding.RvSubsidyBinding

class SubsidyRVAdapter (private val context: Context):
    RecyclerView.Adapter<SubsidyRVAdapter.SubsidyHolder>() {

    private lateinit var viewBinding: RvSubsidyBinding

    var datas = mutableListOf<SubsidyRVDTO>()

    interface OnClickInterface{
        fun onClick(view: RvSubsidyBinding, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) :
            SubsidyHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.rv_subsidy, parent, false)

        viewBinding = RvSubsidyBinding.bind(view)

        return SubsidyHolder(RvSubsidyBinding.bind(view))
    }

    override fun onBindViewHolder(holder: SubsidyRVAdapter.SubsidyHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int = datas.size

    inner class SubsidyHolder(private val binding: RvSubsidyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SubsidyRVDTO) {
            binding.universityLabel.text = item.universityLabel                   // 대학교 이름
            binding.subsidyCategory.text = item.subsidyCategory             // 지원금 카테고리
            binding.subsidyTitle.text = item.subsidyTitle                     // 지원금 이름
            binding.subsidyContents.text = item.subsidyContents               // 지원금 상세내용
            binding.textViewViews.text = item.subsidyViews.toString()         // 지원금 뷰어수
            binding.textViewComments.text = item.subsidyComments.toString()   // 지원금 댓글수

            // 즐겨찾기 추가
            binding.btnStarUnChecked.setOnClickListener {

                // 즐겨찾기 버튼 활성화/비활성화
                binding.btnStarUnChecked.visibility = View.INVISIBLE
                binding.btnStarChecked.visibility = View.VISIBLE

                item.bookMark = true // DTO에 즐겨찾기 추가? <- 테스트필요

            }

            // 즐겨찾기 해제
            binding.btnStarChecked.setOnClickListener {

                // 즐겨찾기 버튼 활성화/비활성화
                binding.btnStarUnChecked.visibility = View.VISIBLE
                binding.btnStarChecked.visibility = View.INVISIBLE

                item.bookMark = false // DTO에 즐겨찾기 해제? <- 테스트필요

            }

            // 상세내용 더보기/접기
            binding.btnViewMore.setOnClickListener {

                when (binding.expandableView.visibility) {
                    View.VISIBLE -> {
                        binding.expandableView.visibility = View.GONE
                        binding.textviewViewMore.text = "더보기"
                        binding.imageViewViewMore.setImageResource(R.drawable.polygon_down)
                    }

                    View.GONE -> {
                        binding.expandableView.visibility = View.VISIBLE
                        binding.textviewViewMore.text = "접기"
                        binding.imageViewViewMore.setImageResource(R.drawable.polygon_up)
                    }
                }
            }

        }

    }

}