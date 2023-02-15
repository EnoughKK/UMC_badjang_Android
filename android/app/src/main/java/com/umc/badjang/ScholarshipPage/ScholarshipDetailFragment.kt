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
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.umc.badjang.Bookmarks.BookmarksFragment
import com.umc.badjang.HomeMorePage.NewIssueFragment
import com.umc.badjang.MainActivity
import com.umc.badjang.R
import com.umc.badjang.ScholarshipPage.Model.GetScholarshipDTO
import com.umc.badjang.Retrofit.RetrofitManager
import com.umc.badjang.databinding.FragmentScholarshipDetailBinding
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentScholarshipDetailBinding.inflate(layoutInflater);

        // 즐겨찾기 버튼 선택
        viewBinding.btnFavorites.setOnClickListener {
            activity?.changeFragment(BookmarksFragment())
        }

        // 알림 버튼 선택
        viewBinding.btnAllam.setOnClickListener {
            activity?.changeFragment(NewIssueFragment())
        }

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
                    val scholarshipName = scholarshipDatas[0].scholarship_name.toString()

                    viewBinding.universityLabel.text = scholarshipDatas[0].scholarship_univ
                    viewBinding.scholarshipTitle.text = scholarshipDatas[0].scholarship_name
                    viewBinding.detailContents.text = scholarshipDatas[0].scholarship_content

                    // 장학금 이미지
                    if(scholarshipDatas[0].scholarship_image != "https://firebasestorage.googleapis.com/v0/b/badjang-88139.appspot.com/o/%EC%9D%B4%EB%AF%B8%EC%A7%80%20x.png?alt=media&token=67666132-9ba2-4f48-bb40-8aa178fcbad7")
                        viewBinding.scholarshipImage.visibility = View.VISIBLE

                    Glide.with(requireContext())
                        .load(scholarshipDatas[0].scholarship_image)
                        .into(viewBinding.scholarshipImage)

                    // 홈페이지 이동
                    viewBinding.btnLink.setOnClickListener {
                        var intent = Intent(Intent.ACTION_VIEW, Uri.parse(scholarshipUri))
                        startActivity(intent)
                    }

                    viewBinding.btnComments.setOnClickListener {
                        // 장학금 디테일 페이지로 전환
                        activity?.SendDataFragment(ScholarshipCommentsFragment(), scholarshipIdx, scholarshipName)
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