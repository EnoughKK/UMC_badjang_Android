package com.umc.badjang.Searchpage.SearchHistroy

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.badjang.R
import com.umc.badjang.Searchpage.models.SearchData
import com.umc.badjang.utils.Constants.TAG

class SearchHistoryRecyclerViewAdapter(searchHistoryRecyclerViewInterface: ISearchHistroyRecyclerView):RecyclerView.Adapter<SearchItemViewHolder>() {
    private  var searchHistoryList:ArrayList<SearchData> =ArrayList()
    private var iSearchHistroyRecyclerView: ISearchHistroyRecyclerView? =null

    init{
        Log.d(TAG,"SearchHistoryRecyclerViewAdapter - init() called")
        this.iSearchHistroyRecyclerView = searchHistoryRecyclerViewInterface
    }

    //뷰홀더가 메모리에 올라갔을 때
    //뷰홀더와 레이아웃을 연결시켜 준다.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemViewHolder {
        val searchItemViewHolder = SearchItemViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.past_search_list,parent,false)
            ,this.iSearchHistroyRecyclerView!!
        )
        return  searchItemViewHolder
    }
    override fun getItemCount(): Int {
        return searchHistoryList.size
    }

    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {

       val dataItem : SearchData = this.searchHistoryList[position]
        holder.bindWithView(dataItem)
    }

    //외부에서 어답터에 데이터 배열을 넣어준다.
    fun submitList(searchHistoryList: ArrayList<SearchData>){
        this.searchHistoryList=searchHistoryList
    }


}