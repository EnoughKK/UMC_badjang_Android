package com.umc.badjang.MyPage.FQA

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
import com.umc.badjang.R
import com.umc.badjang.databinding.*

class FQAAdapter(private val dataSet: ArrayList<FQAData>, var context :Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var myWriteBinding: ItemFqaBinding
    //private lateinit var postViewBinding:

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        myWriteBinding = ItemFqaBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(myWriteBinding)
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

    inner class ViewHolder(private val binding: ItemFqaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FQAData) {
            // 작성자 프로필
            if(item.faq_img == ""){
                binding.itemFqaIvImg.visibility = View.INVISIBLE
            }else{
                Glide.with(context).load(item.faq_img).into(binding.itemFqaIvImg)
            }
            binding.itemFqaTvTitle.text = item.faq_title
            binding.itemFqaTvContent.text = item.faq_content
            binding.fqaCl.setOnClickListener {
                var fragment = DetailFQAFragment()
                val bundle = Bundle()
                bundle.putLong("idx", item.faq_idx)
                fragment.arguments = bundle
                (context as MainActivity).changeFragment(fragment)
            }
        }
    }


}