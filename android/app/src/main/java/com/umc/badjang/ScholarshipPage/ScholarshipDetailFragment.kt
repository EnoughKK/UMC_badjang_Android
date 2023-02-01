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
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.umc.badjang.MainActivity
import com.umc.badjang.Model.GetScholarshipDTO
import com.umc.badjang.R
import com.umc.badjang.Retrofit.RetrofitManager
import com.umc.badjang.databinding.FragmentScholarshipDetailBinding
import com.umc.badjang.utils.RESPONSE_STATE

class ScholarshipDetailFragment:Fragment() {
    private lateinit var viewBinding: FragmentScholarshipDetailBinding

    private var scholarshipDatas = ArrayList<GetScholarshipDTO>()
    var scholarship_idx: Long = 1

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
            scholarship_idx = bundle.getLong("bundleKey")
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

        loadData()

        // 장학금 홈페이지 uri
        val scholarshipUri = scholarshipDatas[0].scholarship_homepage

        viewBinding.universityLabel.text = scholarshipDatas[0].scholarship_univ
        viewBinding.scholarshipTitle.text = scholarshipDatas[0].scholarship_name
        viewBinding.detailContents.text = scholarshipDatas[0].scholarship_content

        viewBinding.btnLink.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse(scholarshipUri))
            startActivity(intent)
        }

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    // 데이터 로드
    private fun loadData() {
        RetrofitManager.instance.searchScholarshipIDx(scholarshipIdx = scholarship_idx, completion = {
                responseState, responseDataArrayList ->

            when(responseState) {
                RESPONSE_STATE.OKAY -> {
                    Log.d(ContentValues.TAG, "api 호출 성공 : ${responseDataArrayList?.size}")
                    scholarshipDatas = ArrayList<GetScholarshipDTO>(responseDataArrayList)

                }
                RESPONSE_STATE.FAIL -> {
                    Toast.makeText(requireContext(), "api 호출 에러입니다", Toast.LENGTH_SHORT).show()
                    Log.d(ContentValues.TAG, "api 호출 실패 : $responseDataArrayList")
                }
            }

        })
    }

}