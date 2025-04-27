package com.iiaannppaann.bigtreehw.ui.main.model

import com.iiaannppaann.bigtreehw.domain.main.model.StockDetailInfoDomainData

data class StockDetailUiModel(
    val stockName: String,
    val stockId: String,
    val dividendYield: Float, // 殖利率(%)
    val peRatio: Float, // 本益比
    val pbRatio: Float, // 股價淨值比
)

fun StockDetailInfoDomainData.toUiModel(): StockDetailUiModel = StockDetailUiModel(
        stockName = stockName,
        stockId = stockId,
        dividendYield = dividendYield,
        peRatio = peRatio,
        pbRatio = pbRatio
    )
