package com.umc.badjang.Searchpage

import com.umc.badjang.ScholarshipPage.SubsidyRVAdapter
import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.badjang.MainActivity
import com.umc.badjang.R
import com.umc.badjang.Retrofit.RetrofitManager
import com.umc.badjang.ScholarshipPage.Model.supportOpiDTO
import com.umc.badjang.databinding.FragmentSubsidyViewpager2Binding
import com.umc.badjang.databinding.RvSubsidyBinding
import com.umc.badjang.utils.API
import com.umc.badjang.utils.RESPONSE_STATE
import com.umc.badjang.utils.supportApiUrl
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import retrofit2.Callback
import retrofit2.Retrofit
import javax.xml.parsers.DocumentBuilderFactory

// 창업지원 데이터
private var supportDatas = ArrayList<supportOpiDTO>()

// 창업지원 url
private var url = "https://www.youthcenter.go.kr/opi/empList.do?pageIndex=1&display=30&query=청년취업&openApiVlak=73444351051dbc5ea4541693"

class SearchViewpager3Fragment:Fragment() {
    private lateinit var viewBinding: FragmentSubsidyViewpager2Binding

    var activity: MainActivity? = null

    // 지원금 recyclerview adapter

    private lateinit var subsidyAdapter: SubsidyRVAdapter

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
        viewBinding = FragmentSubsidyViewpager2Binding.inflate(layoutInflater);

        addSupportAll()
        initRecycler()

        // 카테고리 선택
        viewBinding.spinnerCategory.adapter = ArrayAdapter.createFromResource(requireContext(), R.array.spinner_category_support, R.layout.spinner_layout)
        // 카테고리 클릭리스너
        viewBinding.spinnerCategory.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {
                when (viewBinding.spinnerCategory.getItemAtPosition(position)) {
                    "전체" -> {
                        addSupportAll()
                    }
                    "취업지원" -> {
                        supportDatas.clear()
                        threadStart(supportApiUrl.URL1)
                    }
                    "창업지원" -> {
                        supportDatas.clear()
                        threadStart(supportApiUrl.URL2)
                    }
                    "주거·금융" -> {
                        supportDatas.clear()
                        threadStart(supportApiUrl.URL3)
                        threadStart(supportApiUrl.URL4)
                    }
                    "생활·복지" -> {

                        supportDatas.clear()
                        threadStart(supportApiUrl.URL5)
                        threadStart(supportApiUrl.URL6)
                    }
                    "정책참여" -> {
                        supportDatas.clear()
                        threadStart(supportApiUrl.URL7)
                    }
                    "코로나19" -> {
                        supportDatas.clear()
                        threadStart(supportApiUrl.URL8)
                    }
                    else -> {
                        Log.d(ContentValues.TAG, "onItemSelected: null")
                    }
                }
                initRecycler()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        return viewBinding.root
    }

    // recyclerview 셋팅
    private fun initRecycler() {

        // 지원금 recyclerview 셋팅
        subsidyAdapter = SubsidyRVAdapter(requireContext())
        viewBinding.subsidyRvContainer.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        viewBinding.subsidyRvContainer.adapter = subsidyAdapter
        subsidyAdapter.datas = supportDatas
    }

    private fun threadStart(url: String){

        val thread = Thread(NetworkThread(url))
        thread.start()
        thread.join()
    }

    private fun addSupportAll(){
        supportDatas.clear()

        threadStart(supportApiUrl.URL1)
        threadStart(supportApiUrl.URL2)
        threadStart(supportApiUrl.URL3)
        threadStart(supportApiUrl.URL4)
        threadStart(supportApiUrl.URL5)
        threadStart(supportApiUrl.URL6)
        threadStart(supportApiUrl.URL7)
        threadStart(supportApiUrl.URL8)
    }

    class NetworkThread(
        var url: String): Runnable {

        @RequiresApi(Build.VERSION_CODES.N)
        override fun run() {

            try {

                val xml: Document =
                    DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(url)


                xml.documentElement.normalize()

                //찾고자 하는 데이터가 어느 노드 아래에 있는지 확인
                val list: NodeList = xml.getElementsByTagName("emp")

                //list.length-1 만큼 얻고자 하는 태그의 정보를 가져온다
                for (i in 0..list.length - 1) {

                    val n: Node = list.item(i)

                    if (n.getNodeType() == Node.ELEMENT_NODE) {

                        val elem = n as Element

                        val map = mutableMapOf<String, String>()


                        // 이부분은 어디에 쓰이는지 잘 모르겠다.
                        for (j in 0..elem.attributes.length - 1) {

                            map.putIfAbsent(elem.attributes.item(j).nodeName,
                                elem.attributes.item(j).nodeValue)

                        }

                        val support_institution = elem.getElementsByTagName("polyBizTy").item(0).textContent     // 정책기관
                        val support_name = elem.getElementsByTagName("polyBizSjnm").item(0).textContent          // 정책이름
                        val support_category = elem.getElementsByTagName("plcyTpNm").item(0).textContent         // 정책유형
                        val support_content = elem.getElementsByTagName("polyItcnCn").item(0).textContent        // 정책소개
                        val support_age = elem.getElementsByTagName("ageInfo").item(0).textContent               // 참여요건 - 연령
                        val support_job = elem.getElementsByTagName("empmSttsCn").item(0).textContent            // 참여요건 - 취업상태
                        val support_education = elem.getElementsByTagName("accrRqisCn").item(0).textContent      // 참여요건 - 학력
                        val support_major = elem.getElementsByTagName("majrRqisCn").item(0).textContent          // 참여요건 - 전공
                        val support_field = elem.getElementsByTagName("splzRlmRqisCn").item(0).textContent       // 참여요건 - 특화분야
                        val support_apply = elem.getElementsByTagName("cnsgNmor").item(0).textContent            // 신청기관명
                        val support_date = elem.getElementsByTagName("rqutPrdCn").item(0).textContent            // 신청기간
                        val support_procedure = elem.getElementsByTagName("rqutProcCn").item(0).textContent      // 신청절차
                        val support_announce = elem.getElementsByTagName("jdgnPresCn").item(0).textContent       // 심사발표
                        val support_link = elem.getElementsByTagName("rqutUrla").item(0).textContent             // 사이트 링크 주소

                        val supportOpiItem = supportOpiDTO(
                            support_institution = support_institution,
                            support_name = support_name,
                            support_category = support_category,
                            support_content = support_content,
                            support_age = support_age,
                            support_job = support_job,
                            support_education = support_education,
                            support_major = support_major,
                            support_field = support_field,
                            support_apply = support_apply,
                            support_date = support_date,
                            support_procedure = support_procedure,
                            support_announce = support_announce,
                            support_link = support_link
                        )

                        supportDatas.add(supportOpiItem)

                        Log.d("로그", "run: ${support_name}")

                    }
                }
            } catch (e: Exception) {
                Log.d("TTT", "오픈API" + e.toString())
            }
        }
    }

}