package com.umc.badjang.PostPage

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.badjang.ApplicationClass
import com.umc.badjang.MainActivity
import com.umc.badjang.PostPage.Board.Model.GetPopularPostBoardResponse
import com.umc.badjang.PostPage.Board.Model.GetSchoolPostBoardResponse
import com.umc.badjang.PostPage.Board.PopularPostAdapter
import com.umc.badjang.PostPage.Board.PostBoardRetrofitInterface
import com.umc.badjang.PostPage.Board.SchoolPostBoardAdapter
import com.umc.badjang.R
import com.umc.badjang.databinding.FragmentPostBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

data class AllPostData(val board_idx : Int, val board_name : String)
data class PopularPostData(val popular_idx : Int,
                           val post_idx : Int,
                           val user_idx : Int,
                           val school_name_idx : Int,
                           val popular_createAt : String,
                           val popular_updateAt : String,
                           val popular_status : String,
                           val count : Int,
                           val user_name : String?,
                           val board_category : String,
                           val post_anonymity : String,
                           val user_profileimage_url : String,
                           val popular_content : String,
                           val post_image : String?,
                           val post_view : Int,
                           val post_recommend : Int,
                           val post_name : String)
data class SchoolPostData(val school_name_idx : Int,
                          val post_school_name : String)
class PostFragment : Fragment() {
    private lateinit var binding: FragmentPostBinding // viewBinding

    private lateinit var popularAdapter : PopularPostAdapter
    private var dataList = arrayListOf<AllPostData>()
    private var schoolDataList = arrayListOf<SchoolPostData>()
    private var popularDataList = arrayListOf<PopularPostData>()
    var activity: MainActivity? = null
    lateinit var allPostAdapter : AllPostAdapter
    lateinit var schoolPostAdapter : SchoolPostBoardAdapter
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
        binding = FragmentPostBinding.inflate(layoutInflater);

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar: Toolbar = requireActivity().findViewById(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        dataList.add(AllPostData(1, "자유게시판"))
        dataList.add(AllPostData(2, "인기게시판"))
        dataList.add(AllPostData(3, "부경대학교 게시판"))
        dataList.add(AllPostData(4, "경상국립대학교 게시판"))
        allPostAdapter = AllPostAdapter(dataList, requireContext())
        //allPostAdapter.notifyDataSetChanged()
        binding.allPostRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        binding.allPostRv.adapter = allPostAdapter

        schoolPostAdapter = SchoolPostBoardAdapter(schoolDataList, requireContext())
        getSchoolPostBoard()
        binding.schoolPostRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        binding.schoolPostRv.adapter = schoolPostAdapter

        popularAdapter = PopularPostAdapter(popularDataList, requireContext())
        getPopular2SchoolPostBoard()
        binding.popularPostRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        binding.popularPostRv.adapter = popularAdapter

    }
    private fun getSchoolPostBoard(){
        //Log.d("postScholarship", "호출은 된다.")
        val getSchoolPostBoardInterface = ApplicationClass.sRetrofit.create(PostBoardRetrofitInterface::class.java)
        getSchoolPostBoardInterface.getSchoolPostBoard().enqueue(object :
            Callback<GetSchoolPostBoardResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<GetSchoolPostBoardResponse>, response: Response<GetSchoolPostBoardResponse>) {
                if (response.isSuccessful) {
                    val result = response.body() as GetSchoolPostBoardResponse
                    if(result.message == "요청에 성공하였습니다."){
                        for(i in 0 until result.result.size){

                            schoolDataList.add(
                                SchoolPostData(result.result[i].school_name_idx, result.result[i].post_school_name)
                            )
                        }
                        schoolPostAdapter.notifyDataSetChanged()
                    }
                    else{
                        Log.d("getProfile", "onResponse : Error code ${response.code()}")
                        Log.d("getProfile", "onResponse : Error message ${response.message()}")
                    }
                }
                else{
                    Log.d("getProfile", "onResponse : Error code ${response.code()}")
                }
            }
            override fun onFailure(call: Call<GetSchoolPostBoardResponse>, t: Throwable) {
                Log.d("getProfile", t.message ?: "통신오류")
            }
        })

    }
    private fun getPopular2SchoolPostBoard(){
        //Log.d("postScholarship", "호출은 된다.")
        val getPopularPostBoardInterface = ApplicationClass.sRetrofit.create(PostBoardRetrofitInterface::class.java)
        getPopularPostBoardInterface.getPopular2PostBoard().enqueue(object :
            Callback<GetPopularPostBoardResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<GetPopularPostBoardResponse>, response: Response<GetPopularPostBoardResponse>) {
                if (response.isSuccessful) {
                    val result = response.body() as GetPopularPostBoardResponse
                    if(result.message == "요청에 성공하였습니다."){
                        for(i in 0 until result.result.size){
                            popularDataList.add(
                                PopularPostData(result.result[i].popular_idx,
                                    result.result[i].post_idx, result.result[i].user_idx,
                                    result.result[i].school_name_idx, result.result[i].popular_createAt,
                                    result.result[i].popular_updateAt, result.result[i].popular_status,
                                    result.result[i].count, result.result[i].user_name,
                                    result.result[i].board_category, result.result[i].post_anonymity,
                                    result.result[i].user_profileimage_url, result.result[i].popular_content,
                                    result.result[i].post_image, result.result[i].post_view,
                                    result.result[i].post_recommend, result.result[i].post_name)
                            )
                        }
                        popularAdapter.notifyDataSetChanged()
                    }
                    else{
                        Log.d("getProfile", "onResponse : Error code ${response.code()}")
                        Log.d("getProfile", "onResponse : Error message ${response.message()}")
                    }
                }
                else{
                    Log.d("getProfile", "onResponse : Error code ${response.code()}")
                }
            }
            override fun onFailure(call: Call<GetPopularPostBoardResponse>, t: Throwable) {
                Log.d("getProfile", t.message ?: "통신오류")
            }
        })

    }
    private fun initRecycler() {

    }

}