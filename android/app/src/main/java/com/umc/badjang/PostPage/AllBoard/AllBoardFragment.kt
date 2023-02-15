package com.umc.badjang.PostPage.AllBoard

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.badjang.ApplicationClass
import com.umc.badjang.MainActivity
import com.umc.badjang.MyPage.MyWrite.model.MyWriteRes
import com.umc.badjang.MyPage.Noti.NotiAdapter
import com.umc.badjang.MyPage.QNA.QData
import com.umc.badjang.MyPage.QNA.QNARetrofitInterface
import com.umc.badjang.PostPage.AllBoard.Model.GetAllPostBoardResponse
import com.umc.badjang.PostPage.AllPostAdapter
import com.umc.badjang.PostPage.AllPostData
import com.umc.badjang.PostPage.Board.Model.GetAllPopularPostBoardResponse
import com.umc.badjang.PostPage.Board.Model.GetPopularPostBoardResponse
import com.umc.badjang.PostPage.Board.Model.GetPostBoardResponse
import com.umc.badjang.PostPage.Board.Model.GetSchoolPostBoardResponse
import com.umc.badjang.PostPage.Board.PostBoardFragment
import com.umc.badjang.PostPage.Board.PostBoardRetrofitInterface
import com.umc.badjang.PostPage.Board.SchoolPostBoardAdapter
import com.umc.badjang.PostPage.PopularPostData
import com.umc.badjang.PostPage.SchoolPostData
import com.umc.badjang.databinding.FragmentAllBoardBinding
import com.umc.badjang.databinding.FragmentPostBoardBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllBoardFragment : Fragment() {
    private lateinit var binding: FragmentAllBoardBinding // viewBinding

    private var dataList = arrayListOf<AllPostData>()
    private lateinit var allPostAdapter : AllBoardAdapter
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
        binding = FragmentAllBoardBinding.inflate(layoutInflater);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        allPostAdapter = AllBoardAdapter(dataList, requireContext())
        getAllBoard()
        binding.allBoardRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        binding.allBoardRv.adapter = allPostAdapter
        binding.tvFreeBoard.setOnClickListener {
            var fragment = PostBoardFragment()
            val bundle = Bundle()
            bundle.putString("name", "자유게시판")
            fragment.arguments = bundle
            (context as MainActivity).changeFragment(fragment)
        }
        binding.tvPopularBoard.setOnClickListener {
            var fragment = PostBoardFragment()
            val bundle = Bundle()
            bundle.putString("name", "인기게시판")
            fragment.arguments = bundle
            (context as MainActivity).changeFragment(fragment)
        }

    }

    private fun getAllBoard(){
        //Log.d("postScholarship", "호출은 된다.")
        val getAllBoardInterface = ApplicationClass.sRetrofit.create(AllBoardRetrofitInterface::class.java)
        getAllBoardInterface.getAllSchoolPostBoard().enqueue(object :
            Callback<GetAllPostBoardResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<GetAllPostBoardResponse>, response: Response<GetAllPostBoardResponse>) {
                if (response.isSuccessful) {
                    val result = response.body() as GetAllPostBoardResponse
                    if(result.message == "요청에 성공하였습니다."){
                        for(i in 0 until result.result.size){

                            dataList.add(
                                AllPostData(result.result[i].school_name_idx, result.result[i].post_school_name)
                            )
                        }
                        allPostAdapter.notifyDataSetChanged()
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
            override fun onFailure(call: Call<GetAllPostBoardResponse>, t: Throwable) {
                Log.d("getProfile", t.message ?: "통신오류")
            }
        })

    }

}