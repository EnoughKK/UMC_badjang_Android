package com.umc.badjang.SearchMorePage

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.badjang.ApplicationClass
import com.umc.badjang.HomeMorePage.PopularPostData
import com.umc.badjang.HomePagaApi.ImageLoader
import com.umc.badjang.HomePagaApi.MainPopularApiService
import com.umc.badjang.HomePagaApi.PostIdxApiData
import com.umc.badjang.HomePagaApi.PostIdxApiService
import com.umc.badjang.R
import com.umc.badjang.Searchpage.SearchRetrofit
import com.umc.badjang.Searchpage.models.BoardSearchResponse
import com.umc.badjang.Searchpage.models.BoardSearchResult
import com.umc.badjang.Settings.Logout.LogoutRetrofit
import com.umc.badjang.Settings.Logout.models.LogoutRequest
import com.umc.badjang.databinding.FragmentMorePostBinding
import com.umc.badjang.mConnectUserId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

// 전체 > 더보기 > 게시글
class  MorePostFragment : Fragment() {
    private lateinit var viewBinding: FragmentMorePostBinding // viewBinding

    // api 통신을 위한 retrofit
    private var retrofit: Retrofit? = null

    // 게시글 리스트 recyclerview adapter
    private val searchMorePostDatas = mutableListOf<SearchMorePostData>()
    private lateinit var searchMorePostAdapter: SearchMorePostAdapter

    // 게시글 데이터 익명 사용자 프로필 사진
    var profileImg: Bitmap? = null

    val searchRetrofit= ApplicationClass.sRetrofit.create(SearchRetrofit::class.java)


    val jwt = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN,null)
    //user_idx 불러옴
    val useridx = ApplicationClass.bSharedPreferences.getInt(ApplicationClass.USER_IDX,0)

    val text = ApplicationClass.cSharedPreferences.getString(ApplicationClass.QUREY_TEXT,null)



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentMorePostBinding.inflate(layoutInflater);

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mConnectUserId = ApplicationClass.bSharedPreferences.getInt(ApplicationClass.USER_IDX,0)

        // recyclerview 세팅
        initRecycler()

        //임시 데이터
        profileImg = BitmapFactory.decodeResource(resources,R.drawable.non_profile)


        // 게시글 조회 api
        apiMorePost()

        // 임시 데이터
        profileImg = BitmapFactory.decodeResource(resources, R.drawable.non_profile)


        // 이전 버튼 선택 시
        viewBinding.searchmorePostBackBtn.setOnClickListener {
            // 이전 페이지로 이동
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    // 게시글 recyclerview 세팅
    private fun initRecycler() {
        searchMorePostAdapter = SearchMorePostAdapter(requireContext())
        viewBinding.searchmorePostRecyclerview.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        viewBinding.searchmorePostRecyclerview.adapter = searchMorePostAdapter
        searchMorePostAdapter.datas = searchMorePostDatas
    }

    // 게시글 데이터 추가
    private fun addMorePostData(searchMorePost: SearchMorePostData) {
        searchMorePostDatas.apply {
            add(searchMorePost)
        }
        searchMorePostAdapter.notifyDataSetChanged()
    }


    // 게시글 조회 api
    private fun apiMorePost() {
       searchRetrofit.boardsearch(query =text.toString() ).enqueue(object : Callback<BoardSearchResponse> {
                override fun onResponse(call: Call<BoardSearchResponse>, response: Response<BoardSearchResponse>) {
                    Log.d("게시글 더보기","게시글 -------------------------------------------")
                    Log.d("게시글 더보기", "onResponse: ${response.body().toString()}")

                    var allMorePostData: BoardSearchResponse = response.body()!!
                    var postData: MutableList<BoardSearchResult> = allMorePostData.result
                    for(i: Int in (0..postData.size - 1)) {
                        //apiPostData(postData[i].post_idx)
                    }

                }

                override fun onFailure(call: Call<BoardSearchResponse>, t: Throwable) {
                    //Log.d(TAG,"-------------------------------------------")
                    Log.e("검색에서 게시물 더보기 실패", "onFailure: ${t.message}")
                }
            })
    }




}