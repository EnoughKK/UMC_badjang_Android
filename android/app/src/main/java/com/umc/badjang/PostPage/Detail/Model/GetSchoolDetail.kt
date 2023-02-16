package com.umc.badjang.PostPage.Detail.Model

data class GetSchoolDetail(
    val getOneOfSchoolBoardRes : List<GetSchoolOnePostRes>,
    val getSchoolBoardCommentRes : List<GetSchoolCommentRes>
)
