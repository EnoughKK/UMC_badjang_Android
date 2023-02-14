package com.umc.badjang.MyPage.MyWrite

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.badjang.ApplicationClass
import com.umc.badjang.MainActivity
import com.umc.badjang.MyPage.MyWrite.model.MyWriteRes
import com.umc.badjang.databinding.FragmentWriteBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

data class WriteData(
    val user_name : String,
    val user_profileimage_url : String,
    val post_createAt:String,
    val post_idx: Int,
    val post_name: String,
    val post_content: String,
    val post_image: String,
    val post_view: Int,
    val post_recommend: Int,
    val post_comment: Int,
    val post_category: String,
    val post_anonymity: String )

class WriteFragment : Fragment() {
    private lateinit var binding: FragmentWriteBinding // viewBinding
    lateinit var rvAdapter : MyWriteAdapter
    //api 통신을 위한 retrofit
    private var retrofit : Retrofit? =null
    var dataList = arrayListOf<WriteData>()
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
        binding = FragmentWriteBinding.inflate(layoutInflater);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getMyWrite()

        rvAdapter = MyWriteAdapter(dataList, requireContext())
        binding.writeRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        //val decoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        //binding.writeRv.addItemDecoration(decoration)
        binding.writeRv.adapter = rvAdapter

    }
    private fun getMyWrite(){
        //Log.d("postScholarship", "호출은 된다.")
        val myWriteInterface = ApplicationClass.sRetrofit.create(MyWriteRetrofitInterface::class.java)
        myWriteInterface.getMyWrite().enqueue(object :
            Callback<MyWriteRes> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<MyWriteRes>, response: Response<MyWriteRes>) {
                if (response.isSuccessful) {
                    val result = response.body() as MyWriteRes
                    if(result.message == "요청에 성공하였습니다."){
                        for(i in 0 until result.result.size){
                            var img = ""
                            if(result.result[i].post_image == null){
                                img = ""
                            }else{
                                img = result.result[i].post_image.toString()
                            }
                            var name = ""
                            if(result.result[i].post_anonymity == "N"){
                                name = result.result[i].user_name
                            }else{
                                name = "익명"
                            }
                            dataList.add(WriteData(result.result[i].user_name, result.result[i].user_profileimage_url,
                                result.result[i].post_createAt, result.result[i].post_idx,result.result[i].post_name,
                                result.result[i].post_content, img, result.result[i].post_view, result.result[i].post_recommend,
                                result.result[i].post_comment, result.result[i].post_category, name))
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
            override fun onFailure(call: Call<MyWriteRes>, t: Throwable) {
                Log.d("getProfile", t.message ?: "통신오류")
            }
        })

    }
}