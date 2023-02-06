package com.umc.badjang.HomePage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.badjang.R
import com.umc.badjang.databinding.MainNationalNewsItemBinding

class MainNationalNewsAdapter(private val context: Context) :
    RecyclerView.Adapter<MainNationalNewsAdapter.MainNationalNewsViewHolder>() {

    private lateinit var viewBinding: MainNationalNewsItemBinding

    var datas = mutableListOf<MainNationalNewsDataBitmap>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) :
            MainNationalNewsViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.main_national_news_item, parent, false)

        viewBinding = MainNationalNewsItemBinding.bind(view)

        return MainNationalNewsViewHolder(MainNationalNewsItemBinding.bind(view))
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: MainNationalNewsViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class MainNationalNewsViewHolder(private val binding: MainNationalNewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MainNationalNewsDataBitmap) {
            if(item.nationalNewsImage == null)
                binding.mainNationalNewsImg.visibility = View.GONE
            else
                binding.mainNationalNewsImg.setImageBitmap(item.nationalNewsImage) // 글 이미지
            binding.mainNationalNewsTitle.text = item.nationalNewsTitle        // 글 제목
        }
    }
}