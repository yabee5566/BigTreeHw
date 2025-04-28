package com.iiaannppaann.bigtreehw.domain.main.repo

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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

interface StockInfoRepo {
    suspend fun invalidateStockInfoListCache()
    fun observeStockInfoMap(): Flow<Map<String, StockListItemDomainModel>>
    suspend fun invalidateStockDetailInfoCache()
    suspend fun fetchStockDetailInfoDomainData(stockId: String): StockDetailInfoDomainData?
}

@Singleton
class StockInfoRepoImpl @Inject constructor(
    private val exchangeReportApi: ExchangeReportApi,
) : StockInfoRepo {
    private val stockListItemMapFlow = MutableStateFlow<Map<String, StockListItemDomainModel>>(emptyMap())
    private val stockDetailInfoMap = MutableStateFlow<Map<String, StockDetailInfoDomainData>>(emptyMap())

    override suspend fun invalidateStockInfoListCache() {
        coroutineScope {
            val stockListItemDomainModelMap = mutableMapOf<String, StockListItemDomainModel>()
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
            stockListItemMapFlow.value = stockListItemDomainModelMap
        }
    }

    override fun observeStockInfoMap(): Flow<Map<String, StockListItemDomainModel>> =
        stockListItemMapFlow.asStateFlow()

    override suspend fun invalidateStockDetailInfoCache() {
        val stockDetailInfoDomainDataMap = exchangeReportApi
            .getAllStockPEPBAndDividendYield()
            .associateBy { it.code }
            .mapValues { it.value.toDomainModel() }
        stockDetailInfoMap.value = stockDetailInfoDomainDataMap
    }

    override suspend fun fetchStockDetailInfoDomainData(stockId: String): StockDetailInfoDomainData? {
        val stockDetailInfo = stockDetailInfoMap.value[stockId]
        if (stockDetailInfo == null) {
            invalidateStockDetailInfoCache()
            return stockDetailInfoMap.value[stockId]
        }
        return stockDetailInfo
    }
}
