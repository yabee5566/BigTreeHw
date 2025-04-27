package com.iiaannppaann.bigtreehw.data.http.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StockPEPBDividendYieldData(
    @Json(name = "Date")
    val date: String,
    @Json(name = "Code")
    val code: String,
    @Json(name = "Name")
    val name: String,
    @Json(name = "PEratio")
    val peRatio: Float, // 本益比
    @Json(name = "DividendYield")
    val dividendYield: Float, // 殖利率(%)
    @Json(name = "PBratio")
    val pbRatio: String, // 股價淨值比
)
