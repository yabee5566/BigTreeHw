package com.iiaannppaann.bigtreehw.ui.main.model

import com.iiaannppaann.bigtreehw.domain.main.model.StockListItemDomainModel

// TODO: use String and color for ui model rather than numbers
data class StockListItemUiModel(
    val stockId: String,
    val stockName: String,
    val openingPrice: Float,
    val closingPrice: Float,
    val highPrice: Float,
    val lowPrice: Float,
    val priceChange: Float,
    val avgMonthlyPrice: Float,
    val tradeCount: Float,
    val volume: Float,
    val totalValueTraded: Float,
)

fun StockListItemDomainModel.toStockListItemUiModel(): StockListItemUiModel =
    StockListItemUiModel(
        stockId = stockId,
        stockName = stockName,
        openingPrice = openingPrice ?: 0f,
        closingPrice = closingPrice ?: 0f,
        highPrice = highestPrice ?: 0f,
        lowPrice = lowestPrice ?: 0f,
        priceChange = priceChange ?: 0f,
        avgMonthlyPrice = avgMonthlyPrice ?: 0f,
        tradeCount = tradeCount?.toFloat() ?: 0F,
        volume = volume?.toFloat() ?: 0f,
        totalValueTraded = totalValueTraded?.toFloat() ?: 0f,
    )
