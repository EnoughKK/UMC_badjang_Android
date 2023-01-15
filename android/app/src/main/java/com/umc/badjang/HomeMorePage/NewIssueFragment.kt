package com.umc.badjang.HomeMorePage

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.badjang.R
import com.umc.badjang.databinding.FragmentNewIssueBinding

// 홈화면 > 알림
class NewIssueFragment : Fragment() {
    private lateinit var viewBinding: FragmentNewIssueBinding // viewBinding

    // 알림 리스트 recyclerview adapter
    private val newIssueDatas = mutableListOf<NewIssueData>()
    private lateinit var newIssueAdapter: NewIssueAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentNewIssueBinding.inflate(layoutInflater);

        return viewBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // recyclerview 세팅
        initRecycler()

        // 알림 데이터 추가
        for(i: Int in 0..3) {
            addNewIssueData(
                NewIssueData("도로교통부", "행복주택 공급 공고(이)가 등록되었습니다."))
        }

        // 이전 버튼 선택 시
        viewBinding.newIssueBackBtn.setOnClickListener {
            // 이전 페이지로 이동
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    // 알림 recyclerview 세팅
    private fun initRecycler() {
        newIssueAdapter = NewIssueAdapter(requireContext())
        viewBinding.newIssueRecyclerview.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        viewBinding.newIssueRecyclerview.adapter = newIssueAdapter
        newIssueAdapter.datas = newIssueDatas
    }

    // 알림 데이터 추가
    private fun addNewIssueData(newIssue: NewIssueData) {
        newIssueDatas.apply {
            add(newIssue)
        }
        newIssueAdapter.notifyDataSetChanged()
    }
}