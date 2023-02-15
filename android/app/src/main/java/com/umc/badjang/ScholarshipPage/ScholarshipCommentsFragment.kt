package com.umc.badjang.ScholarshipPage

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.badjang.ApplicationClass
import com.umc.badjang.MainActivity
import com.umc.badjang.R
import com.umc.badjang.Retrofit.RetrofitManager
import com.umc.badjang.ScholarshipPage.Model.*
import com.umc.badjang.databinding.FragmentScholarshipCommentsBinding
import com.umc.badjang.mConnectUserId
import com.umc.badjang.utils.RESPONSE_STATE


class ScholarshipCommentsFragment: Fragment(), CommentsDialogInterface, EditCommentsInterface {
    private lateinit var viewBinding: FragmentScholarshipCommentsBinding

    var activity: MainActivity? = null

    // 현재 로그인 된 사용자 jwt
    private var jwt: String? = null

    // 댓글 삭제 dto
    private lateinit var deleteCommentsPostData: DeleteCommentsDTO

    // 댓글 수정 dto
    private lateinit var editCommentsPostData: EditCommentsDTO

    private var curCommentsIdx: Long? = null
    private var commentsDatas = ArrayList<ScholarshipCommentsDTO>()
    private var curScholarshipIdx: Int? = null
    private var curUserIdx: Int? = null
    private var curComments: String? = null
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
        savedInstanceState: Bundle?,
    ): View? {
        viewBinding = FragmentScholarshipCommentsBinding.inflate(layoutInflater);

        commentsDatas.clear()

        // 현재 로그인 된 사용자 jwt 조회
        jwt = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN, null)

        // 현제 접속된 유저 idx
        mConnectUserId = ApplicationClass.bSharedPreferences.getInt(ApplicationClass.USER_IDX, 0)

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // 장학금 index값, 이름 가져오기
        val scholarshipIdx: Int = arguments?.getLong("ItemIdx")!!.toInt()
        val scholarshipName = arguments?.getString("ScholarshipName")

        viewBinding.scholarshipTitle.text = scholarshipName

        loadData(scholarshipIdx)


        // 이전 페이지로 이동
        viewBinding.btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
            requireActivity().supportFragmentManager.popBackStack()
        }

        // 댓글 생성
        viewBinding.inputComments.setOnClickListener {

            if(viewBinding.comments.text.toString() != "") {

                val commentsPost = NewCommentsDTO(scholarshipIdx, mConnectUserId, viewBinding.comments.text.toString())
                inputComments(commentsPost)
                viewBinding.comments.text.clear()
            }
            loadData(scholarshipIdx)
            initRecycler()
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

                    initRecycler()
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

                curUserIdx = commentsDatas[position].user_idx
                curScholarshipIdx = commentsDatas[position].scholarship_idx
                curCommentsIdx = commentsDatas[position].scholarship_comment_idx!!.toLong()
                curComments = commentsDatas[position].scholarship_comment_content
                deleteCommentsPostData = DeleteCommentsDTO(commentsDatas[position].scholarship_comment_idx, curScholarshipIdx, mConnectUserId)

                if (curUserIdx == mConnectUserId) {
                    Log.d("로그", "onItemLongClick: ${mConnectUserId}")

                    // 수정/삭제 다이얼로그 창 띄우기
                    onDialogBtnClicked(view)
                }
                else {

                    // 신고하기 다이얼로그
                    val myCustomDialog = ReportDialog(requireContext())
                    myCustomDialog.show()
                }

            }
        })
    }

    // 내 댓글 클릭시 수정/삭제 다이얼로그 창 띄우기
    private fun onDialogBtnClicked(view: View) {
        val myCustomDialog = CommentsDialog(requireContext(), this)
        myCustomDialog.show()
    }

    // 수정하기 클릭
    override fun editBtnClicked() {
        val myCustomDialog = EditCommentsDialog(requireContext(), this, curComments!!, jwt!!, curCommentsIdx!!, curUserIdx!!, curScholarshipIdx!!)
        myCustomDialog.show()
    }

    // 삭제하기 클릭
    override fun deleteClicked() {
        DeleteComments(curCommentsIdx!!, deleteCommentsPostData)
        Log.d("삭제", "DeleteComments: ${mConnectUserId}")
        loadData(commentsAdapter.datas[0].scholarship_idx!!)
        initRecycler()
    }

    // 댓글 수정 버튼 클릭시
    override  fun editConfirmBtnClicked() {
        loadData(commentsAdapter.datas[0].scholarship_idx!!)
        initRecycler()
    }


    // 댓글 생성 (api)
    private fun inputComments(body: NewCommentsDTO) {
        RetrofitManager.instance.newComments(jwt!!, body)
    }

    // 댓글 삭제 (api)
    private fun DeleteComments(commentsIdx: Long, body: DeleteCommentsDTO) {
        RetrofitManager.instance.deleteComments(jwt!!, commentsIdx, body)
    }
}