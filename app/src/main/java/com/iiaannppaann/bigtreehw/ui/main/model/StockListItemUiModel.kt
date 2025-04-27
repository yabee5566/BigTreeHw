package com.iiaannppaann.bigtreehw.ui.main.model

import androidx.compose.ui.graphics.Color
import com.iiaannppaann.bigtreehw.domain.main.model.StockListItemDomainModel

// TODO: use String and color for ui model rather than numbers
data class StockListItemUiModel(
    val stockId: String,
    val stockName: String,
    val openingPrice: Float,
    val closingPrice: Float,
    val closingPriceColor: Color,
    val highPrice: Float,
    val lowPrice: Float,
    val priceChange: Float,
    val priceChangeColor: Color,
    val avgMonthlyPrice: Float,
    val tradeCount: Float,
    val volume: Float,
    val totalValueTraded: Float,
)

fun StockListItemDomainModel.toUiModel(): StockListItemUiModel =
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
        closingPriceColor = when {
            (closingPrice == null || avgMonthlyPrice == null) -> Color.Gray
            closingPrice > avgMonthlyPrice -> Color.Red
            closingPrice < avgMonthlyPrice -> Color.Green
            else -> Color.Gray
        },
        priceChangeColor = when {
            priceChange == null -> Color.Gray
            priceChange > 0 -> Color.Red
            priceChange < 0 -> Color.Green
            else -> Color.Gray
        },
    )
