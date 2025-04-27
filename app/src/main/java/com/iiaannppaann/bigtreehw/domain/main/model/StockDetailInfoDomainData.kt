package com.iiaannppaann.bigtreehw.domain.main.model

import com.iiaannppaann.bigtreehw.data.http.response.StockPEPBDividendYieldData

data class StockDetailInfoDomainData(
    val stockName: String,
    val stockId: String,
    val dividendYield: Float, // 殖利率(%)
    val peRatio: Float, // 本益比
    val pbRatio: Float, // 股價淨值比
)

fun StockPEPBDividendYieldData.toDomainModel(): StockDetailInfoDomainData = StockDetailInfoDomainData(
        stockName = name,
        stockId = code,
        dividendYield = dividendYield,
        peRatio = peRatio,
        pbRatio = pbRatio
    )
