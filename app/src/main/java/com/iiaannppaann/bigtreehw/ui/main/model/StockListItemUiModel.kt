package com.iiaannppaann.bigtreehw.ui.main.model

// TODO: check if the type is applicable for stock data, too short? or too long?
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
