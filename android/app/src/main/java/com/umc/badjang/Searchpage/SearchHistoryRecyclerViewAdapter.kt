package com.umc.badjang.Searchpage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.badjang.R
import com.umc.badjang.Searchpage.models.SearchData

class SearchHistoryRecyclerViewAdapter:RecyclerView.Adapter<SearchItemViewHolder>() {
    private  var searchHistoryList:ArrayList<SearchData> =ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemViewHolder {
        val searchItemViewHolder = SearchItemViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.past_search_list,parent,false)
        )
        return  searchItemViewHolder
    }

    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return searchHistoryList.size
    }

    //외부에서 어답터에 데이터 배열을 넣어준다.
    fun submitList(searchHistoryList: ArrayList<SearchData>){
        this.searchHistoryList=searchHistoryList
    }


}