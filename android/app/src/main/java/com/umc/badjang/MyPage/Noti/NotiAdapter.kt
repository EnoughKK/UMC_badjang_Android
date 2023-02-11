package com.umc.badjang.MyPage.Noti

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.umc.badjang.ApplicationClass
import com.umc.badjang.Bookmarks.BookmarkPostData
import com.umc.badjang.MainActivity
import com.umc.badjang.MyPage.MyWrite.MyWriteRetrofitInterface
import com.umc.badjang.MyPage.MyWrite.WriteData
import com.umc.badjang.MyPage.MyWrite.model.MyWriteRes
import com.umc.badjang.MyPage.Noti.model.GetNotiResponse
import com.umc.badjang.R
import com.umc.badjang.Settings.SettingsFragment
import com.umc.badjang.databinding.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotiAdapter(private val dataSet: ArrayList<NotiData>, var context :Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var binding: ItemNotiBinding
    //private lateinit var postViewBinding:

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = ItemNotiBinding.inflate(LayoutInflater.from(parent.context),parent, false)
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

    inner class ViewHolder(private val binding: ItemNotiBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NotiData) {
            var idx = item.notice_idx
            if(item.notice_image == ""){
                binding.itemNotiIvImg.visibility = View.INVISIBLE
            }else{
                Glide.with(context).load(item.notice_image).into(binding.itemNotiIvImg)
            }
            binding.itemNotiTvTitle.text = item.notice_title
            binding.itemNotiTvContent.text = item.notice_content
            binding.notiCl.setOnClickListener {
                var fragment = DetailNotiFragment()
                val bundle = Bundle()
                bundle.putInt("idx", item.notice_idx)
                fragment.arguments = bundle
                (context as MainActivity).changeFragment(fragment)
            }
        }
    }

}