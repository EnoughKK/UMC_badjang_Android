package com.umc.badjang.Model

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "empsInfo")
data class Support(

    @PropertyElement(name = "pageIndex")
    val pageIndex: Int,

    @PropertyElement(name = "totalCnt")
    val totalCnt: Int,

    @Element(name = "emp")
    val emp: Emp
)

@Xml(name = "emp")
data class Emp(

    @PropertyElement(name = "bizId")
    val bizId: String,

    @PropertyElement(name = "polyBizTy")
    val polyBizTy: String,

    @PropertyElement(name = "polyBizSjnm")
    val polyBizSjnm: String,

    @PropertyElement(name = "polyItcnCn")
    val polyItcnCn: String,

    @PropertyElement(name = "plcyTpNm")
    val plcyTpNm: String,

    @PropertyElement(name = "sporScvl")
    val sporScvl: String,

    @PropertyElement(name = "sporCn")
    val sporCn: String,

    @PropertyElement(name = "ageInfo")
    val ageInfo: String,

    @PropertyElement(name = "accrRqisCn")
    val accrRqisCn: String,

    @PropertyElement(name = "majrRqisCn")
    val majrRqisCn: String,

    @PropertyElement(name = "splzRlmRqisCn")
    val splzRlmRqisCn: String,

    @PropertyElement(name = "cnsgNmor")
    val cnsgNmor: String,

    @PropertyElement(name = "rqutPrdCn")
    val rqutPrdCn: String,

    @PropertyElement(name = "rqutProcCn")
    val rqutProcCn: String,

    @PropertyElement(name = "jdgnPresCn")
    val jdgnPresCn: String,

    @PropertyElement(name = "rqutUrla")
    val rqutUrla: String,

)