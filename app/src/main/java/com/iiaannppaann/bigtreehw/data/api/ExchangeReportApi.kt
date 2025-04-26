package com.iiaannppaann.bigtreehw.data.api

import com.iiaannppaann.bigtreehw.data.http.response.StockClosingPriceAndMonthlyAvgData
import com.iiaannppaann.bigtreehw.data.http.response.StockDayTradeInfoData
import com.iiaannppaann.bigtreehw.data.http.response.StockPEPBDividendYieldData
import retrofit2.http.GET

interface ExchangeReportApi {
    @GET("v1/exchangeReport/BWIBBU_ALL")
    suspend fun getAllStockPEPBAndDividendYield(): List<StockPEPBDividendYieldData>

    @GET("v1/exchangeReport/STOCK_DAY_AVG_ALL")
    suspend fun getAllStockClosingPriceAndMonthlyAvg(): List<StockClosingPriceAndMonthlyAvgData>

    @GET("v1/exchangeReport/STOCK_DAY_ALL")
    suspend fun getAllStockDayTradeInfo(): List<StockDayTradeInfoData>
}
