package com.umc.badjang.HomePage

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.badjang.R
import com.umc.badjang.databinding.MainMySchoolItemBinding

class MainMySchoolAdapter(private val context: Context) :
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
        holder.bind(datas[position])
    }

    inner class MainMySchoolViewHolder(private val binding: MainMySchoolItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MainMySchoolData) {
            binding.mainMySchoolPostNum.text = item.mySchoolNum.toString()     // 글 번호
            binding.mainMySchoolPostTitle.text = item.mySchoolTitle            // 글 제목
            binding.mainMySchoolViewNum.text = item.mySchoolViewNum.toString() // 조회수
        }
    }
}