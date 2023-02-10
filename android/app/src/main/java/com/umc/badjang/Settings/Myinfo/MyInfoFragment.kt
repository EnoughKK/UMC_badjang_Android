package com.umc.badjang.Settings.Myinfo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.umc.badjang.ApplicationClass
import com.umc.badjang.MainActivity
import com.umc.badjang.Settings.Myinfo.models.MyinfoRequest
import com.umc.badjang.Settings.Myinfo.models.MyinfoResponse
import com.umc.badjang.Settings.UserResponse
import com.umc.badjang.databinding.FragmentMyinfoBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MyInfoFragment :Fragment() {

    private var _binding:FragmentMyinfoBinding?=null
    private val binding get() = _binding!!

    val jwt = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN, null)

    //user_idx 불러옴
    val useridx = ApplicationClass.bSharedPreferences.getInt(ApplicationClass.USER_IDX, 0)

    //api 통신을 위한 retrofit
    private var retrofit : Retrofit? =null

    // 프래그먼트 전환을 위해
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
        _binding=FragmentMyinfoBinding.inflate(inflater,container,false)
        return binding.root


        val myinfoRetrofit = ApplicationClass.sRetrofit.create(MyinfoRetrofit::class.java)

        myinfoRetrofit.requestUserinfo(jwt.toString()).enqueue(object : Callback<UserResponse> {

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.d("사용자 서버 실패", "${t.localizedMessage}")
            }

            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                Log.d("사용자 서버", response.body().toString())
                val received = response.body()
                when(response.body()!!.code) {
                    1000->{
                        binding.MyInfoName.text="${received?.result?.user_name}"
                        binding.MyInfoPhone.text="${received?.result?.user_phone}"
                        binding.MyInfoBirthDate.text="${received?.result?.user_birth}"
                        binding.MyInfoEmail.text="${received?.result?.user_email}"

                    }
                    else->{
                        Log.e("사용자 조회 실패","${response.message()}")
                    }
                }


            }
        })
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 이전 버튼 선택 시
        binding.MyInfoUpBtn.setOnClickListener {
            // 이전 페이지로 이동
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
            requireActivity().supportFragmentManager.popBackStack()
        }

        val test_n =arguments?.getString("changename")
        val test_p = arguments?.getString("changephone")

        //이름 변경
        binding.MyInfoNameBtn.setOnClickListener {
            MyinfoNameDialog(requireContext()).show()
            val intent = Intent(context,MyInfoFragment::class.java)
                intent.putExtra("name",binding.MyInfoName.text.toString())
            binding.MyInfoName.text="${test_n}"
        }

        //전번 변경
        binding.MyInfoPhoneBtn.setOnClickListener {
            MyinfoPhoneDialog(requireContext()).show()
            if (test_p !=null)
            {
                binding.MyInfoPhone.text=test_p
            }
        }

        //전화번호 변경
        binding.MyInfoPhoneBtn.setOnClickListener {
            MyinfoPhoneDialog(requireContext()).show()
        }

        //서비스 이용약관 클릭시
        binding.MyInfoTermofuseBtn.setOnClickListener{
            activity?.changeFragment(TermofUseFragment())
        }

        //개인정보 이용약관 클릭시

        binding.MyInfoPrivacyBtn.setOnClickListener{
            activity?.changeFragment(PrivacyFragment())
        }
    }


}