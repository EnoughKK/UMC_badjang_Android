package com.umc.badjang.HomePage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.umc.badjang.R

// 메인 홈 > 추천 배너 슬라이드 어댑터
class MainRecommendSliderAdapter: PagerAdapter() {

    private var mContext: Context? = null

    fun MainRecommendSliderAdapter(context: Context){
        mContext = context
    }

    // position에 해당하는 페이지 생성
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val recommendImage = LayoutInflater.from(container.context).inflate(R.layout.home_recommend_slide_item, container, false)
        //view.title.text= "TEXT "+position

        container.addView(recommendImage)
        return recommendImage
    }

    // position에 위치한 페이지 제거
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }


    // 사용가능한 뷰 개수 리턴
    override fun getCount(): Int {
        return 5
    }

    // 페이지뷰가 특정 키 객체(key object)와 연관 되는지 여부
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return (view == `object`)
    }
}