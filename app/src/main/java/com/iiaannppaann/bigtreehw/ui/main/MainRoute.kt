package com.iiaannppaann.bigtreehw.ui.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iiaannppaann.bigtreehw.ui.main.model.StockListItemUiModel

@Composable
fun MainRoute(modifier: Modifier = Modifier) {
    val dummyStockList =
        List(20) {
            StockListItemUiModel(
                stockId = "233$it",
                stockName = "台積電",
                openingPrice = 1f,
                closingPrice = 2f,
                highPrice = 3f,
                lowPrice = 4f,
                priceChange = 5f,
                avgMonthlyPrice = 6f,
                tradeCount = 7f,
                volume = 8f,
                totalValueTraded = 9f,
            )
        }
    MainScreen(
        stockList = dummyStockList,
        modifier = modifier,
    )
}

@Composable
fun MainScreen(
    stockList: List<StockListItemUiModel>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.background(Color.White),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            items = stockList,
            key = { it.stockId },
        ) { item ->
            StockItem(uiModel = item)
        }
    }
}

@Composable
private fun StockItem(
    uiModel: StockListItemUiModel,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        border = BorderStroke(width = 1.dp, color = Color.Black),
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(text = uiModel.stockId)
            Text(text = uiModel.stockName)
            StockItemCenterBlock(
                modifier = Modifier.fillMaxWidth(),
                openingPrice = uiModel.openingPrice,
                closingPrice = uiModel.closingPrice,
                highPrice = uiModel.highPrice,
                lowPrice = uiModel.lowPrice,
                priceChange = uiModel.priceChange,
                avgMonthlyPrice = uiModel.avgMonthlyPrice,
            )
            Spacer(modifier = Modifier.height(8.dp))
            StockItemBottomBlock(
                modifier = Modifier.fillMaxWidth(),
                tradeCount = uiModel.tradeCount,
                volume = uiModel.volume,
                totalTradeValue = uiModel.totalValueTraded,
            )
        }
    }
}

@Composable
private fun StockItemCenterBlock(
    openingPrice: Float,
    closingPrice: Float,
    highPrice: Float,
    lowPrice: Float,
    priceChange: Float,
    avgMonthlyPrice: Float,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row {
            Row(modifier = Modifier.weight(1f)) {
                Text(text = "開盤價:")
                Text(text = "$openingPrice")
            }
            Row(modifier = Modifier.weight(1f)) {
                Text(text = "收盤價:")
                Text(text = "$closingPrice")
            }
        }
        Row {
            Row(modifier = Modifier.weight(1f)) {
                Text(text = "最高價:")
                Text(text = "$highPrice")
            }
            Row(modifier = Modifier.weight(1f)) {
                Text(text = "最低價:")
                Text(text = "$lowPrice")
            }
        }
        Row {
            Row(modifier = Modifier.weight(1f)) {
                Text(text = "漲跌價差:")
                Text(text = "$priceChange")
            }
            Row(modifier = Modifier.weight(1f)) {
                Text(text = "月平均價:")
                Text(text = "$avgMonthlyPrice")
            }
        }
    }
}

@Composable
private fun StockItemBottomBlock(
    tradeCount: Float,
    volume: Float,
    totalTradeValue: Float,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = CenterVertically,
        ) {
            Text(text = "成交筆數:")
            Text(text = "$tradeCount")
        }
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = CenterVertically,
        ) {
            Text(text = "成交股數:")
            Text(text = "$volume")
        }
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = CenterVertically,
        ) {
            Text(text = "成交金額:")
            Text(text = "$totalTradeValue")
        }
    }
}

@Preview
@Composable
private fun StockItemBottomBlockPreview() {
    StockItemBottomBlock(
        modifier = Modifier.background(Color.White),
        tradeCount = 1f,
        volume = 2f,
        totalTradeValue = 3f,
    )
}

@Composable
@Preview
private fun StockItemCenterBlockPreview() {
    Box(
        modifier = Modifier.background(Color.White),
    ) {
        StockItemCenterBlock(
            openingPrice = 1f,
            closingPrice = 2f,
            highPrice = 3f,
            lowPrice = 4f,
            priceChange = 5f,
            avgMonthlyPrice = 6f,
            modifier = Modifier,
        )
    }
}

@Preview
@Composable
private fun StockItemPreview() {
    StockItem(
        uiModel =
            StockListItemUiModel(
                stockId = "2330",
                stockName = "台積電",
                openingPrice = 1f,
                closingPrice = 2f,
                highPrice = 3f,
                lowPrice = 4f,
                priceChange = 5f,
                avgMonthlyPrice = 6f,
                tradeCount = 7f,
                volume = 8f,
                totalValueTraded = 9f,
            ),
    )
}

@Preview
@Composable
private fun MainScreenPreview() {
    val dummyStockList =
        List(20) {
            StockListItemUiModel(
                stockId = "233$it",
                stockName = "台積電",
                openingPrice = 1f,
                closingPrice = 2f,
                highPrice = 3f,
                lowPrice = 4f,
                priceChange = 5f,
                avgMonthlyPrice = 6f,
                tradeCount = 7f,
                volume = 8f,
                totalValueTraded = 9f,
            )
        }
    MainScreen(
        stockList = dummyStockList,
    )
}
