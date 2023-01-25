package com.umc.badjang.ScholarshipPage

data class ScholarshipRVDTO (
    val universityLabel: String,
    val scholarshipCategory: String,
    val scholarshipTitle: String,
    val scholarshipContents: String,
    var bookMark: Boolean,
    val scholarshipViews: Int,
    val scholarshipComments: Int
    )