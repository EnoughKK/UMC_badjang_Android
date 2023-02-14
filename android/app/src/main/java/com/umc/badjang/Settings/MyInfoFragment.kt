package com.umc.badjang.Settings

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer
import com.bumptech.glide.Glide
import com.umc.badjang.ApplicationClass
import com.umc.badjang.LoginPage.SignUp.PrivacyActivity
import com.umc.badjang.LoginPage.SignUp.TermofUseActivity
import com.umc.badjang.MainActivity
import com.umc.badjang.MyPage.Model.MyProfileRes
import com.umc.badjang.MyPage.Model.PostProfileModifyReq
import com.umc.badjang.MyPage.Model.PostUserInfoModify
import com.umc.badjang.MyPage.MyProfileRetrofitInterface
import com.umc.badjang.Settings.Logout.LogoutDialog
import com.umc.badjang.Settings.model.PostNameModifyReq
import com.umc.badjang.Settings.model.PostNameModifyResponse
import com.umc.badjang.databinding.FragmentMyinfoBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyInfoFragment :Fragment() {
    private lateinit var binding : FragmentMyinfoBinding

    // 프래그먼트 전환을 위해
    var activity: MainActivity? = null

    private var idx : Int = 0
    private var email : String = ""
    private var name : String = ""
    private var profileimage_url : String? = ""
    private var type : String = ""
    private var birth : String = ""
    private var phone : String = ""
    private var push_yn : String? = ""
    private var on_off : String = ""
    private var univ : String? = ""
    private var college : String? = ""
    private var department : String? = ""
    private var grade : String? = ""
    private var semester : String? = ""
    private var province : String? = ""
    private var city : String? = ""
    private val bookmark_yn : String = ""
    private val new_post_yn : String = ""
    private val inq_answer_yn : String = ""
    private val comment_yn : String = ""

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = getActivity() as MainActivity
    }

    override fun onDetach() {
        super.onDetach()
        activity = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMyinfoBinding.inflate(layoutInflater)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.myInfoBtnPrev.setOnClickListener {
            // 이전 페이지로 이동
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.myInfoBtnName.setOnClickListener {
            ApplicationClass.sSharedPreferences.edit().putString("name", binding.myInfoTvNameContent.text.toString()).commit()
            var dialog = NameChangeDialog(requireContext())
            dialog.show()
            dialog.setOnDismissListener {
                getMyProfile()
            }
        }
        binding.myInfoBtnPhoneNumber.setOnClickListener {
            ApplicationClass.sSharedPreferences.edit().putString("phone", phone).commit()
            var dialog = PhoneChangeDialog(requireContext())
            dialog.show()
            dialog.setOnDismissListener {
                getMyProfile()
            }
        }

        binding.myInfoLlPrivate.setOnClickListener {
            activity?.changeFragment(PrivacyFragment())
        }
        binding.myInfoBtnPrivate.setOnClickListener {
            activity?.changeFragment(PrivacyFragment())
        }
        binding.myInfoLlTermsOfUse.setOnClickListener {
            activity?.changeFragment(TermofUseFragment())
        }
        binding.myInfoBtnTermsOfUse.setOnClickListener {
            activity?.changeFragment(TermofUseFragment())
        }
        binding.myInfoLlWithdrawal.setOnClickListener {

        }
    }
    private fun getMyProfile(){
        //Log.d("postScholarship", "호출은 된다.")
        val getMyProfileInterface = ApplicationClass.sRetrofit.create(MyProfileRetrofitInterface::class.java)
        getMyProfileInterface.getMyProfileRes().enqueue(object :
            Callback<MyProfileRes> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<MyProfileRes>, response: Response<MyProfileRes>) {
                if (response.isSuccessful) {
                    val result = response.body() as MyProfileRes
                    if(result.message == "요청에 성공하였습니다."){
                        idx = result.result.user_idx
                        email = result.result.user_email
                        name = result.result.user_name
                        if(result.result.user_profileimage_url == null){
                            profileimage_url = ""
                        }else{
                            profileimage_url = result.result.user_profileimage_url
                        }
                        type = result.result.user_type
                        birth = result.result.user_birth
                        phone = result.result.user_phone
                        push_yn = result.result.user_push_yn
                        on_off = result.result.user_on_off
                        if(result.result.user_univ == null){
                            univ = "학교를 입력해주세요."
                        }else{
                            univ = result.result.user_univ
                        }
                        if(result.result.user_college == null){
                            college = ""
                        }else{
                            college = result.result.user_college
                        }
                        if(result.result.user_department == null){
                            department = ""
                        }else{
                            department = result.result.user_department
                        }
                        if(result.result.user_grade == null){
                            grade = ""
                        }else{
                            grade = result.result.user_grade
                        }
                        if(result.result.user_semester == null){
                            semester = ""
                        }else{
                            semester = result.result.user_semester
                        }
                        if(result.result.user_province == null){
                            province = ""
                        }else{
                            province = result.result.user_province
                        }
                        if(result.result.user_city == null){
                            city = ""
                        }else{
                            city = result.result.user_city
                        }
                        binding.myInfoTvNameContent.text = name
                        binding.myInfoTvBirthContent.text = birth.substring(0,4) + "/" + birth.substring(4,6) + "/" + birth.substring(6,8)
                        binding.myInfoTvPhoneNumberContent.text = phone.substring(0,3) + "-" + phone.substring(3,7) + "-" + phone.substring(7,11)
                        binding.myInfoTvEmailContent.text = email
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
            override fun onFailure(call: Call<MyProfileRes>, t: Throwable) {
                Log.d("getProfile", t.message ?: "통신오류")
            }
        })
    }

    override fun onResume() {
        super.onResume()
        getMyProfile()
    }
}