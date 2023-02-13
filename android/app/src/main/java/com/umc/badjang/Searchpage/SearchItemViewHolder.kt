package com.umc.badjang.Searchpage

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.umc.badjang.Searchpage.models.SearchData
import com.umc.badjang.utils.Constants.TAG
import kotlinx.android.synthetic.main.past_search_list.view.*

class SearchItemViewHolder(itemView: View,searchRecyclerViewInterface: ISearchHistroyRecyclerView) :RecyclerView.ViewHolder(itemView), View.OnClickListener {


    private var mySearchRecyclerViewInterface: ISearchHistroyRecyclerView
    //뷰 가져오기기
    private val searchTermTextView = itemView.search_term_text
    private val deleteSearchBtn = itemView.delete_search_btn
    private val constraintSearchItem = itemView.constraint_search_item

    //리스너를 연결해준다 그렇지 않으면 onClick이 실행 되지 않음
    init{
        Log.d(TAG,"SearchItemViewHolder - init() called")
        //리스너 연결
        deleteSearchBtn.setOnClickListener(this)
        constraintSearchItem.setOnClickListener(this)
        this.mySearchRecyclerViewInterface = searchRecyclerViewInterface
    }

    //데이터와 뷰를 묶는다.
    fun bindWithView(searchItem:SearchData){
        Log.d(TAG,"SearchItemViewHolder - bindWithView() called")

        searchTermTextView.text = searchItem.term //검색어
    }

    override fun onClick(view: View?) {
       Log.d(TAG,"SearchItemViewHolder - onClick() called")
        when(view){
            deleteSearchBtn ->{
                Log.d(TAG,"SearchItemViewHolder - 검색 삭제 버튼 클릭!")
                this.mySearchRecyclerViewInterface.onSearchItemDeleteClicked(adapterPosition)
            }
            constraintSearchItem->{
                Log.d(TAG,"SearchItemViewHolder - 검색 아이템 클릭!")
                this.mySearchRecyclerViewInterface.onSearchItemClicked(adapterPosition)
            }
        }
    }
}
