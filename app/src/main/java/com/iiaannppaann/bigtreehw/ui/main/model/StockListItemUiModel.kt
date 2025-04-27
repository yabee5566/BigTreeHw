package com.iiaannppaann.bigtreehw.ui.main.model

import androidx.compose.ui.graphics.Color
import com.iiaannppaann.bigtreehw.domain.main.model.StockListItemDomainModel
import com.iiaannppaann.bigtreehw.ui.main.model.StockListItemUiModel.Companion.NO_DATA_DISPLAY_TEXT
import java.util.Locale

data class StockListItemUiModel(
    val stockId: String,
    val stockName: String,
    val openingPrice: String,
    val closingPrice: String,
    val closingPriceColor: Color,
    val highPrice: String,
    val lowPrice: String,
    val priceChange: String,
    val priceChangeColor: Color,
    val avgMonthlyPrice: String,
    val tradeCount: String,
    val volume: String,
    val totalValueTraded: String,
) {
    companion object {
        const val NO_DATA_DISPLAY_TEXT = "----"
    }
}

fun StockListItemDomainModel.toUiModel(): StockListItemUiModel =
    StockListItemUiModel(
        stockId = stockId,
        stockName = stockName,
        openingPrice = openingPrice?.toFormattedString() ?: NO_DATA_DISPLAY_TEXT,
        closingPrice = closingPrice?.toFormattedString() ?: NO_DATA_DISPLAY_TEXT,
        highPrice = highestPrice?.toFormattedString() ?: NO_DATA_DISPLAY_TEXT,
        lowPrice = lowestPrice?.toFormattedString() ?: NO_DATA_DISPLAY_TEXT,
        priceChange = priceChange?.toFormattedString() ?: NO_DATA_DISPLAY_TEXT,
        avgMonthlyPrice = avgMonthlyPrice?.toFormattedString() ?: NO_DATA_DISPLAY_TEXT,
        tradeCount = tradeCount?.toFormattedString() ?: NO_DATA_DISPLAY_TEXT,
        volume = volume?.toFormattedString() ?: NO_DATA_DISPLAY_TEXT,
        totalValueTraded = totalValueTraded?.toFormattedString() ?: NO_DATA_DISPLAY_TEXT,
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

fun Long.toFormattedString(): String =
    when {
        this > 1_000_000 -> {
            String.format(locale = Locale.US, format = "%.2fM", this / 1_000_000f)
        }

        this > 1_000 -> {
            String.format(locale = Locale.US, format = "%.2fK", this / 1_000f)
        }

        else -> {
            this.toString()
        }
    }

fun Float.toFormattedString(): String = String.format(Locale.US, "%.2f", this)
