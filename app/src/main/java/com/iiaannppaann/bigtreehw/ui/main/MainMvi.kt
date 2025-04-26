package com.iiaannppaann.bigtreehw.ui.main

import com.iiaannppaann.bigtreehw.ui.main.model.StockListItemUiModel

data class MainMviUiState(
    val stockListItemUiModelList: List<StockListItemUiModel> = emptyList(),
)
