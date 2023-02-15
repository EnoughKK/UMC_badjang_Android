package com.umc.badjang.PostPage.Detail

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.umc.badjang.ApplicationClass
import com.umc.badjang.ApplicationClass.Companion.bSharedPreferences
import com.umc.badjang.MainActivity
import com.umc.badjang.PostPage.AllPostAdapter
import com.umc.badjang.PostPage.AllPostData
import com.umc.badjang.PostPage.Board.*
import com.umc.badjang.PostPage.Board.Model.GetSchoolPostBoardResponse
import com.umc.badjang.PostPage.Detail.Model.*
import com.umc.badjang.PostPage.SchoolPostData
import com.umc.badjang.R
import com.umc.badjang.Settings.NameChangeDialog
import com.umc.badjang.databinding.FragmentDetailPostBinding
import com.umc.badjang.databinding.FragmentPostBoardBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

data class CommentData(
    val comment_idx : Int,
    val user_idx : Int,
    val post_idx : Int,
    val comment_content : String,
    val comment_recommend : Int,
    val comment_anonymity : String,
    val comment_createAt : String,
    val comment_updatedAt : String,
    val comment_status : String,
    val user_name : String?,
    val user_profileimage_url : String,
    val recommend_status : Int
)

class DetailPostFragment : Fragment() {
    private lateinit var binding: FragmentDetailPostBinding // viewBinding

    private lateinit var rvAdapter : DetailPostCommentsAdapter
    private var dataList = arrayListOf<CommentData>()

    var activity: MainActivity? = null
    var post_idx = 0
    var board_name = ""
    var comment_idx = 0
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
        binding = FragmentDetailPostBinding.inflate(layoutInflater);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //이전 화면 Adapter 에서 게시글 idx 받아오는 코드
        post_idx = requireArguments().getInt("post_idx")
        board_name = requireArguments().getString("board_name").toString()
        binding.detailPostTvTitle.text = board_name
        //학교게시판
        if(board_name.contains("대학교")){
            var idx = 0
            if(board_name.contains("부경대학교")){
                idx = 1
            }else if(board_name.contains("경상국립대학교")){
                idx = 2
            }else if(board_name.contains("한국해양대학교")){
                idx = 3
            }else if(board_name.contains("부산대학교")){
                idx = 4
            }
            getSchoolOnePost(idx,post_idx)
            rvAdapter = DetailPostCommentsAdapter(dataList, requireContext())
            getComments(post_idx, bSharedPreferences.getInt(ApplicationClass.USER_IDX, 0))
            binding.detailPostRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            binding.detailPostRv.adapter = rvAdapter
        }
        else{ //자유게시판
            getOnePost(bSharedPreferences.getInt(ApplicationClass.USER_IDX, 0), post_idx)
            rvAdapter = DetailPostCommentsAdapter(dataList, requireContext())
            getComments(post_idx, bSharedPreferences.getInt(ApplicationClass.USER_IDX, 0))
            binding.detailPostRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            binding.detailPostRv.adapter = rvAdapter
        }

