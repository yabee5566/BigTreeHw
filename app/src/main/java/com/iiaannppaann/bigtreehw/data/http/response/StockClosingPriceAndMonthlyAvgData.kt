package com.iiaannppaann.bigtreehw.data.http.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StockClosingPriceAndMonthlyAvgData(
    @Json(name = "Date")
    val date: String,
    @Json(name = "Code")
    val code: String,
    @Json(name = "Name")
    val name: String,
    @Json(name = "ClosingPrice")
    val closingPrice: Float,
    @Json(name = "MonthlyAveragePrice")
    val monthlyAveragePrice: Float,
)
