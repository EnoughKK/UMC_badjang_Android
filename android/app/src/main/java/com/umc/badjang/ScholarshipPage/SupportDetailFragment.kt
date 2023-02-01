package com.umc.badjang.ScholarshipPage

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.umc.badjang.MainActivity
import com.umc.badjang.Model.GetScholarshipDTO
import com.umc.badjang.Model.GetSupportDTO
import com.umc.badjang.Retrofit.RetrofitManager
import com.umc.badjang.databinding.FragmentScholarshipDetailBinding
import com.umc.badjang.utils.RESPONSE_STATE

class SupportDetailFragment: Fragment() {
    private lateinit var viewBinding: FragmentScholarshipDetailBinding

    private var supportDatas = ArrayList<GetSupportDTO>()
    var support_idx: Long = 1

    var activity: MainActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = getActivity() as MainActivity
    }

    override fun onDetach() {
        super.onDetach()
        activity = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener("requestKey") { requestKey, bundle ->
            //결과 값을 받는곳입니다.
            support_idx = bundle.getLong("bundleKey")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentScholarshipDetailBinding.inflate(layoutInflater);

        viewBinding.btnBack.setOnClickListener {
            // 이전 페이지로 이동
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
            requireActivity().supportFragmentManager.popBackStack()
        }

        // 장학금 홈페이지 uri
        val supportUri = supportDatas[0].support_homepage

        viewBinding.universityLabel.text = supportDatas[0].support_univ
        viewBinding.scholarshipTitle.text = supportDatas[0].support_name
        viewBinding.detailContents.text = supportDatas[0].support_content

        // 홈페이지 이동
        viewBinding.btnLink.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse(supportUri))
            startActivity(intent)
        }

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }


}