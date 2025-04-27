package com.iiaannppaann.bigtreehw.ui.main

import com.iiaannppaann.bigtreehw.ui.main.model.StockListItemUiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class MainUiState(
    val isStockListAscOrder: Boolean = true,
    val stockListItemUiModelList: ImmutableList<StockListItemUiModel> = persistentListOf(),
    val currentDialog: MainDialog? = null,
)

sealed interface MainDialog {
    data object StockSortOrderBottomSheet : MainDialog
    // TODO: can add error dialog
}

sealed interface MainUiAction {
    data object OnBurgerClick : MainUiAction

    data object OnDialogDismiss : MainUiAction

    data class OnStockItemClick(
        val stockId: String,
    ) : MainUiAction

    data class OnSortOrderItemClick(
        val isAscOrder: Boolean,
    ) : MainUiAction
}
