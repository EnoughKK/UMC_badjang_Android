package com.umc.badjang.HomeMorePage

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.badjang.HomePagaApi.ImageLoader
import com.umc.badjang.HomePage.MainNationalNewsData
import com.umc.badjang.R
import com.umc.badjang.databinding.FragmentNationalNewsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// 홈화면 > 전국소식
class NationalNewsFragment : Fragment() {
    private lateinit var viewBinding: FragmentNationalNewsBinding // viewBinding

    private val sampleData = mutableListOf(/*mutableListOf("국가장학", "https://firebasestorage.googleapis.com/v0/b/badjang-88139.appspot.com/o/2023-1%ED%95%99%EA%B8%B0%20%EA%B5%AD%EA%B0%80%EC%9E%A5%ED%95%99%EA%B8%88%202%EC%B0%A8%20%EC%8B%A0%EC%B2%AD%EC%95%88%EB%82%B4%20%ED%8F%AC%EC%8A%A4%ED%84%B0.jpg?alt=media&token=84d810f1-28af-45ef-aee8-98177fe7a965",
        "★★ 2023학년도 1학기 국가장학금 2차 신청 안내 ★★", "2023학년도 1학기 국가장학금 2차 신청일정을 다음과 같이 알려드리니, 모든 학생들은 기간 내 신청해주시기 바랍니다.\n\n\n\n 가. 신청일정        \n\n - 2차 신청기간: 2023.2.2.(목) 9:00 ~ 2023.3.15.(수) 18:00\n\n - 서류제출＊ 및 가구원 동의 기간: 2023.2.2.(목) 9:00 ~ 2023.3.22.(수) 18:00\n\n +＊ 정부24 앱을 통해 모바일환경에서도 가족관계증명서 발급 및 재단 제출 가능\n\n\n\n 나. 신청대상: 모든 대학생\n\n - 신입생(고3, 재수생 등 입학예정자), 편입생, 재입학생 포함\n\n - 구,과기대 및 구,경상대 재학생 모두 \"경상국립대학교\"로 신청   \n\n + - 재학생은 1차 신청이 원칙, 2차 심사시 심사탈락(사유: 신청기간 미준수)\n" +
                "\n- 재학생은 재학중 2회에 한해 2차신청이 가능하며, 구제신청서에 공인인증서 서명완료시 심사후 지원가능\n" +
                "\n(단, 구제신청기회 기사용 또는 기타 탈락사유 존재시 지원불가)\n" +
                "\n\n\n다. 신청방법: 한국장학재단 홈페이지(http://www.kosaf.go.kr) 및 모바일 앱(https://mo.kosaf.go.kr/apps)을 통해 신청\n" +
                "\n\n\n라. 가구원 정보제공 동의 대상자: 부모＊(미혼자) 또는 배우자(기혼자)\n\n＊ 함께거주하지 않는 경우라도 부모 모두의 동의 필요\n" +
                "\n\n\n마. 기타사항\n\n- 미신청시 타장학금 지급대상에서도 제외될 수 있음\n\n- 서류제출 및 가구원 정보제공 동의를 완료하지 않는 경우 소득분위 산정불가로 심사불가\n",
        1, 10),
        mutableListOf("충주시청", "https://firebasestorage.googleapis.com/v0/b/badjang-88139.appspot.com/o/%EC%B6%A9%EB%B6%81.png?alt=media&token=caa10371-0dc2-4017-9b36-94c562f53dbc",
        "충주 청년관광코디네이터 육성 사업", " 관광축제 프로그램 개발 충주체험관광센터와 연계사업 추진 충주중원문화재단, 충주체험관광센터 업무지원 홍보업무 지원\n", 0, 5),
        */mutableListOf("대구경영자총협회", "https://www.jobaba.net/resource/images/web/2018/01/02/1514871744928_6915623648.png",
        "청년테마별 취업지원", "지역의 청년 구직자에게 실전취업 준비 프로그램과 지역 기업 탐방 및 취재의 기회를 제공하여 지역기업에 대한 인식 개선과 이미지 제고로 일자리 미스매칭과 지역기업 취업확대에 기여 \n" +
                    "\n지역일자리 네트워크 구축, 청년구직자 역량강화 프로그램, 청년공감 기업탐방기자단, 청년취업멘토링, 일자리행사\n", 2, 21),
        mutableListOf("재단법인 플라톤 아카데미", "https://cyberimg.wku.ac.kr/ComBoard/img/upload/1115983888724/1115985252888/2023/01/1675061273364/org/bbs1.jpg",
        "2023학년도 재단법인 플라톤 아카데미 인문 지혜 장학생 판플러스(PAN+) 모집 안내", "1. 관련: 재단법인 플라톤 아카데미 제2023-0001호(2023.01.27.)\n\n" +
                    "2. (재)플라톤 아카데미에서는 삶의 올바른 방향을 설정하고 아름다운 사회를 만들기 위해 기여하는 인재 양성을 위해 2023학년도 인문 지혜 장학생 판플러스(PAN+)를 모집하오니, 관심있는 학생들은 신청하시기 바랍니다.\n\n" +
                    "  가. 장학금명: (재)플라톤 아카데미 인문 지혜 장학생 판플러스(PAN+)\n\n" +
                    "  나. 모집대상\n\n" +
                    "    1) 전공불문 전국 4년제 대학교 재학생, 휴학생 가능\n\n" +
                    "    2) 총 평균평점 3.5 이상\n\n" +
                    "  다. 지원내용\n\n" +
                    "    1) 인문 세미나‧네트워크 활동 기회\n\n" +
                    "    2) 수료증 및 해외 경험 지원\n\n\n" +
                    "    3) 인문 활동을 위한 서적 및 관련 콘텐츠\n\n" +
                    "    4) 연합 멘토링‧문화활동‧봉사활동 참여\n\n" +
                    "  라. 기타사항: 상세내용 재단 홈페이지(www.platonacademy.org) 공지사항 및 공고문 참고\n", 0, 0),
        mutableListOf("부경대학교 / 국제교류부", "https://www.pknu.ac.kr/images/front/sub/univ_logo00.png",
        "2023년도 대만장학금 장학생 선발 안내", "2023년도 <대만장학금> 장학생 선발을 아래와 같이 안내합니다. \n\n" +
                    "  가. 선발 과정: 석·박사 학위과정\n\n" +
                    "  나. 선발 인원: 6명\n\n" +
                    "  다. 지원 방법: 지원자가 주한국타이페이대표부 교육조에 직접 지원(등기우편으로 송부)\n\n" +
                    "  라. 서류 제출 마감: 2023. 3. 31.(금) 18:00까지 도착분\n\n" +
                    "  마. 지원자격 및 제출서류: <붙임> 선발 안내 참조\n\n" +
                    "  바. 문의처: 02-6329-6058(대만장학금 담당자), korea@mail.moe.gov.tw\n\n" +
                    "   ※ 선발 및 장학금 관련 문의는 주한국타이페이대표부 교육조 <대만장학금> 담당자에게 직접 문의할 것\n", 0, 0))

