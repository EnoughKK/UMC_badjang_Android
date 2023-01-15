package com.umc.badjang.HomeMorePage

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.badjang.R
import com.umc.badjang.databinding.MainMoreNewIssueItemBinding

class NewIssueAdapter(private val context: Context) :
    RecyclerView.Adapter<NewIssueAdapter.NewIssueViewHolder>() {

    private lateinit var viewBinding: MainMoreNewIssueItemBinding

    var datas = mutableListOf<NewIssueData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) :
            NewIssueViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.main_more_new_issue_item, parent, false)

        viewBinding = MainMoreNewIssueItemBinding.bind(view)

        return NewIssueViewHolder(MainMoreNewIssueItemBinding.bind(view))
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: NewIssueViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class NewIssueViewHolder(private val binding: MainMoreNewIssueItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: NewIssueData) {
            binding.newIssueInstitutionLabel.text = item.newIssueInstitution  // 알림 기관명
            binding.newIssueContent.text = item.newIssueContent               // 알림 내용
        }
    }
}