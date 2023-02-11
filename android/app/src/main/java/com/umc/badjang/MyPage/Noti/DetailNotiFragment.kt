package com.umc.badjang.MyPage.Noti

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.umc.badjang.ApplicationClass
import com.umc.badjang.MainActivity
import com.umc.badjang.MyPage.Noti.model.GetDetailNotiResponse
import com.umc.badjang.MyPage.Noti.model.GetNotiResponse
import com.umc.badjang.databinding.FragmentMyWriteBinding
import com.umc.badjang.databinding.FragmentNotiDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

data class DetailNotiData(
    val notice_idx : Int,
    val notice_title : String,
    val notice_content : String,
    val notice_image : String?,
    val notice_createAt : String,
    val notice_updateAt : String?
)

class DetailNotiFragment : Fragment() {
    private lateinit var binding: FragmentNotiDetailBinding
    private var dataList = arrayListOf<DetailNotiData>()
    var activity: MainActivity? = null
    var idx = 0
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
        binding = FragmentNotiDetailBinding.inflate(layoutInflater);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        idx = requireArguments().getInt("idx",0)
        getDetailNoti(idx)
        binding.notiDetailIvPrev.setOnClickListener {
            // 이전 페이지로 이동
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
    private fun getDetailNoti(idx : Int){
        //Log.d("postScholarship", "호출은 된다.")
        val detailNotiInterface = ApplicationClass.sRetrofit.create(NotiInterface::class.java)
        detailNotiInterface.getDetailNoti(idx).enqueue(object :
            Callback<GetDetailNotiResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<GetDetailNotiResponse>, response: Response<GetDetailNotiResponse>) {
                if (response.isSuccessful) {
                    val result = response.body() as GetDetailNotiResponse
                    if(result.message == "요청에 성공하였습니다."){
                        var img = ""
                        if(result.result.notice_image == null){
                            binding.notiDetailIv.visibility = View.GONE
                        }else{
                            Glide.with(requireActivity()).load(result.result.notice_image).into(binding.notiDetailIv)
                        }
                        binding.notiDetailTvTitle.text = result.result.notice_title
                        binding.notiDetailTvContent.text = result.result.notice_content


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
            override fun onFailure(call: Call<GetDetailNotiResponse>, t: Throwable) {
                Log.d("getProfile", t.message ?: "통신오류")
            }
        })

    }
}