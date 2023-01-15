package com.umc.badjang.HomeMorePage

import android.content.Context
import android.view.LayoutInflater
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

            // 전국소식 내용
            binding.nationalNewsTitle.text = item.nationalNewsTitle             // 전국소식 제목
            //binding.nationalNewsContents.text = item.nationalNewsContent      // 전국소식내용
            binding.nationalNewsImg.setImageBitmap(item.nationalNewsImg)        // 전국소식 이미지

            // 기타 정보
            binding.nationalNewsCommentsNum.text = item.nationalNewsCommentsCnt.toString() // 댓글 수
            binding.nationalNewsViewNum.text = item.nationalNewsViewCnt.toString()         // 조회수
        }
    }
}