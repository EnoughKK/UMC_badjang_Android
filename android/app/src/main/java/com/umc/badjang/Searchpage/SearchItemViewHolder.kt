package com.umc.badjang.Searchpage

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.umc.badjang.Searchpage.models.SearchData
import com.umc.badjang.utils.Constants.TAG
import kotlinx.android.synthetic.main.past_search_list.view.*

class SearchItemViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {

    //뷰 가져오기기
    private val searchTermTextView = itemView.search_term_text
    private val deleteSearchBtn = itemView.delete_search_btn
    private val constraintSearchItem = itemView.constraint_search_item



    //데이터와 뷰를 묶는다.
    fun bindWithView(searchItem:SearchData){
        Log.d(TAG,"SearchItemViewHolder - bindWithView() called")
    }
}
