package com.umc.badjang.PostPage.Board

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
import com.umc.badjang.PostPage.AllPostData
import com.umc.badjang.PostPage.Board.Model.GetAllPopularPostBoardResponse
import com.umc.badjang.PostPage.Board.Model.GetAllSchoolPostResponse
import com.umc.badjang.PostPage.Board.Model.GetPopularPostBoardResponse
import com.umc.badjang.PostPage.Board.Model.GetPostBoardResponse
import com.umc.badjang.PostPage.Detail.DetailPostFragment
import com.umc.badjang.PostPage.PopularPostData
import com.umc.badjang.PostPage.PostFragment
import com.umc.badjang.PostWritePage.PostWriteFragment
import com.umc.badjang.databinding.FragmentPostBoardBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

data class BoardData(val post_idx : Int,
                     val user_idx : Int,
                     val post_category : String,
                     val post_name : String,
                     val post_content : String,
                     val post_image : String?,
                     val post_view : Int,
                     val post_recommend : Int,
                     val post_comment : Int,
                     val post_createAt : String,
                     val post_updateAt : String,
                     val post_status : String,
                     val post_anonymity : String,
                     val school_name_idx : Int,
                     val post_bookmark : Int,
                     val user_name : String?,
                     val user_profileimage_url : String)

data class PopularPostBoardData(val popular_idx : Int,
                                val post_idx : Int,
                                val user_idx : Int,
                                val school_name_idx : Int,
                                val post_content: String,
                                val post_createAt : String,
                                val post_updateAt : String,
                                val postp_status : String,
                                val count : Int,
                                val user_name : String?,
                                val post_category : String,
                                val post_anonymity : String,
                                val user_profileimage_url : String,
                                val post_image : String?,
                                val post_view : Int,
                                val post_recommend : Int,
                                val post_name : String,
                                val post_comment : Int
)

data class SchoolData(val post_idx : Int,
                     val user_idx : Int,
                      val user_name : String,
                      val user_profileimage_url : String,
                     val post_name : String,
                     val post_content : String,
                     val post_image : String?,
                     val post_view : Int,
                     val post_recommend : Int,
                     val post_comment : Int,
                      val post_anonymity : String,
                      val post_category:String,
                      val post_school_name : String,
                     val post_createAt : String,
                     val recommend_check : Int)

class PostBoardFragment : Fragment() {
    private lateinit var binding: FragmentPostBoardBinding // viewBinding

    private lateinit var rvAdapter : PostBoardAdapter
    private lateinit var popularAdapter : PopularPostBoardAdapter
    private var dataList = arrayListOf<BoardData>()
    private var popularDataList = arrayListOf<PopularPostBoardData>()

    private lateinit var schoolAdapter : SchoolAdapter
    private var schoolDataList = arrayListOf<SchoolData>()

    var activity: MainActivity? = null
    var category_idx = -1
    var category_name = ""
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
        binding = FragmentPostBoardBinding.inflate(layoutInflater);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(requireArguments().getString("name") == "???????????????"){
            binding.postBoardTvTitle.text = requireArguments().getString("name")

            rvAdapter = PostBoardAdapter(dataList, requireContext())
            getPostBoard(ApplicationClass.bSharedPreferences.getInt(ApplicationClass.USER_IDX,0))
            binding.postBoardRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            binding.postBoardRv.adapter = rvAdapter
        }else if(requireArguments().getString("name") == "???????????????"){
            binding.postBoardTvTitle.text = requireArguments().getString("name")
            binding.postBoardIvWrite.visibility = View.GONE
            binding.postBoardIvWrite.isClickable = false
            popularAdapter = PopularPostBoardAdapter(popularDataList, requireContext())
            getPopularPostBoard()
            binding.postBoardRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            binding.postBoardRv.adapter = popularAdapter
        }else if(requireArguments().getString("name") == "???????????????"){
            binding.postBoardTvTitle.text = requireArguments().getString("name") + " ?????????"
//            binding.postBoardIvWrite.visibility = View.GONE
//            binding.postBoardIvWrite.isClickable = false
            schoolAdapter = SchoolAdapter(schoolDataList, requireContext())
            getSchoolPost(1)
            binding.postBoardRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            binding.postBoardRv.adapter = schoolAdapter
        }else if(requireArguments().getString("name") == "?????????????????????"){
            binding.postBoardTvTitle.text = requireArguments().getString("name") + " ?????????"
//            binding.postBoardIvWrite.visibility = View.GONE
//            binding.postBoardIvWrite.isClickable = false
            schoolAdapter = SchoolAdapter(schoolDataList, requireContext())
            getSchoolPost(2)
            binding.postBoardRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            binding.postBoardRv.adapter = schoolAdapter
        }else if(requireArguments().getString("name") == "?????????????????????"){
            binding.postBoardTvTitle.text = requireArguments().getString("name") + " ?????????"
//            binding.postBoardIvWrite.visibility = View.GONE
//            binding.postBoardIvWrite.isClickable = false
            schoolAdapter = SchoolAdapter(schoolDataList, requireContext())
            getSchoolPost(3)
            binding.postBoardRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            binding.postBoardRv.adapter = schoolAdapter
        }else if(requireArguments().getString("name") == "???????????????"){
            binding.postBoardTvTitle.text = requireArguments().getString("name") + " ?????????"
//            binding.postBoardIvWrite.visibility = View.GONE
//            binding.postBoardIvWrite.isClickable = false
            schoolAdapter = SchoolAdapter(schoolDataList, requireContext())
            getSchoolPost(4)
            binding.postBoardRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            binding.postBoardRv.adapter = schoolAdapter
        }

