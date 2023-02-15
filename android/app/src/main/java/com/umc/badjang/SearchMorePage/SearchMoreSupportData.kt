package com.umc.badjang.SearchMorePage

data class SearchMoreSupportData(
    val universityLabel: String,
    val subsidyCategory: String,
    val subsidyTitle: String,
    val subsidyContents: String,
    var bookMark: Boolean,
    val subsidyViews: Int,
    val subsidyComments: Int
)
