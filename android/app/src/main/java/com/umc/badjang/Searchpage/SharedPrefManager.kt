package com.umc.badjang.Searchpage

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.umc.badjang.ApplicationClass
import com.umc.badjang.Searchpage.models.SearchData


object SharedPrefManager  {

    private const val SHARED_SEARCH_HISTORY = "shared_search_history"
    private const val KEY_SEARCH_HISTORY = "key_search_history"

    private const val SHARED_SEARCH_HISTORY_MODE = "shared_search_history_mode"
    private const val KEY_SEARCH_HISTORY_MODE = "key_search_history_mode"

    // 검색어 저장 모드 설정하기
    fun setSearchHistoryMode(isActivated: Boolean){
        Log.d("검색 저장 모드 설정", "SharedPrefManager - setSearchHistoryMode() called / isActivated: $isActivated")

        // 쉐어드 가져오기
        val shared = ApplicationClass.instance.getSharedPreferences(SHARED_SEARCH_HISTORY_MODE, Context.MODE_PRIVATE)

        // 쉐어드 에디터 가져오기
        val editor = shared.edit()

        editor.putBoolean(KEY_SEARCH_HISTORY_MODE, isActivated)

        editor.apply()

    }


    // 검색어 저장 모드 확인하기
    fun checkSearchHistoryMode() : Boolean {

        // 쉐어드 가져오기
        val shared = ApplicationClass.instance.getSharedPreferences(SHARED_SEARCH_HISTORY_MODE, Context.MODE_PRIVATE)

        return shared.getBoolean(KEY_SEARCH_HISTORY_MODE, false)
    }


    // 검색 목록을 저장
    fun storeSearchHistoryList(searchHistoryList: MutableList<SearchData>){
        Log.d("검색목록 저장", "SharedPrefManager - storeSearchHistoryList() called")

        // 매개변수로 들어온 배열을 -> 문자열로 변환
        val searchHistoryListString : String = Gson().toJson(searchHistoryList)
        Log.d("검색목록 저장", "SharedPrefManager - searchHistoryListString : $searchHistoryListString")

        // 쉐어드 가져오기
        val shared = ApplicationClass.instance.getSharedPreferences(SHARED_SEARCH_HISTORY, Context.MODE_PRIVATE)

        // 쉐어드 에디터 가져오기
        val editor = shared.edit()

        editor.putString(KEY_SEARCH_HISTORY, searchHistoryListString)

        editor.apply()

    }

    // 검색 목록 가져오기
    fun getSearchHistoryList() : MutableList<SearchData> {

        // 쉐어드 가져오기
        val shared = ApplicationClass.instance.getSharedPreferences(SHARED_SEARCH_HISTORY, Context.MODE_PRIVATE)

        val storedSearchHistoryListString = shared.getString(KEY_SEARCH_HISTORY, "")!!


        var storedSearchHistoryList = ArrayList<SearchData>()

        // 검색 목록이 값이 있다면
        if (storedSearchHistoryListString.isNotEmpty()){

            // 저장된 문자열을 -> 객체 배열로 변경
            storedSearchHistoryList = Gson().
            fromJson(storedSearchHistoryListString, Array<SearchData>::class.java).
            toMutableList() as ArrayList<SearchData>
        }

        return storedSearchHistoryList
    }

    // 검색 목록 지우기
    fun clearSearchHistoryList(){
        Log.d("검색목록 지우기", "SharedPrefManager - clearSearchHistoryList() called")
        // 쉐어드 가져오기
        val shared = ApplicationClass.instance.getSharedPreferences(SHARED_SEARCH_HISTORY, Context.MODE_PRIVATE)

        // 쉐어드 에디터 가져오기
        val editor = shared.edit()

        // 해당 데이터 지우기
        editor.clear()

        // 변경 사항 적용
        editor.apply()
    }


}