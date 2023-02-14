package com.umc.badjang.MyPage.FQA

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.umc.badjang.ApplicationClass
import com.umc.badjang.MainActivity
import com.umc.badjang.MyPage.FQA.Model.FQARes
import com.umc.badjang.MyPage.MyWrite.MyWriteAdapter
import com.umc.badjang.MyPage.MyWrite.MyWriteRetrofitInterface
import com.umc.badjang.MyPage.MyWrite.WriteData
import com.umc.badjang.MyPage.MyWrite.model.MyWriteRes
import com.umc.badjang.databinding.FragmentFqaBinding
import com.umc.badjang.databinding.FragmentQnaBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


data class FQAData(
    val faq_content: String,
    val faq_title: String,
    val faq_img: String,
    val faq_idx: Long,
    val faq_status: String,
    val faq_updateAt: String,
    val faq_createAt: String)

class FQAFragment : Fragment() {
    private lateinit var binding: FragmentFqaBinding

    var activity: MainActivity? = null
    lateinit var rvAdapter : FQAAdapter
    var dataList = arrayListOf<FQAData>()

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
        binding = FragmentFqaBinding.inflate(layoutInflater);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvAdapter = FQAAdapter(dataList, requireContext())
        getFQA()
        binding.fqaRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        binding.fqaRv.adapter = rvAdapter

        binding.fqaPrev.setOnClickListener {
            // 이전 페이지로 이동
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun getFQA(){
        //Log.d("postScholarship", "호출은 된다.")
        val fQAInterface = ApplicationClass.sRetrofit.create(FQARetrofitInterface::class.java)
        fQAInterface.getFQA().enqueue(object :
            Callback<FQARes> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<FQARes>, response: Response<FQARes>) {
                if (response.isSuccessful) {
                    val result = response.body() as FQARes
                    if(result.message == "요청에 성공하였습니다."){
                        for(i in 0 until result.result.size){
                            var img = ""
                            if(result.result[i].faq_img == null){
                                img = ""
                            }else{
                                img = result.result[i].faq_img.toString()
                            }

                            dataList.add(FQAData(result.result[i].faq_content, result.result[i].faq_title,img,
                            result.result[i].faq_idx, result.result[i].faq_status, result.result[i].faq_updateAt,
                            result.result[i].faq_createAt))
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
            override fun onFailure(call: Call<FQARes>, t: Throwable) {
                Log.d("getProfile", t.message ?: "통신오류")
            }
        })

    }
}