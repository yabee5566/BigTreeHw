package com.iiaannppaann.bigtreehw.ui.main

import androidx.annotation.StringRes
import com.iiaannppaann.bigtreehw.ui.main.model.StockDetailUiModel
import com.iiaannppaann.bigtreehw.ui.main.model.StockListItemUiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class MainUiState(
    val loading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isStockListAscOrder: Boolean = true,
    val stockListItemUiModelList: ImmutableList<StockListItemUiModel> = persistentListOf(),
    val currentDialog: MainDialog? = null,
)

sealed interface MainDialog {
    data object StockSortOrderBottomSheet : MainDialog
    data class StockDetailDialog(
        val stockDetailUiModel: StockDetailUiModel
    ) : MainDialog

    data class ErrorDialog(
        @StringRes val msgResId: Int
    ) : MainDialog
}

sealed interface MainUiAction {
    data object OnBurgerClick : MainUiAction
    data object OnDialogDismiss : MainUiAction
    data class OnStockItemClick(
        val stockId: String
    ) : MainUiAction

    data class OnSortOrderItemClick(
        val isAscOrder: Boolean
    ) : MainUiAction

    data object OnPullRefresh : MainUiAction
}
