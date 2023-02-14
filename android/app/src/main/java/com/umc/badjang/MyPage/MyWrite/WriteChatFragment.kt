package com.umc.badjang.MyPage.MyWrite

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
import com.umc.badjang.MyPage.MyWrite.model.MyWriteChatRes
import com.umc.badjang.MyPage.MyWrite.model.MyWriteRes
import com.umc.badjang.databinding.FragmentWriteChatBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

data class MyChatData(val comment_idx: Int,
                     val post_idx: Int,
                      val post_name:String,
                     val post_category: String,
                     val comment_content: String)

class WriteChatFragment : Fragment() {
    private lateinit var binding: FragmentWriteChatBinding

    //api 통신을 위한 retrofit
    private var retrofit : Retrofit? =null

    var activity: MainActivity? = null

    private lateinit var rvAdapter : MyWriteChatAdapter
    private var dataList = arrayListOf<MyChatData>()

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
        binding = FragmentWriteChatBinding.inflate(layoutInflater);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvAdapter = MyWriteChatAdapter(dataList, requireContext())
        getMyChat()
        binding.writeChatRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        //val decoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        //binding.writeRv.addItemDecoration(decoration)
        binding.writeChatRv.adapter = rvAdapter

    }

    private fun getMyChat(){
        //Log.d("postScholarship", "호출은 된다.")
        val myWriteChatInterface = ApplicationClass.sRetrofit.create(MyWriteRetrofitInterface::class.java)
        myWriteChatInterface.getMyWriteChat().enqueue(object :
            Callback<MyWriteChatRes> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<MyWriteChatRes>, response: Response<MyWriteChatRes>) {
                if (response.isSuccessful) {
                    val result = response.body() as MyWriteChatRes
                    if(result.message == "요청에 성공하였습니다."){
                        for(i in 0 until result.result.size){

                            dataList.add(MyChatData(result.result[i].comment_idx, result.result[i].post_idx, result.result[i].post_name,
                                result.result[i].post_category, result.result[i].comment_content))
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
            override fun onFailure(call: Call<MyWriteChatRes>, t: Throwable) {
                Log.d("getProfile", t.message ?: "통신오류")
            }
        })

    }
}