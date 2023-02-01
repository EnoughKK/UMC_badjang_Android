package com.umc.badjang.HomePage

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.umc.badjang.HomePagaApi.ImageLoader
import com.umc.badjang.R
import kotlinx.android.synthetic.main.home_recommend_slide_item.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// 메인 홈 > 추천 배너 슬라이드 어댑터
class MainRecommendSliderAdapter: PagerAdapter() {

    private var mContext: Context? = null

    private var sample = mutableListOf("https://firebasestorage.googleapis.com/v0/b/badjang-88139.appspot.com/o/%ED%99%94%EB%A9%B4%20%EC%BA%A1%EC%B2%98%202023-02-01%20144934.png?alt=media&token=7852e453-efcb-468f-8d45-192bbdd13eb7",
        "https://firebasestorage.googleapis.com/v0/b/badjang-88139.appspot.com/o/%ED%99%94%EB%A9%B4%20%EC%BA%A1%EC%B2%98%202023-02-01%20145112.png?alt=media&token=434f4256-6f6c-4615-b5f5-92dd30b9aae8",
        "https://firebasestorage.googleapis.com/v0/b/badjang-88139.appspot.com/o/%ED%99%94%EB%A9%B4%20%EC%BA%A1%EC%B2%98%202023-02-01%20144734.png?alt=media&token=5c4545f5-c320-47e6-b0be-a36e67b29789")

    fun MainRecommendSliderAdapter(context: Context){
        mContext = context
    }

    // position에 해당하는 페이지 생성
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val recommendImage = LayoutInflater.from(container.context).inflate(R.layout.home_recommend_slide_item, container, false)
        //view.title.text= "TEXT "+position

        CoroutineScope(Dispatchers.Main).launch {
            val img: Bitmap = withContext(Dispatchers.IO) {
                ImageLoader.loadImage(sample[position].toString())!!
            }
            recommendImage.main_recommend_image.setImageBitmap(img)
        }

        container.addView(recommendImage)
        return recommendImage
    }

    // position에 위치한 페이지 제거
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }


    // 사용가능한 뷰 개수 리턴
    override fun getCount(): Int {
        return 3
    }

    // 페이지뷰가 특정 키 객체(key object)와 연관 되는지 여부
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return (view == `object`)
    }
}