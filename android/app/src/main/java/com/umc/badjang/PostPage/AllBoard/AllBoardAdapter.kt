package com.umc.badjang.PostPage.AllBoard

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.umc.badjang.Bookmarks.BookmarkPostData
import com.umc.badjang.MainActivity
import com.umc.badjang.MyPage.Noti.DetailNotiFragment
import com.umc.badjang.PostPage.AllPostData
import com.umc.badjang.PostPage.SchoolPostData
import com.umc.badjang.R
import com.umc.badjang.databinding.*

class AllBoardAdapter(private val dataSet: ArrayList<AllPostData>, var context :Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var binding: RvAllPostBinding
    //private lateinit var postViewBinding:

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = RvAllPostBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ViewHolder -> holder.bind(dataSet[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    inner class ViewHolder(private val binding: RvAllPostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AllPostData) {

            binding.allPostCl.setOnClickListener {
//                var fragment = DetailNotiFragment()
//                val bundle = Bundle()
//                bundle.putInt("idx", item.)
//                fragment.arguments = bundle
//                (context as MainActivity).changeFragment(fragment)
            }
            binding.allPostBoardName.text = item.board_name
        }
    }


}