package com.iiaannppaann.bigtreehw.data.http.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StockDayTradeInfoData(
    @Json(name = "Date")
    val date: String,
    @Json(name = "Code")
    val code: String,
    @Json(name = "Name")
    val name: String,
    @Json(name = "TradeVolume")
    val tradeVolume: Long,
    @Json(name = "TradeValue")
    val tradeValue: Long,
    @Json(name = "OpeningPrice")
    val openingPrice: Float,
    @Json(name = "HighestPrice")
    val highestPrice: Float,
    @Json(name = "LowestPrice")
    val lowestPrice: Float,
    @Json(name = "ClosingPrice")
    val closingPrice: Float,
    @Json(name = "Change")
    val change: Float,
    @Json(name = "Transaction")
    val transaction: Long,
)
