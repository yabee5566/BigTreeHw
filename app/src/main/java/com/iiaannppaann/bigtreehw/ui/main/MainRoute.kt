package com.iiaannppaann.bigtreehw.ui.main

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        isRefreshing = uiState.isRefreshing,
        onStockItemClick = { stockId ->
            onAction(MainUiAction.OnStockItemClick(stockId = stockId))
        },
        onDialogDismiss = {
            onAction(MainUiAction.OnDialogDismiss)
        },
        onStockSortOrderClick = { isAscOrder ->
            onAction(MainUiAction.OnSortOrderItemClick(isAscOrder = isAscOrder))
        },
        onOnPullRefresh = {
            onAction(MainUiAction.OnPullRefresh)
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun MainScreen(
    currentDialog: MainDialog?,
    stockList: ImmutableList<StockListItemUiModel>,
    loading: Boolean,
    isRefreshing: Boolean,
    onStockItemClick: (stockId: String) -> Unit,
    onStockSortOrderClick: (isAscOrder: Boolean) -> Unit,
    onDialogDismiss: () -> Unit,
    onOnPullRefresh: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        val pullRefreshState = rememberPullRefreshState(
            refreshing = isRefreshing,
            onRefresh = onOnPullRefresh,
        )
        LazyColumn(
            modifier = Modifier
                .background(Color.White)
                .pullRefresh(pullRefreshState),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            if (stockList.isEmpty() && !isRefreshing) {
                item {
                    Text(
                        modifier = Modifier.fillParentMaxSize(),
                        text = "這裡什麼也沒有",
                        textAlign = TextAlign.Center
                    )
                }
            }
            items(items = stockList, key = { it.stockId }) { item ->
                StockItem(
                    modifier = Modifier.clickable {
                        onStockItemClick(item.stockId)
                    },
                    uiModel = item
                )
            }
        }
        PullRefreshIndicator(
            modifier = Modifier.align(Alignment.TopCenter),
            refreshing = isRefreshing,
            state = pullRefreshState
        )
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
        (currentDialog as? MainDialog.ErrorDialog)?.msgResId?.let { msgResId ->
            ErrorDialog(
                msgResId = msgResId,
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
        border = BorderStroke(width = 1.dp, color = Color.Gray),
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                text = stringResource(
                    R.string.stock_name_and_id_format,
                    uiModel.stockName, uiModel.stockId,
                ),
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
            )
            Spacer(modifier = Modifier.height(8.dp))
            StockItemCenterBlock(
                modifier = Modifier.fillMaxWidth(),
                openingPrice = uiModel.openingPrice,
                closingPrice = uiModel.closingPrice,
                closingPriceColor = uiModel.closingPriceColor,
                highPrice = uiModel.highPrice,
                lowPrice = uiModel.lowPrice,
                priceChange = uiModel.priceChange,
                priceChangeColor = uiModel.priceChangeColor,
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
    openingPrice: String,
    closingPrice: String,
    closingPriceColor: Color,
    highPrice: String,
    lowPrice: String,
    priceChange: String,
    priceChangeColor: Color,
    avgMonthlyPrice: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row {
            Row(modifier = Modifier.weight(1f)) {
                Text(text = "開盤價:")
                Text(text = openingPrice)
            }
            Row(modifier = Modifier.weight(1f)) {
                Text(text = "收盤價:")
                Text(text = closingPrice, color = closingPriceColor)
            }
        }
        Row {
            Row(modifier = Modifier.weight(1f)) {
                Text(text = "最高價:")
                Text(text = highPrice)
            }
            Row(modifier = Modifier.weight(1f)) {
                Text(text = "最低價:")
                Text(text = lowPrice)
            }
        }
        Row {
            Row(modifier = Modifier.weight(1f)) {
                Text(text = "漲跌價差:")
                Text(text = priceChange, color = priceChangeColor)
            }
            Row(modifier = Modifier.weight(1f)) {
                Text(text = "月平均價:")
                Text(text = avgMonthlyPrice)
            }
        }
    }
}

@Composable
private fun StockItemBottomBlock(
    tradeCount: String,
    volume: String,
    totalTradeValue: String,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()
    Row(
        modifier = modifier.horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = CenterVertically,
    ) {
        Text(text = "成交筆數:")
        Text(text = tradeCount)
        Text(text = "成交股數:")
        Text(text = volume)
        Text(text = "成交金額:")
        Text(text = totalTradeValue)
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
                Text(
                    text = stringResource(
                        R.string.stock_name_and_id_format,
                        stockDetailUiModel.stockName,
                        stockDetailUiModel.stockId,
                    )
                )
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ErrorDialog(
    @StringRes msgResId: Int,
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
                Text(text = stringResource(R.string.notice))
                Text(text = stringResource(msgResId))
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
        tradeCount = "11K",
        volume = "22M",
        totalTradeValue = "123",
    )
}

@Composable
@Preview
private fun StockItemCenterBlockPreview() {
    Box(
        modifier = Modifier.background(Color.White),
    ) {
        StockItemCenterBlock(
            openingPrice = "11.22",
            closingPrice = "11.22",
            highPrice = "11.22",
            lowPrice = "11.22",
            priceChange = "11.22",
            avgMonthlyPrice = "11.22",
            closingPriceColor = Color.Red,
            priceChangeColor = Color.Green,
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
            openingPrice = "11.22",
            closingPrice = "11.22",
            highPrice = "11.22",
            lowPrice = "11.22",
            priceChange = "11.22",
            avgMonthlyPrice = "11.22",
            tradeCount = "---",
            volume = "---",
            totalValueTraded = "1M",
            closingPriceColor = Color.Red,
            priceChangeColor = Color.Green,
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
                openingPrice = "11.22",
                closingPrice = "11.22",
                highPrice = "11.22",
                lowPrice = "11.22",
                priceChange = "11.22",
                avgMonthlyPrice = "11.22",
                tradeCount = "---",
                volume = "1K",
                totalValueTraded = "1M",
                closingPriceColor = Color.Red,
                priceChangeColor = Color.Green,
            )
        }.toPersistentList()
    MainScreen(
        stockList = dummyStockList,
        loading = false,
        isRefreshing = false,
        currentDialog = null,
        onStockItemClick = {},
        onStockSortOrderClick = {},
        onDialogDismiss = {},
        onOnPullRefresh = {},
    )
}
