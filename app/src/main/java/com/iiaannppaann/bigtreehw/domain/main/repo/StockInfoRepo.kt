package com.iiaannppaann.bigtreehw.domain.main.repo

import android.util.Log
import com.iiaannppaann.bigtreehw.data.api.ExchangeReportApi
import com.iiaannppaann.bigtreehw.domain.main.model.StockDetailInfoDomainData
import com.iiaannppaann.bigtreehw.domain.main.model.StockListItemDomainModel
import com.iiaannppaann.bigtreehw.domain.main.model.toDomainModel
import com.iiaannppaann.bigtreehw.domain.main.model.toStockListItemDomainModel
import com.iiaannppaann.bigtreehw.domain.main.model.update
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

interface StockInfoRepo {
    suspend fun getStockInfoDomainModelList(isSortAsc: Boolean = true): List<StockListItemDomainModel>
    suspend fun getStockDetailInfoDomainData(stockId: String): StockDetailInfoDomainData?
}

@Singleton
class StockInfoRepoImpl @Inject constructor(
    private val exchangeReportApi: ExchangeReportApi,
) : StockInfoRepo {
    val stockListItemDomainModelMap = mutableMapOf<String, StockListItemDomainModel>()

    override suspend fun getStockInfoDomainModelList(isSortAsc: Boolean): List<StockListItemDomainModel> {
        // TODO: if error or null, use cache
        return coroutineScope {
            val dayTradeInfoDataListDeferred = async {
                exchangeReportApi.getAllStockDayTradeInfo()
            }
            val closingPriceAndMonthlyAvgDataListDeferred = async {
                exchangeReportApi.getAllStockClosingPriceAndMonthlyAvg()
            }

            for (data in dayTradeInfoDataListDeferred.await()) {
                stockListItemDomainModelMap[data.code] =
                    stockListItemDomainModelMap[data.code]?.update(data)
                        ?: data.toStockListItemDomainModel()
            }

            for (data in closingPriceAndMonthlyAvgDataListDeferred.await()) {
                stockListItemDomainModelMap[data.code] =
                    stockListItemDomainModelMap[data.code]?.update(data)
                        ?: data.toStockListItemDomainModel()
            }
            if (isSortAsc) {
                stockListItemDomainModelMap.values.sortedBy { it.stockId }.toList()
            } else {
                stockListItemDomainModelMap.values.sortedByDescending { it.stockId }.toList()
            }
        }
    }

    override suspend fun getStockDetailInfoDomainData(stockId: String): StockDetailInfoDomainData? {
        return exchangeReportApi
            .getAllStockPEPBAndDividendYield()
            .firstOrNull { it.code == stockId }
            ?.toDomainModel()
    }
}
