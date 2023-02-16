package com.umc.badjang.SearchMorePage

import com.umc.badjang.HomeMorePage.NationalNewsAdapter
import com.umc.badjang.HomeMorePage.NationalNewsData
import com.umc.badjang.HomeMorePage.NationalNewsDataBitmap
import com.umc.badjang.Searchpage.SearchRetrofit
import com.umc.badjang.databinding.FragmentMoreScholarshipBinding

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.badjang.ApplicationClass
import com.umc.badjang.HomePagaApi.*
import com.umc.badjang.HomePage.MainMySchoolData
import com.umc.badjang.R
import com.umc.badjang.databinding.FragmentMySchoolBinding
import com.umc.badjang.mConnectUserId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

// 홈화면 > 우리학교 장학금
class MoreScholarshipFragment : Fragment() {
    private lateinit var viewBinding: FragmentMoreScholarshipBinding // viewBinding

    // api 통신을 위한 retrofit
    private var retrofit: Retrofit? = null

    // 현재 로그인 된 사용자 jwt
    private var jwt: String? = null

    //  장학금 recyclerview adapter
    private val searchScholarshipDatas = mutableListOf<NationalNewsDataBitmap>()
    private lateinit var searchScholarshipAdapter: NationalNewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentMoreScholarshipBinding.inflate(layoutInflater);

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 현재 로그인 된 사용자 jwt 조회
        jwt = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN, null)

        // retrofit 세팅
        retrofit = MainApiClient.mainApiRetrofit

        // 이전 버튼 선택 시
        viewBinding.searchmoreScholarshipBackBtn.setOnClickListener {
            // 이전 페이지로 이동
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
            requireActivity().supportFragmentManager.popBackStack()
        }

        // recyclerview 세팅
        initRecycler()

    }

    // 장학금 recyclerview 세팅
    private fun initRecycler() {
        searchScholarshipAdapter = NationalNewsAdapter(
            requireContext(),
            onClickScholarshipBookmark = {
               // apiBookmarkMySchool(it)
            },
            onClickSupportBookmark = {
                null
            })
        viewBinding.searchmoreScholarshipRecyclerview.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        viewBinding.searchmoreScholarshipRecyclerview.adapter = searchScholarshipAdapter
        searchScholarshipAdapter.datas = searchScholarshipDatas
    }



}