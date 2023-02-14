package com.umc.badjang.HomePage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.badjang.R
import com.umc.badjang.databinding.MainMySchoolItemBinding

class MainMySchoolAdapter(
        private val context: Context,
        val onClickBookmark: (position: Int) -> Unit) :
    RecyclerView.Adapter<MainMySchoolAdapter.MainMySchoolViewHolder>() {

    private lateinit var viewBinding: MainMySchoolItemBinding

    var datas = mutableListOf<MainMySchoolData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) :
            MainMySchoolViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.main_my_school_item, parent, false)

        viewBinding = MainMySchoolItemBinding.bind(view)

        return MainMySchoolViewHolder(MainMySchoolItemBinding.bind(view))
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: MainMySchoolViewHolder, position: Int) {
        holder.bind(datas[position], position)
    }

    inner class MainMySchoolViewHolder(private val binding: MainMySchoolItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MainMySchoolData, position: Int) {
            binding.mainMySchoolPostNum.text = item.mySchoolNum.toString()     // 글 번호
            binding.mainMySchoolPostTitle.text = item.mySchoolTitle            // 글 제목
            binding.mainMySchoolViewNum.text = item.mySchoolViewNum.toString() // 조회수

            if(item.mySchoolBookmark) {
                binding.mainMySchoolStarCheckBtn.visibility = View.GONE
                binding.mainMySchoolStarUncheckBtn.visibility = View.VISIBLE
            }
            else {
                binding.mainMySchoolStarCheckBtn.visibility = View.VISIBLE
                binding.mainMySchoolStarUncheckBtn.visibility = View.GONE
            }

            // 즐겨찾기 체크 버튼 선택 시
            binding.mainMySchoolStarCheckBtn.setOnClickListener {
                onClickBookmark(position) // 즐겨찾기 선택한 장학금 idx 넘겨주기
            }

            // 즐겨찾기 해제 버튼 선택 시
            binding.mainMySchoolStarUncheckBtn.setOnClickListener {
                onClickBookmark(position) // 즐겨찾기 선택한 장학금 idx 넘겨주기
            }
        }
    }
}