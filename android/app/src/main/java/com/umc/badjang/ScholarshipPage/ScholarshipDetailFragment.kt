package com.umc.badjang.ScholarshipPage

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.umc.badjang.MainActivity
import com.umc.badjang.R
import com.umc.badjang.databinding.FragmentScholarshipDetailBinding
import com.umc.badjang.utils.Constants.TAG
import com.umc.badjang.utils.RESPONSE_STATE

class ScholarshipDetailFragment:Fragment() {
    private lateinit var viewBinding: FragmentScholarshipDetailBinding

    private var scholarshipDatas = ArrayList<GetScholarshipDTO>()
    var scholarshipIdx: Long = 1
    
    var activity: MainActivity? = null

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
        viewBinding = FragmentScholarshipDetailBinding.inflate(layoutInflater);

        viewBinding.btnBack.setOnClickListener {
            // 이전 페이지로 이동
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
            requireActivity().supportFragmentManager.popBackStack()
        }

        // 장학금 index값 가져오기
        scholarshipIdx = arguments?.getLong("ItemIdx")!!

        // api에서 index값으로 장학금 정보 가져오기, viewBinding
        RetrofitManager.instance.searchScholarshipIDx(scholarshipIdx = scholarshipIdx, completion = {
                responseState, responseDataArrayList->

            when(responseState) {
                RESPONSE_STATE.OKAY -> {
                    scholarshipDatas = ArrayList<GetScholarshipDTO>(responseDataArrayList)
//                    Log.d(ContentValues.TAG, "디테일 페이지 api 호출 성공 : ${scholarshipDatas[0].scholarship_homepage}")

                    val scholarshipUri = scholarshipDatas[0].scholarship_homepage

                    viewBinding.universityLabel.text = scholarshipDatas[0].scholarship_univ
                    viewBinding.scholarshipTitle.text = scholarshipDatas[0].scholarship_name
                    viewBinding.detailContents.text = scholarshipDatas[0].scholarship_content

                    // 홈페이지 이동
                    viewBinding.btnLink.setOnClickListener {
                        var intent = Intent(Intent.ACTION_VIEW, Uri.parse(scholarshipUri))
                        startActivity(intent)
                    }
                }
                RESPONSE_STATE.FAIL -> {
                    Toast.makeText(requireContext(), "api 호출 에러입니다", Toast.LENGTH_SHORT).show()
                    Log.d(ContentValues.TAG, "api 호출 실패 : ")
                }
            }

        })
        
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}