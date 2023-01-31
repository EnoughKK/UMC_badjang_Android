package com.umc.badjang.ScholarshipPage

data class SubsidyRVDTO (
    val universityLabel: String,
    val subsidyCategory: String,
    val subsidyTitle: String,
    val subsidyContents: String,
    var bookMark: Boolean,
    val subsidyViews: Int,
    val subsidyComments: Int
)