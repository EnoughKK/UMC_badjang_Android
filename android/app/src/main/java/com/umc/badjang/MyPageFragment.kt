package com.umc.badjang

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.bumptech.glide.Glide
import com.umc.badjang.Bookmarks.BookmarksFragment
import com.umc.badjang.HomePage.UniversityFilterDialog
import com.umc.badjang.MyPage.ChangeProfileActivity
import com.umc.badjang.MyPage.Model.MyProfileRes
import com.umc.badjang.MyPage.MyProfileRetrofitInterface
import com.umc.badjang.MyPage.MyWrite.MyWriteFragment
import com.umc.badjang.MyPage.Noti.NotiFragment
import com.umc.badjang.Settings.SettingsFragment
import com.umc.badjang.databinding.FragmentMyPageBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class MyPageFragment : Fragment() {
    private lateinit var viewBinding: FragmentMyPageBinding // viewBinding

    //api 통신을 위한 retrofit
    private var retrofit :Retrofit? =null

    // 프래그먼트 전환을 위해
    var activity: MainActivity? = null
    var idx : Int = 0
    var email : String = ""
    var name : String = ""
    var profileimage_url : String? = ""
    var type : String = ""
    var birth : String = ""
    var phone : String = ""
    var push_yn : String = ""
    var on_off : String = ""
    var univ : String? = ""
    var college : String? = ""
    var department : String? = ""
    var grade : String? = ""
    var semester : String? = ""
    var province : String? = ""
    var city : String? = ""

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
        viewBinding = FragmentMyPageBinding.inflate(layoutInflater);
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val jwt: String? = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN,null)
        println("jwt : $jwt")
        getMyProfile()
        viewBinding.MypageLlSetting.setOnClickListener{
            activity?.changeFragment(SettingsFragment())

        }

        viewBinding.MypageLlProfile.setOnClickListener {
//            var fragment = ChangeProfileFragment()
//            val bundle = Bundle()
//            bundle.putString("name", name)
//            fragment.arguments = bundle
            val intent = Intent(context,ChangeProfileActivity::class.java)
            intent.putExtra("name",name)
            startActivity(intent)
        }

        viewBinding.mypageCvBookmark.setOnClickListener {
            activity?.changeFragment(BookmarksFragment())
        }
        viewBinding.mypageCvMyWrite.setOnClickListener {
            activity?.changeFragment(MyWriteFragment())
        }
        viewBinding.mypageCvSchoolLocal.setOnClickListener {
            UniversityFilterDialog(requireContext(), requireActivity()).show()
        }

        viewBinding.MypageLlViewed.setOnClickListener {

        }
        viewBinding.MypageLlAnnouncement.setOnClickListener {
            activity?.changeFragment(NotiFragment())
        }
        viewBinding.MypageLlInquiry.setOnClickListener {

        }
        viewBinding.MypageLlQna.setOnClickListener {

        }
        // 이전 버튼 선택 시
        viewBinding.MypageUpBtn.setOnClickListener {
            // 이전 페이지로 이동
            //requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
            //requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun getMyProfile(){
        //Log.d("postScholarship", "호출은 된다.")
        val scholarshipInterface = ApplicationClass.sRetrofit.create(MyProfileRetrofitInterface::class.java)
        scholarshipInterface.getMyProfileRes().enqueue(object :
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
                        viewBinding.MypageTvNickname.text = name
                        viewBinding.MypageTvUniv.text = univ
                        if(profileimage_url != null){
                            Glide.with(requireActivity()).load(profileimage_url).into(viewBinding.MyPageIvProfileimg)
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