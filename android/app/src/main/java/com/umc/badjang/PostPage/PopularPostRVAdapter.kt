package com.umc.badjang.PostPage

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.badjang.R
import com.umc.badjang.databinding.RvPopularPostBinding

class PopularPostRVAdapter(private val context: Context) :
    RecyclerView.Adapter<PopularPostRVAdapter.PopularPostHolder>(){

    private lateinit var viewBinding: RvPopularPostBinding

    var datas = mutableListOf<PopularPostDTO>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            PopularPostRVAdapter.PopularPostHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.rv_popular_post, parent, false)

        viewBinding = RvPopularPostBinding.bind(view)

        return PopularPostHolder(RvPopularPostBinding.bind(view))
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: PopularPostRVAdapter.PopularPostHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class PopularPostHolder(private val binding: RvPopularPostBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PopularPostDTO) {

        }
    }

}