package com.umc.badjang.ScholarshipPage

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.badjang.ApplicationClass
import com.umc.badjang.MainActivity
import com.umc.badjang.R
import com.umc.badjang.Retrofit.RetrofitManager
import com.umc.badjang.ScholarshipPage.Model.GetScholarshipDTO
import com.umc.badjang.ScholarshipPage.Model.ScholarshipCommentsDTO
import com.umc.badjang.databinding.FragmentScholarshipCommentsBinding
import com.umc.badjang.mConnectUserId
import com.umc.badjang.utils.RESPONSE_STATE

class ScholarshipCommentsFragment: Fragment() {
    private lateinit var viewBinding: FragmentScholarshipCommentsBinding

    var activity: MainActivity? = null

    private var commentsDatas = ArrayList<ScholarshipCommentsDTO>()
    private lateinit var commentsAdapter: CommentsRVAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = getActivity() as MainActivity
    }

    override fun onDetach() {
        super.onDetach()
        activity = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentScholarshipCommentsBinding.inflate(layoutInflater);


        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 장학금 index값, 이름 가져오기
        val scholarshipIdx: Int = arguments?.getLong("ItemIdx")!!.toInt()
        val scholarshipName = arguments?.getString("ScholarshipName")

        viewBinding.scholarshipTitle.text = scholarshipName

        loadData(scholarshipIdx)

        if (commentsDatas != null)
            initRecycler()

        // 이전 페이지로 이동
        viewBinding.btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
            requireActivity().supportFragmentManager.popBackStack()
        }

    }

    // 데이터 가져오기 (api 셋팅)
    private fun loadData(scholarshipIdx: Int) {
        RetrofitManager.instance.scholarshipComments(scholarshipIdx = scholarshipIdx, completion = {
                responseState, responseDataArrayList ->

            when(responseState) {
                RESPONSE_STATE.OKAY -> {
                    Log.d(ContentValues.TAG, "api 호출 성공 : ${responseDataArrayList?.size}")
                    commentsDatas = ArrayList<ScholarshipCommentsDTO>(responseDataArrayList)

                }
                RESPONSE_STATE.FAIL -> {
                    Toast.makeText(requireContext(), "api 호출 에러입니다", Toast.LENGTH_SHORT).show()
                    Log.d(ContentValues.TAG, "api 호출 실패 : $responseDataArrayList")
                }
            }

        })
    }

    // recyclerview 셋팅
    private fun initRecycler() {
        Log.d(ContentValues.TAG, "복사 성공 : ${commentsDatas?.size}")

        // 장학금 recyclerview 셋팅
        commentsAdapter = CommentsRVAdapter(requireContext())
        viewBinding.commentsRvContainer.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        viewBinding.commentsRvContainer.adapter = commentsAdapter
        commentsAdapter.datas = commentsDatas

        // 클릭 리스너 셋팅
        commentsAdapter.setItemClickListener(object: CommentsRVAdapter.OnClickInterface{
            override fun onItemLongClick(view: View, position: Int) {

            }
        })
    }
}