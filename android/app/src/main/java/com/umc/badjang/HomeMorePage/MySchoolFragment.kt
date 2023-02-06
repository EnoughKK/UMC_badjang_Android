package com.umc.badjang.HomeMorePage

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.badjang.R
import com.umc.badjang.databinding.FragmentMySchoolBinding

// 홈화면 > 우리학교 장학금
class MySchoolFragment : Fragment() {
    private lateinit var viewBinding: FragmentMySchoolBinding // viewBinding

    private val sampleData = mutableListOf(NationalNewsDataBitmap("경상국립대학교","재학생 장학금 - 개척",
        "- 최저기준\n" +
                "   - 직전학기 10학점 이상 이수하고, 직전학기 평균평점 3.0 이상인 자\n" +
                "   - 학과 추천에 의하여 선발 (학과별 자체 선정기준에 의함)\n", null, 1, 13),
        NationalNewsDataBitmap("경상국립대학교",  "재학생 장학금 - 희망,경상국립대학교",
            "- 최저기준\n" +
                    "      - 직전학기 10학점 이상 이수하고, 직전학기 평균평점 2.0 이상인 자\n" +
                    "   - 학과 추천에 의하여 선발 (학과별 자체 선정기준에 의함)\n", null,0, 8),
        NationalNewsDataBitmap("경상국립대학교 / 학생과",  "2023학년도 1학기 특별장학금 신청 안내",
            "■ 2023학년도 1학기 특별장학금 신청 대상자는 붙임 안내문을 참고하여 기한 내 신청하여 주시기 바랍니다.\n\n" +
                    "  - 신청방법: 통합서비스(https://my. gnu.ac.kr) 에서 신청\n\n" +
                    "  - 신청대상 장학금 및 신청기한(매학기 필수 신청)\n\n\n" +
                    "국가유공자 - 국가유공자 본인 및 자녀\n\n" +
                    "북한이탈주민 - 북한이탈주민 중 교육보호 대상자\n\n" +
                    "장애학생 - 중증, 경증\n\n" +
                    "다문화가족자녀 - 부모 중 한 명이 외국 국적자 또는 귀화한 경우\n\n" +
                    "가족사랑\n\n" +
                    "<구, 과기대 학생으로만 구성된 경우>\n\n" +
                    " - 다자녀: 3.5 이상\n\n" +
                    "  ※ 본인이 셋째 이상인 경우(첫째×, 둘째×)\n\n" +
                    " - 형제자매: 3.5 이상 / 부부,자: 2.5 이상\n\n" +
                    "1차: ’23. 2. 13.까지 \n\n" +
                    "  ⇒ 등록금 납부 시 감면\n\n" +
                    "2차: ’23. 3. 1. ~ 3. 31. 까지\n\n" +
                    "  ⇒ ’23. 5월 중 학생 계좌 지급\n\n\n" +
                    "학부과정(대학원 제외)에 가족이 2명 이상 재학 중인 경우\n\n" +
                    "·’23. 3. 1. ~3. 31.까지\n\n" +
                    "  ⇒ ’23. 5월 중 학생 계좌 지급\n\n" +
                    "※ 등록금 사전감면 불가\n\n" +
                    "  ※ 장학금 수혜대상자는 법정의무교육을 이수하여야 함: 폭력예방 교육(성폭력, 가정폭력), 장애인 인식 개선 교육\n", null,2, 23),
        NationalNewsDataBitmap("동암장학회", "2023년 동암장학회 장학생 선발 안내",
            "1. 관련: 재단법인 동암장학회 2023-5(2023.01.17.)\n\n" +
                    "2. 동암장학회에서는 2022년 장학생을 선발하오니 관심있는 학생들은 신청하시기 바랍니다.\n\n" +
                    "  가. 선발인원: 00명(성적우수장학생, 생활장학생)\n\n" +
                    "  나. 지원자격 및 요건\n\n" +
                    "    1) 지원자격: 경주시에서 출생하고 거주하거나 원적이 경주시 강동면인 국내외 4년제 대학교의 재학생 또는 신입생으로 품행이 바르며 경제적 여건이 어렵고 성적이 우수한 학생으로 아래 지원 요건에 해당하는 자 \n\n" +
                    "    2) 지원요건\n\n" +
                    "구분 - 성적우수장학생 / 생활장학생\n\n" +
                    "지원 요건 - 누적 학기 학점 3.5이상 및 직전 학기 학점 3.8 이상인 학생\n\n / \n\n" +
                    "누적학점과 직전학기 학점 모두 B학점(2.6/4.5) 이상인 학생으로\n\n" +
                    " - 부모나 본인이 아래 항목 중 1개 이상에 해당하는 자\n\n" +
                    " · 기초생활수급자 또는 차상위계층\n\n" +
                    " · 세대 건강보험료(부모합산) 월간 납부 금액이 100,000원 이하인 자 \n\n" +
                    " · 직전년도 연간 재산세(부모합산) 납부금액이 70,000원 이하인 자\n\n" +
                    "장학금 액 및 성격 - 200만원/학기 등록금 한도 내 지원 / 200만원/학기 생활비 지원\n\n\n\n" +
                    "  다. 접수기한: 2023. 2. 3.(금) 17:00(서류 도착 기준)\n\n" +
                    "  라. 제출서류: [붙임 1] 선발공고 참고\n\n" +
                    "  마. 제출처: (우) 44259 울산광역시 북구 염포로 260-10 ㈜경동도시가스 내 (재)동암장학회 사무국 \n\n\n\n" +
                    "붙임 1. 2023년 동암장학회 장학생 선발 공고문 1부. \n\n" +
                    "      2. 제출서류(서식) 1부. 끝.\n", null,0, 11))

    // 우리학교 장학금 recyclerview adapter
    private val mySchoolDatas = mutableListOf<NationalNewsDataBitmap>()
    private lateinit var mySchoolAdapter: NationalNewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentMySchoolBinding.inflate(layoutInflater);

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 이전 버튼 선택 시
        viewBinding.mySchoolBackBtn.setOnClickListener {
            // 이전 페이지로 이동
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
            requireActivity().supportFragmentManager.popBackStack()
        }

        // recyclerview 세팅
        initRecycler()

        // 우리학교 데이터 추가
        val mySchoolImg: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.popular_post_img)
        for(i: Int in (0..sampleData.size - 1)) {
            addMySchoolData(
               sampleData[i])
        }
    }

    // 우리학교 recyclerview 세팅
    private fun initRecycler() {
        mySchoolAdapter = NationalNewsAdapter(requireContext())
        viewBinding.mySchoolRecyclerview.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        viewBinding.mySchoolRecyclerview.adapter = mySchoolAdapter
        mySchoolAdapter.datas = mySchoolDatas
    }

    // 우리학교 데이터 추가
    private fun addMySchoolData(mySchoolData: NationalNewsDataBitmap) {
        mySchoolDatas.apply {
            add(mySchoolData)
        }
        mySchoolAdapter.notifyDataSetChanged()
    }

}