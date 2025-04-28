package com.iiaannppaann.bigtreehw.ui.main.model

import com.iiaannppaann.bigtreehw.core.util.toFormattedString
import com.iiaannppaann.bigtreehw.domain.main.model.StockDetailInfoDomainData

data class StockDetailUiModel(
    val stockName: String,
    val stockId: String,
    val dividendYield: String, // 殖利率(%)
    val peRatio: String, // 本益比
    val pbRatio: String, // 股價淨值比
)

fun StockDetailInfoDomainData.toUiModel(): StockDetailUiModel = StockDetailUiModel(
        stockName = stockName,
        stockId = stockId,
        dividendYield = dividendYield.toFormattedString(),
        peRatio = peRatio.toFormattedString(),
        pbRatio = pbRatio.toFormattedString()
    )
