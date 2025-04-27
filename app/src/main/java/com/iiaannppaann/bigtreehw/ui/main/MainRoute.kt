package com.iiaannppaann.bigtreehw.ui.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iiaannppaann.bigtreehw.R
import com.iiaannppaann.bigtreehw.ui.main.model.StockDetailUiModel
import com.iiaannppaann.bigtreehw.ui.main.model.StockListItemUiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch

@Composable
fun MainRoute(
    uiState: MainUiState,
    onAction: (MainUiAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    MainScreen(
        modifier = modifier,
        stockList = uiState.stockListItemUiModelList,
        currentDialog = uiState.currentDialog,
        loading = uiState.loading,
        onStockItemClick = { stockId ->
            onAction(MainUiAction.OnStockItemClick(stockId = stockId))
        },
        onDialogDismiss = {
            onAction(MainUiAction.OnDialogDismiss)
        },
        onStockSortOrderClick = { isAscOrder ->
            onAction(MainUiAction.OnSortOrderItemClick(isAscOrder = isAscOrder))
        },
    )
}

@Composable
private fun MainScreen(
    currentDialog: MainDialog?,
    stockList: ImmutableList<StockListItemUiModel>,
    loading: Boolean,
    onStockItemClick: (stockId: String) -> Unit,
    onStockSortOrderClick: (isAscOrder: Boolean) -> Unit,
    onDialogDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        LazyColumn(
            modifier = Modifier.background(Color.White),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(items = stockList, key = { it.stockId }) { item ->
                StockItem(
                    modifier = Modifier.clickable {
                        onStockItemClick(item.stockId)
                    },
                    uiModel = item
                )
            }
        }
        StockSortOrderBottomSheet(
            isVisible = currentDialog is MainDialog.StockSortOrderBottomSheet,
            onStockSortOrderClick = onStockSortOrderClick,
            onDialogDismiss = onDialogDismiss,
        )
        (currentDialog as? MainDialog.StockDetailDialog)?.stockDetailUiModel?.let { stockDetailUiModel ->
            StockDetailDialog(
                stockDetailUiModel = stockDetailUiModel,
                onDialogDismiss = onDialogDismiss,
            )
        }

        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(58.dp)
                    .align(Center)
            )
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StockSortOrderBottomSheet(
    isVisible: Boolean,
    onStockSortOrderClick: (isAscOrder: Boolean) -> Unit,
    onDialogDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        val bottomSheetState = rememberModalBottomSheetState()
        val coroutineScope = rememberCoroutineScope()
        LaunchedEffect(isVisible) {
            if (isVisible) {
                bottomSheetState.show()
            } else {
                bottomSheetState.hide()
            }
        }
        if (isVisible) {
            ModalBottomSheet(
                sheetState = bottomSheetState,
                onDismissRequest = onDialogDismiss,
            ) {
                Text(
                    modifier = Modifier
                        .clickable {
                            coroutineScope
                                .launch { bottomSheetState.hide() }
                                .invokeOnCompletion { onDialogDismiss() }
                            onStockSortOrderClick(false)
                        }.fillMaxWidth()
                        .padding(16.dp),
                    text = stringResource(R.string.stock_sort_with_desc_order),
                    textAlign = TextAlign.Center,
                )
                Spacer(
                    modifier =
                    Modifier
                        .padding(horizontal = 16.dp)
                        .height(2.dp)
                        .fillMaxWidth(),
                )
                Text(
                    modifier = Modifier
                        .clickable {
                            coroutineScope
                                .launch { bottomSheetState.hide() }
                                .invokeOnCompletion { onDialogDismiss() }
                            onStockSortOrderClick(true)
                        }.fillMaxWidth()
                        .padding(16.dp),
                    text = stringResource(R.string.stock_sort_with_asc_order),
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StockDetailDialog(
    stockDetailUiModel: StockDetailUiModel,
    onDialogDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        BasicAlertDialog(
            onDismissRequest = onDialogDismiss,
        ) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = CenterHorizontally,
            ) {
                Text(text = "${stockDetailUiModel.stockName} (${stockDetailUiModel.stockId})")
                Text(text = stringResource(R.string.pb_ratio_format, stockDetailUiModel.pbRatio))
                Text(text = stringResource(R.string.pe_ratio_format, stockDetailUiModel.peRatio))
                Text(text = stringResource(R.string.dividend_yield_format, stockDetailUiModel.dividendYield))

                Button(onClick = onDialogDismiss) {
                    Text(
                        modifier = Modifier
                            .width(100.dp)
                            .clip(CircleShape),
                        text = stringResource(R.string.confirm),
                        textAlign = TextAlign.Center,
                    )
                }
            }
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
        }.toPersistentList()
    MainScreen(
        stockList = dummyStockList,
        loading = false,
        currentDialog = null,
        onStockItemClick = {},
        onStockSortOrderClick = {},
        onDialogDismiss = {},
    )
}
