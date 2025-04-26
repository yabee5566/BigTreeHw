package com.iiaannppaann.bigtreehw.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iiaannppaann.bigtreehw.domain.main.repo.StockInfoRepo
import com.iiaannppaann.bigtreehw.ui.main.model.toStockListItemUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
    @Inject
    constructor(
        private val stockInfoRepo: StockInfoRepo,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(MainMviUiState())
        val uiState = _uiState.asStateFlow()

        init {
            viewModelScope.launch {
                val stockListItemUiModelList =
                    stockInfoRepo.getStockInfoDomainModelList().map {
                        it.toStockListItemUiModel()
                    }
                _uiState.value =
                    _uiState.value.copy(
                        stockListItemUiModelList = stockListItemUiModelList,
                    )
            }
        }
    }
