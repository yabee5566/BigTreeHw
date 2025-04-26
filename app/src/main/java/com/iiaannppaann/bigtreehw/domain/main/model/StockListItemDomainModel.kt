package com.iiaannppaann.bigtreehw.domain.main.model

import com.iiaannppaann.bigtreehw.data.http.response.StockClosingPriceAndMonthlyAvgData
import com.iiaannppaann.bigtreehw.data.http.response.StockDayTradeInfoData

data class StockListItemDomainModel(
    val stockId: String,
    val stockName: String,
    val openingPrice: Float?,
    val closingPrice: Float?,
    val highestPrice: Float?,
    val lowestPrice: Float?,
    val priceChange: Float?,
    val avgMonthlyPrice: Float?,
    val tradeCount: Long?,
    val volume: Long?,
    val totalValueTraded: Long?,
)

fun StockListItemDomainModel.update(data: StockClosingPriceAndMonthlyAvgData): StockListItemDomainModel =
    this.copy(
        closingPrice = data.closingPrice,
        avgMonthlyPrice = data.monthlyAveragePrice,
    )

fun StockListItemDomainModel.update(data: StockDayTradeInfoData): StockListItemDomainModel =
    this.copy(
        openingPrice = data.openingPrice,
        highestPrice = data.highestPrice,
        lowestPrice = data.lowestPrice,
        priceChange = data.change,
        tradeCount = data.transaction,
        volume = data.tradeVolume,
        totalValueTraded = data.tradeValue,
    )

fun StockClosingPriceAndMonthlyAvgData.toStockListItemDomainModel(): StockListItemDomainModel =
    StockListItemDomainModel(
        stockId = code,
        stockName = name,
        openingPrice = null,
        closingPrice = closingPrice,
        highestPrice = null,
        lowestPrice = null,
        priceChange = null,
        avgMonthlyPrice = monthlyAveragePrice,
        tradeCount = null,
        volume = null,
        totalValueTraded = null,
    )

fun StockDayTradeInfoData.toStockListItemDomainModel(): StockListItemDomainModel =
    StockListItemDomainModel(
        stockId = code,
        stockName = name,
        openingPrice = openingPrice,
        closingPrice = closingPrice,
        highestPrice = highestPrice,
        lowestPrice = lowestPrice,
        priceChange = change,
        avgMonthlyPrice = null,
        tradeCount = transaction,
        volume = tradeVolume,
        totalValueTraded = tradeValue,
    )