        // ????????? ???????????? ?????? ?????? ?????????
        binding.postBoardIvWrite.setOnClickListener {
            if(requireArguments().getString("name") == "???????????????"){
                category_idx = 4
                category_name = "???????????????"
            }else if(requireArguments().getString("name") == "?????????????????????"){
                category_idx = 3
                category_name = "???????????????"
            }else if(requireArguments().getString("name") == "?????????????????????"){
                category_idx = 2
                category_name = "???????????????"
            }else if(requireArguments().getString("name") == "???????????????"){
                category_idx = 1
                category_name = "???????????????"
            }else if(requireArguments().getString("name") == "???????????????"){
                category_idx = 0
                category_name = "???????????????"
            }
            ApplicationClass.bSharedPreferences.edit().putString("board_name",category_name).commit()
            var fragment = PostWriteFragment()
            val bundle = Bundle()
            bundle.putInt("category_idx", category_idx) // ????????? ???????????? idx
            bundle.putString("category_name", category_name) // ????????? ???????????? ??????
            fragment.arguments = bundle
            (context as MainActivity).changeReplaceFragment(fragment)
        }

        binding.postBoardPrev.setOnClickListener {
            // ?????? ???????????? ??????
            (context as MainActivity).changeReplaceFragment(PostFragment())
//            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
//            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun getPostBoard(user_idx: Int){
        //Log.d("postScholarship", "????????? ??????.")
        val getPostBoardInterface = ApplicationClass.sRetrofit.create(PostBoardRetrofitInterface::class.java)
        getPostBoardInterface.getPostBoard(user_idx).enqueue(object :
            Callback<GetPostBoardResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<GetPostBoardResponse>, response: Response<GetPostBoardResponse>) {
                if (response.isSuccessful) {
                    val result = response.body() as GetPostBoardResponse
                    if(result.message == "????????? ?????????????????????."){
                        for(i in 0 until result.result.size){
                            var img = ""
                            if(result.result[i].post_image == null || result.result[i].post_image == ""){
                                img = ""
                            }else{
                                img = result.result[i].post_image.toString()
                            }
                            var name = ""
                            if(result.result[i].post_anonymity == "N"){
                                //name = result.result[i].user_name
                                name = result.result[i].user_name.toString()
                            }else{
                                name = "??????"
                            }
                            category_idx = 0
                            category_name = "???????????????"
                            dataList.add(
                                BoardData(result.result[i].post_idx, result.result[i].user_idx,
                                result.result[i].post_category, result.result[i].post_name,result.result[i].post_content,
                                    img, result.result[i].post_view, result.result[i].post_recommend,
                                result.result[i].post_comment, result.result[i].post_createAt, result.result[i].post_updateAt,
                                    result.result[i].post_status, name,
                                    result.result[i].school_name_idx, result.result[i].post_bookmark,result.result[i].user_name,
                                    result.result[i].user_profileimage_url)
                            )
                        }
                        var newList = arrayListOf<BoardData>()
                        for(i in 0 until dataList.size){
                            newList.add(dataList[dataList.size -1 - i])
                        }
                        for(i in 0 until dataList.size){
                            dataList[i] = newList[i]
                        }
                        rvAdapter.notifyDataSetChanged()
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
            override fun onFailure(call: Call<GetPostBoardResponse>, t: Throwable) {
                Log.d("getProfile", t.message ?: "????????????")
            }
        })
    }

    private fun getPopularPostBoard(){
        //Log.d("postScholarship", "????????? ??????.")
        val getPostBoardInterface = ApplicationClass.sRetrofit.create(PostBoardRetrofitInterface::class.java)
        getPostBoardInterface.getPopularPostBoard().enqueue(object :
            Callback<GetAllPopularPostBoardResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<GetAllPopularPostBoardResponse>, response: Response<GetAllPopularPostBoardResponse>) {
                if (response.isSuccessful) {
                    val result = response.body() as GetAllPopularPostBoardResponse
                    if(result.message == "????????? ?????????????????????."){
                        for(i in 0 until result.result.size){
                            var img = ""
                            if(result.result[i].post_image == null || result.result[i].post_image == ""){
                                img = ""
                            }else{
                                img = result.result[i].post_image.toString()
                            }
                            var name = ""
                            if(result.result[i].post_anonymity == "N"){
                                //name = result.result[i].user_name
                                name = result.result[i].user_name.toString()
                            }else{
                                name = "??????"
                            }
                            popularDataList.add(PopularPostBoardData(result.result[i].popular_idx,
                                result.result[i].post_idx, result.result[i].user_idx,
                                result.result[i].school_name_idx, result.result[i].post_content,
                                result.result[i].post_createAt,
                                result.result[i].post_updateAt, result.result[i].postp_status,
                                result.result[i].count, result.result[i].user_name,
                                result.result[i].post_category, result.result[i].post_anonymity,
                                result.result[i].user_profileimage_url, result.result[i].post_image,
                                result.result[i].post_view, result.result[i].post_recommend,
                                result.result[i].post_name, result.result[i].post_comment)
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
            override fun onFailure(call: Call<GetAllPopularPostBoardResponse>, t: Throwable) {
                Log.d("getProfile", t.message ?: "????????????")
            }
        })

    }
    private fun getSchoolPost(schoolNameIdx: Int){
        //Log.d("postScholarship", "????????? ??????.")
        val getSchoolPostInterface = ApplicationClass.sRetrofit.create(PostBoardRetrofitInterface::class.java)
        getSchoolPostInterface.getSchoolAllPost(schoolNameIdx).enqueue(object :
            Callback<GetAllSchoolPostResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<GetAllSchoolPostResponse>, response: Response<GetAllSchoolPostResponse>) {
                if (response.isSuccessful) {
                    val result = response.body() as GetAllSchoolPostResponse
                    if(result.message == "????????? ?????????????????????."){
                        for(i in 0 until result.result.size){
                            var img = ""
                            if(result.result[i].post_image == null || result.result[i].post_image == ""){
                                img = ""
                            }else{
                                img = result.result[i].post_image.toString()
                            }
                            var name = ""
                            if(result.result[i].post_anonymity == "N"){
                                //name = result.result[i].user_name
                                name = result.result[i].user_name.toString()
                            }else{
                                name = "??????"
                            }
                            category_idx = schoolNameIdx
                            category_name = "???????????????"
                            schoolDataList.add(
                                SchoolData(result.result[i].post_idx, result.result[i].user_idx,
                                    name, result.result[i].user_profileimage_url,
                                    result.result[i].post_name,
                                    result.result[i].post_content, img,
                                    result.result[i].post_view, result.result[i].post_recommend,
                                    result.result[i].post_comment, result.result[i].post_anonymity,
                                    result.result[i].post_category, result.result[i].post_school_name,
                                    result.result[i].post_createAt, result.result[i].recommend_check)
                            )
                        }
                        var newList = arrayListOf<SchoolData>()
                        for(i in 0 until schoolDataList.size){
                            newList.add(schoolDataList[schoolDataList.size -1 - i])
                        }
                        for(i in 0 until schoolDataList.size){
                            schoolDataList[i] = newList[i]
                        }
                        schoolAdapter.notifyDataSetChanged()
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
            override fun onFailure(call: Call<GetAllSchoolPostResponse>, t: Throwable) {
                Log.d("getProfile", t.message ?: "????????????")
            }
        })
    }

    override fun onResume() {
        super.onResume()
        //getPostBoard(ApplicationClass.bSharedPreferences.getInt(ApplicationClass.USER_IDX,0))
    }
}
