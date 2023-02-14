package com.umc.badjang.MyPage.Noti

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.badjang.ApplicationClass
import com.umc.badjang.MainActivity
import com.umc.badjang.MyPage.MyWrite.MyChatData
import com.umc.badjang.MyPage.MyWrite.MyWriteChatAdapter
import com.umc.badjang.MyPage.MyWrite.WriteData
import com.umc.badjang.MyPage.Noti.model.GetNotiResponse
import com.umc.badjang.databinding.FragmentMyWriteBinding
import com.umc.badjang.databinding.FragmentNotiBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

data class NotiData(
    val notice_idx : Int,
    val notice_title : String,
    val notice_content : String,
    val notice_image : String?,
    val notice_createAt : String,
    val notice_updateAt : String?
        )

class NotiFragment : Fragment() {
    private lateinit var binding: FragmentNotiBinding
    private lateinit var rvAdapter : NotiAdapter
    private var dataList = arrayListOf<NotiData>()
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
        binding = FragmentNotiBinding.inflate(layoutInflater);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvAdapter = NotiAdapter(dataList, requireContext())
        getNoti()
        binding.notiRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        binding.notiRv.adapter = rvAdapter
        binding.notiPrev.setOnClickListener {
            // 이전 페이지로 이동
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
    private fun getNoti(){
        //Log.d("postScholarship", "호출은 된다.")
        val notiInterface = ApplicationClass.sRetrofit.create(NotiInterface::class.java)
        notiInterface.getNoti().enqueue(object :
            Callback<GetNotiResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<GetNotiResponse>, response: Response<GetNotiResponse>) {
                if (response.isSuccessful) {
                    val result = response.body() as GetNotiResponse
                    if(result.message == "요청에 성공하였습니다."){
                        for(i in 0 until result.result.size){

                            var img = ""
                            if(result.result[i].notice_image == null){
                                img = ""
                            }else{
                                img = result.result[i].notice_image.toString()
                            }
                            dataList.add(
                                NotiData(result.result[i].notice_idx, result.result[i].notice_title,
                                    result.result[i].notice_content, img,result.result[i].notice_createAt,
                                    result.result[i].notice_updateAt)
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
            override fun onFailure(call: Call<GetNotiResponse>, t: Throwable) {
                Log.d("getProfile", t.message ?: "통신오류")
            }
        })

    }
}