        binding.detailPostTvOther.setOnClickListener {
            if(binding.detailPostTvOther.text == "삭제하기"){
                var dialog = DeleteDialog(requireContext())
                bSharedPreferences.edit().putInt("post_idx",post_idx).commit()
                //bSharedPreferences.edit().putString("comment_idx","").commit()
                dialog.show()
                dialog.setOnDismissListener {
                    if(bSharedPreferences.getString("delete_content",null) == "삭제"){
                        requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
                        requireActivity().supportFragmentManager.popBackStack()
                    }
                }
            }else{
                var dialog = ReportDialog(requireContext())
                //ApplicationClass.bSharedPreferences.edit().putInt("post_idx",post_idx).commit()
                dialog.show()
                dialog.setOnDismissListener {

                }
            }
        }
        binding.detailPostIvSendComment.setOnClickListener {
            if(binding.detailPostEtComment.length() != 0){
                var checked = ""
                if(binding.detailPostCheckboxAnonymous.isChecked){
                    checked = "Y"
                }else{
                    checked = "N"
                }
                postComment(post_idx = post_idx,
                    user_idx = bSharedPreferences.getInt(ApplicationClass.USER_IDX, 0),
                    PostCommentReq(post_idx = post_idx, user_idx = bSharedPreferences.getInt(ApplicationClass.USER_IDX, 0),
                        comment_content = binding.detailPostEtComment.text.toString(),
                        comment_anonymity = checked, comment_status = "Y")
                )

            }
        }
        binding.detailPostPrev.setOnClickListener {
            // 이전 페이지로 이동
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
            requireActivity().supportFragmentManager.popBackStack()
        }

    }
    private fun getComments(post_idx: Int, user_idx: Int){
        //Log.d("postScholarship", "호출은 된다.")
        val getCommentsInterface = ApplicationClass.sRetrofit.create(DetailPostRetrofitInterface::class.java)
        getCommentsInterface.getComments(post_idx, user_idx).enqueue(object :
            Callback<GetCommentsResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<GetCommentsResponse>, response: Response<GetCommentsResponse>) {
                if (response.isSuccessful) {
                    val result = response.body() as GetCommentsResponse
                    if(result.message == "요청에 성공하였습니다."){
                        dataList = arrayListOf()
                        for(i in 0 until result.result.size){
                            dataList.add(
                                CommentData(result.result[i].comment_idx, result.result[i].user_idx,
                                    result.result[i].post_idx, result.result[i].comment_content,
                                    result.result[i].comment_recommend, result.result[i].comment_anonymity,
                                    result.result[i].comment_createAt, result.result[i].comment_updatedAt,
                                    result.result[i].comment_status, result.result[i].user_name,
                                    result.result[i].user_profileimage_url,result.result[i].recommend_status)
                            )
                        }
                        rvAdapter = DetailPostCommentsAdapter(dataList, requireContext())
                        binding.detailPostRv.adapter = rvAdapter
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
            override fun onFailure(call: Call<GetCommentsResponse>, t: Throwable) {
                Log.d("getProfile", t.message ?: "통신오류")
            }
        })

    }
    private fun getOnePost(user_idx: Int, post_idx: Int){
        //Log.d("postScholarship", "호출은 된다.")
        val getOnePostInterface = ApplicationClass.sRetrofit.create(DetailPostRetrofitInterface::class.java)
        getOnePostInterface.getOnePost(user_idx, post_idx).enqueue(object :
            Callback<GetOnePostResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<GetOnePostResponse>, response: Response<GetOnePostResponse>) {
                if (response.isSuccessful) {
                    val result = response.body() as GetOnePostResponse
                    if(result.message == "요청에 성공하였습니다."){
                        for(i in 0 until result.result.size){
                            if(result.result[0].user_idx == user_idx){
                                binding.detailPostTvOther.text = "삭제하기"
                            }else{
                                binding.detailPostTvOther.text = "신고하기"
                            }
                            Glide.with(requireActivity()).load(result.result[0].user_profileimage_url).into(binding.detailPostProfileImg)
                            if(result.result[0].post_anonymity == "Y"){
                                binding.detailPostProfileNickname.text = "익명"
                            }else{
                                binding.detailPostProfileNickname.text = result.result[0].user_name
                            }
                            binding.detailPostProfileDate.text = result.result[0].post_createAt.substring(0,4)+"."+result.result[0].post_createAt.substring(5,7)+"."+result.result[0].post_createAt.substring(8,10)
                            binding.detailPostContentTitle.text = result.result[0].post_name
                            binding.detailPostContentText.text = result.result[0].post_content
                            if(result.result[0].recommend_status == 1){
                                binding.detailPostGoodIcon.setImageResource(R.drawable.ic_good_count_blue)
                            }else{
                                binding.detailPostGoodIcon.setImageResource(R.drawable.ic_recommend_stroke)
                            }
                            if(result.result[0].post_image == "" || result.result[0].post_image == null){
                                binding.detailPostContentImg.visibility = View.GONE
                            }else{
                                Glide.with(requireActivity()).load(result.result[0].post_image).into(binding.detailPostContentImg)
                            }
                            binding.detailPostViewNum.text = result.result[0].post_view.toString()
                            binding.detailPostGoodNum.text = result.result[0].post_recommend.toString()
                            binding.detailPostCommentsNum.text = result.result[0].post_comment.toString()
                            binding.detailPostBookmarkNum.text = result.result[0].bookmark_count.toString()
                            if(result.result[0].post_bookmark == 1){
                                binding.detailPostBookmarkIcon.setImageResource(R.drawable.ic_star_checked)
                            }else{
                                binding.detailPostBookmarkIcon.setImageResource(R.drawable.ic_star_unchecked)
                            }
                        }
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
            override fun onFailure(call: Call<GetOnePostResponse>, t: Throwable) {
                Log.d("getProfile", t.message ?: "통신오류")
            }
        })

    }
    private fun postComment(post_idx: Int, user_idx: Int, postCommentReq: PostCommentReq){
        //Log.d("postScholarship", "호출은 된다.")
        val getCommentsInterface = ApplicationClass.sRetrofit.create(DetailPostRetrofitInterface::class.java)
        getCommentsInterface.postComment(post_idx, user_idx, postCommentReq).enqueue(object :
            Callback<PostCommentResponse> {
            @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
            override fun onResponse(call: Call<PostCommentResponse>, response: Response<PostCommentResponse>) {
                if (response.isSuccessful) {
                    val result = response.body() as PostCommentResponse
                    if(result.message == "요청에 성공하였습니다."){
                        dataList = arrayListOf()
                        binding.detailPostEtComment.text = null
                        binding.detailPostCheckboxAnonymous.isChecked = false
                        getComments(post_idx, user_idx)
                        rvAdapter.notifyDataSetChanged()
                        getOnePost(bSharedPreferences.getInt(ApplicationClass.USER_IDX, 0), post_idx)
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
            override fun onFailure(call: Call<PostCommentResponse>, t: Throwable) {
                Log.d("getProfile", t.message ?: "통신오류")
            }
        })

    }
    private fun getSchoolOnePost(schoolNameIdx: Int, postIdx: Int){
        //Log.d("postScholarship", "호출은 된다.")
        val getOnePostInterface = ApplicationClass.sRetrofit.create(DetailPostRetrofitInterface::class.java)
        getOnePostInterface.getSchoolOnePost(schoolNameIdx, postIdx).enqueue(object :
            Callback<GetSchoolOnePostResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<GetSchoolOnePostResponse>, response: Response<GetSchoolOnePostResponse>) {
                if (response.isSuccessful) {
                    val result = response.body() as GetSchoolOnePostResponse
                    if(result.message == "요청에 성공하였습니다."){
                        if(result.result.getOneOfSchoolBoardRes[0].user_idx == bSharedPreferences.getInt(ApplicationClass.USER_IDX, 0)){
                            binding.detailPostTvOther.text = "삭제하기"
                        }else{
                            binding.detailPostTvOther.text = "신고하기"
                        }
                        Glide.with(requireActivity()).load(result.result.getOneOfSchoolBoardRes[0].user_profileimage_url).into(binding.detailPostProfileImg)
                        if(result.result.getOneOfSchoolBoardRes[0].post_anonymity == "N"){
                            binding.detailPostProfileNickname.text = "익명"
                        }else{
                            binding.detailPostProfileNickname.text = result.result.getOneOfSchoolBoardRes[0].user_name
                        }
                        binding.detailPostProfileDate.text = result.result.getOneOfSchoolBoardRes[0].post_createAt.substring(0,4)+"."+result.result.getOneOfSchoolBoardRes[0].post_createAt.substring(5,7)+"."+result.result.getOneOfSchoolBoardRes[0].post_createAt.substring(8,10)
                        binding.detailPostContentTitle.text = result.result.getOneOfSchoolBoardRes[0].post_name
                        binding.detailPostContentText.text = result.result.getOneOfSchoolBoardRes[0].post_content
                        if(result.result.getOneOfSchoolBoardRes[0].recommend_check == 1){
                            binding.detailPostGoodIcon.setImageResource(R.drawable.ic_good_count_blue)
                        }else{
                            binding.detailPostGoodIcon.setImageResource(R.drawable.ic_recommend_stroke)
                        }
                        if(result.result.getOneOfSchoolBoardRes[0].post_image == "" || result.result.getOneOfSchoolBoardRes[0].post_image == null){
                            binding.detailPostContentImg.visibility = View.GONE
                        }else{
                            Glide.with(requireActivity()).load(result.result.getOneOfSchoolBoardRes[0].post_image).into(binding.detailPostContentImg)
                        }
                        binding.detailPostViewNum.text = result.result.getOneOfSchoolBoardRes[0].post_view.toString()
                        binding.detailPostGoodNum.text = result.result.getOneOfSchoolBoardRes[0].post_recommend.toString()
                        binding.detailPostCommentsNum.text = result.result.getOneOfSchoolBoardRes[0].post_comment.toString()
                        //binding.detailPostBookmarkNum.text = result.post[0].bookmark_count.toString()
                        if(result.result.getOneOfSchoolBoardRes[0].bookmark_check == 1){
                            binding.detailPostBookmarkIcon.setImageResource(R.drawable.ic_star_checked)
                        }else{
                            binding.detailPostBookmarkIcon.setImageResource(R.drawable.ic_star_unchecked)
                        }
                        for(i in 0 until result.result.getSchoolBoardCommentRes.size){
                            dataList.add(
                                CommentData(result.result.getSchoolBoardCommentRes[i].comment_idx, result.result.getSchoolBoardCommentRes[i].user_idx,
                                    result.result.getSchoolBoardCommentRes[i].post_idx, result.result.getSchoolBoardCommentRes[i].comment_content,
                                    result.result.getSchoolBoardCommentRes[i].comment_recommend, result.result.getSchoolBoardCommentRes[i].comment_anonymity,
                                    result.result.getSchoolBoardCommentRes[i].comment_createAt, "",
                                    "", result.result.getSchoolBoardCommentRes[i].user_name,
                                    result.result.getSchoolBoardCommentRes[i].user_profileimage_url,result.result.getSchoolBoardCommentRes[i].recommend_check)
                            )
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
            override fun onFailure(call: Call<GetSchoolOnePostResponse>, t: Throwable) {
                Log.d("getProfile", t.message ?: "통신오류")
            }
        })

    }

    override fun onResume() {
        super.onResume()
        getOnePost(bSharedPreferences.getInt(ApplicationClass.USER_IDX, 0), post_idx)
    }
}