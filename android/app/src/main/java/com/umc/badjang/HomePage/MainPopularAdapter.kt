package com.umc.badjang.HomePage

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.badjang.R
import com.umc.badjang.databinding.MainPopularItemBinding

class MainPopularAdapter(private val context: Context) :
    RecyclerView.Adapter<MainPopularAdapter.MainPopularViewHolder>() {

    private lateinit var viewBinding: MainPopularItemBinding

    var datas = mutableListOf<MainPopularData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) :
            MainPopularViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.main_popular_item, parent, false)

        viewBinding = MainPopularItemBinding.bind(view)

        return MainPopularViewHolder(MainPopularItemBinding.bind(view))
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: MainPopularViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class MainPopularViewHolder(private val binding: MainPopularItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MainPopularData) {
            binding.mainPopularPostNum.text = item.popularNum.toString()     // 글 번호
            binding.mainMySchoolPostTitle.text = item.popularTitle           // 글 제목
            binding.mainPopularGoodNum.text = item.popularGoodNum.toString() // 좋아요 수
            binding.mainPopularViewNum.text = item.popularViewNum.toString() // 조회수
        }
    }
    
}