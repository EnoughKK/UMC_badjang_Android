package com.umc.badjang.ScholarshipPage

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.umc.badjang.Model.supportOpiDTO
import com.umc.badjang.R
import com.umc.badjang.databinding.RvSubsidyBinding

class SubsidyRVAdapter (private val context: Context):
    RecyclerView.Adapter<SubsidyRVAdapter.SubsidyHolder>() {

    private lateinit var viewBinding: RvSubsidyBinding

    var datas = ArrayList<supportOpiDTO>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) :
            SubsidyHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.rv_subsidy, parent, false)

        viewBinding = RvSubsidyBinding.bind(view)

        return SubsidyHolder(RvSubsidyBinding.bind(view))
    }

    override fun onBindViewHolder(holder: SubsidyRVAdapter.SubsidyHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int = datas.size

    inner class SubsidyHolder(private val binding: RvSubsidyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: supportOpiDTO) {
            binding.universityLabel.text = item.support_institution
            binding.subsidyCategory.text = item.support_category
            binding.subsidyTitle.text = item.support_name
            binding.subsidyContents.text = item.support_content
            binding.supportAge.text = item.support_age
            binding.supportEducation.text = item.support_education
            binding.supportJob.text = item.support_job
            binding.supportMajor.text = item.support_major
            binding.supportField.text = item.support_field
            binding.supportApply.text = item.support_apply
            binding.supportDate.text = item.support_date
            binding.supportProcedure.text = item.support_procedure
            binding.supportAnnounce.text = item.support_announce
            binding.supportLink.text = item.support_link

            // 상세내용 더보기/접기
            binding.btnViewMore.setOnClickListener {

                when (binding.expandableView.visibility) {
                    View.VISIBLE -> {
                        binding.expandableView.visibility = View.GONE
                        binding.textviewViewMore.text = "더보기"
                        binding.imageViewViewMore.setImageResource(R.drawable.polygon_down)
                    }

                    View.GONE -> {
                        binding.expandableView.visibility = View.VISIBLE
                        binding.textviewViewMore.text = "접기"
                        binding.imageViewViewMore.setImageResource(R.drawable.polygon_up)
                    }
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}