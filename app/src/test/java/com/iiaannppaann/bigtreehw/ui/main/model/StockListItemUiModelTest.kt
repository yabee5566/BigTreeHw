package com.iiaannppaann.bigtreehw.ui.main.model

import androidx.compose.ui.graphics.Color
import com.iiaannppaann.bigtreehw.domain.main.model.StockListItemDomainModel
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class StockListItemUiModeTest {
    @Test
    fun `StockListItemDomainModel to StockListItemUiModel`() {
        val domainModel = StockListItemDomainModel(
            stockId = "2330",
            stockName = "TSMC",
            openingPrice = 150.0f,
            closingPrice = 155.0f,
            highestPrice = 157.0f,
            lowestPrice = 148.0f,
            priceChange = 5.0f,
            avgMonthlyPrice = 152.0f,
            tradeCount = 1000L,
            volume = 50000L,
            totalValueTraded = 7500000L
        )

        val uiModel = domainModel.toUiModel()
        assertEquals(domainModel.stockId, uiModel.stockId)
        assertEquals(domainModel.stockName, uiModel.stockName)
        assertEquals("150.00", uiModel.openingPrice)
        assertEquals("155.00", uiModel.closingPrice)
        assertEquals("157.00", uiModel.highPrice)
        assertEquals("148.00", uiModel.lowPrice)
        assertEquals("5.00", uiModel.priceChange)
        assertEquals("152.00", uiModel.avgMonthlyPrice)
        assertEquals("1000", uiModel.tradeCount)
        assertEquals("50.00K", uiModel.volume)
        assertEquals("7.50M", uiModel.totalValueTraded)
    }

    @Test
    fun `StockListItemDomainModel to StockListItemUiModel with null values`() {
        val domainModel = StockListItemDomainModel(
            stockId = "2330",
            stockName = "TSMC",
            openingPrice = null,
            closingPrice = null,
            highestPrice = null,
            lowestPrice = null,
            priceChange = null,
            avgMonthlyPrice = null,
            tradeCount = null,
            volume = null,
            totalValueTraded = null
        )
        val uiModel = domainModel.toUiModel()
        assertEquals(domainModel.stockId, uiModel.stockId)
        assertEquals(domainModel.stockName, uiModel.stockName)
        assertEquals(StockListItemUiModel.NO_DATA_DISPLAY_TEXT, uiModel.openingPrice)
        assertEquals(StockListItemUiModel.NO_DATA_DISPLAY_TEXT, uiModel.closingPrice)
        assertEquals(StockListItemUiModel.NO_DATA_DISPLAY_TEXT, uiModel.highPrice)
        assertEquals(StockListItemUiModel.NO_DATA_DISPLAY_TEXT, uiModel.lowPrice)
        assertEquals(StockListItemUiModel.NO_DATA_DISPLAY_TEXT, uiModel.priceChange)
        assertEquals(StockListItemUiModel.NO_DATA_DISPLAY_TEXT, uiModel.avgMonthlyPrice)
        assertEquals(StockListItemUiModel.NO_DATA_DISPLAY_TEXT, uiModel.tradeCount)
        assertEquals(StockListItemUiModel.NO_DATA_DISPLAY_TEXT, uiModel.volume)
        assertEquals(StockListItemUiModel.NO_DATA_DISPLAY_TEXT, uiModel.totalValueTraded)
    }

    @Test
    fun `StockListItemUiModel text color logic`() {
        val domainModel = StockListItemDomainModel(
            stockId = "2330",
            stockName = "TSMC",
            openingPrice = null,
            closingPrice = null,
            highestPrice = null,
            lowestPrice = null,
            priceChange = null,
            avgMonthlyPrice = null,
            tradeCount = null,
            volume = null,
            totalValueTraded = null
        )
        val uiModel = domainModel.toUiModel()
        // all null case
        assertEquals(Color.Gray, uiModel.priceChangeColor)
        assertEquals(Color.Gray, uiModel.closingPriceColor)
        // closingPrice or avgMonthlyPrice is null
        domainModel
            .copy(
            closingPrice = null,
            avgMonthlyPrice = 100.0f
        ).toUiModel()
            .let {
            assertEquals(Color.Gray, it.closingPriceColor)
        }
        domainModel
            .copy(
            closingPrice = 100.0f,
            avgMonthlyPrice = null
        ).toUiModel()
            .let {
            assertEquals(Color.Gray, it.closingPriceColor)
        }
        // closingPrice and avgMonthlyPrice are not null
        domainModel
            .copy(
            closingPrice = 200.0f,
            avgMonthlyPrice = 100.0f
        ).toUiModel()
            .let {
            assertEquals(Color.Red, it.closingPriceColor)
        }
        domainModel
            .copy(
            closingPrice = 100.0f,
            avgMonthlyPrice = 200.0f
        ).toUiModel()
            .let {
            assertEquals(Color.Green, it.closingPriceColor)
        }
        domainModel
            .copy(
            closingPrice = 100.0f,
            avgMonthlyPrice = 100.0f
        ).toUiModel()
            .let {
            assertEquals(Color.Gray, it.closingPriceColor)
        }
        // priceChange is not null
        domainModel
            .copy(
            priceChange = 10.0f
        ).toUiModel()
            .let {
            assertEquals(Color.Red, it.priceChangeColor)
        }
        domainModel
            .copy(
            priceChange = -10.0f
        ).toUiModel()
            .let {
            assertEquals(Color.Green, it.priceChangeColor)
        }
        domainModel
            .copy(
            priceChange = 0.0f
        ).toUiModel()
            .let {
            assertEquals(Color.Gray, it.priceChangeColor)
        }
    }
}
