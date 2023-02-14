package com.umc.badjang.MyPage.FQA

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
import com.umc.badjang.MyPage.FQA.Model.FQADetailRes
import com.umc.badjang.MyPage.Noti.model.GetDetailNotiResponse
import com.umc.badjang.MyPage.Noti.model.GetNotiResponse
import com.umc.badjang.databinding.FragmentFqaDetailBinding
import com.umc.badjang.databinding.FragmentMyWriteBinding
import com.umc.badjang.databinding.FragmentNotiDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

data class DetailFQAData(
    val faq_content : String,
    val faq_title : String,
    val faq_createAt : String,
    val faq_status : String,
    val faq_updateAt : String,
    val faq_image : String?,
    val faq_idx : Long
)

class DetailFQAFragment : Fragment() {
    private lateinit var binding: FragmentFqaDetailBinding

    var activity: MainActivity? = null
    var idx:Long = 0
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
        binding = FragmentFqaDetailBinding.inflate(layoutInflater);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        idx = requireArguments().getLong("idx",0)
        getDetailFQA(idx)
        binding.fqaDetailIvPrev.setOnClickListener {
            // 이전 페이지로 이동
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
    private fun getDetailFQA(idx : Long){
        //Log.d("postScholarship", "호출은 된다.")
        val detailFQAInterface = ApplicationClass.sRetrofit.create(FQARetrofitInterface::class.java)
        detailFQAInterface.getDetailFQA(idx).enqueue(object :
            Callback<FQADetailRes> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<FQADetailRes>, response: Response<FQADetailRes>) {
                if (response.isSuccessful) {
                    val result = response.body() as FQADetailRes
                    if(result.message == "요청에 성공하였습니다."){
                        var img = ""
                        if(result.result.faq_image == null || result.result.faq_image == ""){
                            binding.fqaDetailIv.visibility = View.GONE
                        }else{
                            Glide.with(requireActivity()).load(result.result.faq_image).into(binding.fqaDetailIv)
                        }
                        binding.fqaDetailTvTitle.text = result.result.faq_title
                        binding.fqaDetailTvContent.text = result.result.faq_content

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
            override fun onFailure(call: Call<FQADetailRes>, t: Throwable) {
                Log.d("getProfile", t.message ?: "통신오류")
            }
        })

    }
}