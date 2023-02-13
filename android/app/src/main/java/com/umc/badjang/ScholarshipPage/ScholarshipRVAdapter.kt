package com.umc.badjang.ScholarshipPage

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.badjang.ScholarshipPage.Model.GetScholarshipDTO
import com.umc.badjang.R
import com.umc.badjang.databinding.RvScholarshipBinding

class ScholarshipRVAdapter(private val context: Context):
    RecyclerView.Adapter<ScholarshipRVAdapter.ScholarshipHolder>() {

    private lateinit var viewBinding: RvScholarshipBinding
    private lateinit var mItemClickListener: OnClickInterface

    var mScholarshipIDx: Long = 1

    var datas = ArrayList<GetScholarshipDTO>()

    interface OnClickInterface{
        fun onClick(view: View, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) :
            ScholarshipHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.rv_scholarship, parent, false)

        viewBinding = RvScholarshipBinding.bind(view)

        return ScholarshipHolder(RvScholarshipBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ScholarshipRVAdapter.ScholarshipHolder, position: Int) {
        holder.bind(datas[position])
        holder.itemView.setOnClickListener {
            mItemClickListener.onClick(it, position)
        }
    }

    override fun getItemCount(): Int = datas.size

    inner class ScholarshipHolder(private val binding: RvScholarshipBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GetScholarshipDTO) {
            Log.d("로그", "${datas.size}")

            mScholarshipIDx = item.scholarship_idx!!

            binding.universityLabel.text = item.scholarship_univ                  // 대학교 이름
            binding.scholarshipCategory.text = item.scholarship_category          // 장학금 카테고리
            binding.scholarshipTitle.text = item.scholarship_name                 // 장학금 이름
            binding.scholarshipContents.text = item.scholarship_content           // 장학금 상세내용
            binding.textViewViews.text = item.scholarship_view.toString()         // 장학금 뷰어수
            binding.textViewComments.text = item.scholarship_comment.toString()   // 장학금 댓글수

//            if(item.bookMark == true){
//                binding.btnStarUnChecked.visibility = View.INVISIBLE
//                binding.btnStarChecked.visibility = View.VISIBLE
//            } else {
//                binding.btnStarUnChecked.visibility = View.VISIBLE
//                binding.btnStarChecked.visibility = View.INVISIBLE
//            }

            // 즐겨찾기 추가
//            binding.btnStarUnChecked.setOnClickListener {
//
//                // 즐겨찾기 버튼 활성화/비활성화
//                binding.btnStarUnChecked.visibility = View.INVISIBLE
//                binding.btnStarChecked.visibility = View.VISIBLE
//
//                item.bookMark = true // DTO에 즐겨찾기 추가? <- 임시
//
//            }

            // 즐겨찾기 해제
//            binding.btnStarChecked.setOnClickListener {
//
//                // 즐겨찾기 버튼 활성화/비활성화
//                binding.btnStarUnChecked.visibility = View.VISIBLE
//                binding.btnStarChecked.visibility = View.INVISIBLE
//
//                item.bookMark = false // DTO에 즐겨찾기 해제? <- 임시
//
//            }

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

    // 장학금 상세 페이지 이동
    fun setItemClickListener(itemClickListener: OnClickInterface){
        mItemClickListener = itemClickListener
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

}