    // 전국소식 리스트 recyclerview adapter
    private val nationalNewsDatas = mutableListOf<NationalNewsData>()
    private lateinit var nationalNewsAdapter: NationalNewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentNationalNewsBinding.inflate(layoutInflater);

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // recyclerview 세팅
        initRecycler()

        // 전국소식 데이터 추가
        val nationalNewsImg: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.popular_post_img)
        for(i: Int in 0..(sampleData.size - 1)) {
            CoroutineScope(Dispatchers.Main).launch {
                val img: Bitmap = withContext(Dispatchers.IO) {
                    ImageLoader.loadImage(sampleData[i][1].toString())!!
                }
                addNationalNewsData(
                    NationalNewsData(sampleData[i][0].toString(),
                        img, sampleData[i][2].toString(), sampleData[i][3].toString(),
                        0, i+3))
            }
        }

        // 이전 버튼 선택 시
        viewBinding.nationalNewsBackBtn.setOnClickListener {
            // 이전 페이지로 이동
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    // 전국소식 recyclerview 세팅
    private fun initRecycler() {
        nationalNewsAdapter = NationalNewsAdapter(requireContext())
        viewBinding.nationalNewsRecyclerview.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        viewBinding.nationalNewsRecyclerview.adapter = nationalNewsAdapter
        nationalNewsAdapter.datas = nationalNewsDatas
    }

    // 전국소식 데이터 추가
    private fun addNationalNewsData(nationalNews: NationalNewsData) {
        nationalNewsDatas.apply {
            add(nationalNews)
        }
        nationalNewsAdapter.notifyDataSetChanged()
    }
}