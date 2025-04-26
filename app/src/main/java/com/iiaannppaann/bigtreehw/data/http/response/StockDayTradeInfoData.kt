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
    val tradeVolume: String,
    @Json(name = "TradeValue")
    val tradeValue: String,
    @Json(name = "OpeningPrice")
    val openingPrice: String,
    @Json(name = "HighestPrice")
    val highestPrice: String,
    @Json(name = "LowestPrice")
    val lowestPrice: String,
    @Json(name = "ClosingPrice")
    val closingPrice: String,
    @Json(name = "Change")
    val change: String,
    @Json(name = "Transaction")
    val transaction: String,
)
