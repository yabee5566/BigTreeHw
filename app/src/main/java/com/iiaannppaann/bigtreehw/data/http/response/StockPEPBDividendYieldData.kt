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
    val peRatio: String,
    @Json(name = "DividendYield")
    val dividendYield: String,
    @Json(name = "PBratio")
    val pbRatio: String,
)
