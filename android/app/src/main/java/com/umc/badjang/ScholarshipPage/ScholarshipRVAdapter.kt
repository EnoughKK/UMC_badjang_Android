package com.umc.badjang.ScholarshipPage

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.umc.badjang.ApplicationClass
import com.umc.badjang.ScholarshipPage.Model.GetScholarshipDTO
import com.umc.badjang.R
import com.umc.badjang.Retrofit.RetrofitManager
import com.umc.badjang.ScholarshipPage.Model.ScholarshipBookmarkDTO
import com.umc.badjang.databinding.RvScholarshipBinding
import com.umc.badjang.utils.RESPONSE_STATE

class ScholarshipRVAdapter(private val context: Context):
    RecyclerView.Adapter<ScholarshipRVAdapter.ScholarshipHolder>() {

    private lateinit var viewBinding: RvScholarshipBinding
    private lateinit var mItemClickListener: OnClickInterface

    // 즐겨찾기 유무 조회 DTO
    private var isBookmarked: Boolean = false

    // 현재 로그인 된 사용자 jwt
    private var jwt: String? = null

    var mScholarshipIDx: Long = 1

    var datas = ArrayList<GetScholarshipDTO>()

    interface OnClickInterface{
        fun onClick(view: View, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) :
            ScholarshipHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.rv_scholarship, parent, false)

        // 현재 로그인 된 사용자 jwt 조회
        jwt = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN, null)

        viewBinding = RvScholarshipBinding.bind(view)

        // 즐겨찾기 유무 확인 API
//        checkScholarshipBookmark()

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

            // 즐겨찾기 버튼 셋팅
//            if(item.isBookmarked!!) {
//
//                viewBinding.btnStarChecked.visibility = View.VISIBLE
//                viewBinding.btnStarUnChecked.visibility = View.INVISIBLE
//            } else {
//
//                viewBinding.btnStarChecked.visibility = View.INVISIBLE
//                viewBinding.btnStarUnChecked.visibility = View.VISIBLE
//            }

            // 즐겨찾기 추가 및 삭제
//            binding.btnStarChecked.setOnClickListener {
//                bookmarkEdit()
//                binding.btnStarChecked.visibility = View.INVISIBLE
//                binding.btnStarUnChecked.visibility = View.VISIBLE
//            }
//            binding.btnStarUnChecked.setOnClickListener {
//                bookmarkEdit()
//                binding.btnStarChecked.visibility = View.VISIBLE
//                binding.btnStarUnChecked.visibility = View.INVISIBLE
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

    // 즐겨찾기 유무 조회 API
//    private fun checkScholarshipBookmark() {
//        RetrofitManager.instance.scholarshipBookmark(jwt!!, datas[0].scholarship_idx!!.toInt(), completion = {
//                responseState, responseDataArrayList ->
//
//            when(responseState){
//                RESPONSE_STATE.OKAY -> {
//                    Log.d(ContentValues.TAG, "api 호출 성공 : ${responseDataArrayList?.size}")
//
//                    val scholarshipBookmarkDatas = ArrayList<ScholarshipBookmarkDTO>(responseDataArrayList)
//
//                }
//                RESPONSE_STATE.FAIL -> {
//                    Toast.makeText(context, "api 호출 에러입니다", Toast.LENGTH_SHORT).show()
//                    Log.d(ContentValues.TAG, "api 호출 실패 : $responseDataArrayList")
//                }
//
//            }
//
//        })
//    }

    // 즐겨찾기 추가 및 삭제 API
    private fun bookmarkEdit() {
        RetrofitManager.instance.bookmarkEdit(jwt!!, datas[0].scholarship_idx!!.toInt())
    }

}