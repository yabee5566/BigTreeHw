package com.iiaannppaann.bigtreehw.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iiaannppaann.bigtreehw.R
import com.iiaannppaann.bigtreehw.domain.main.model.StockListItemDomainModel
import com.iiaannppaann.bigtreehw.domain.main.repo.StockInfoRepo
import com.iiaannppaann.bigtreehw.ui.main.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val stockInfoRepo: StockInfoRepo,
) : ViewModel() {
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        val currentDialog = MainDialog.ErrorDialog(msgResId = R.string.network_error)
        _uiState.update { it.copy(loading = false, currentDialog = currentDialog) }
        Log.d("MainViewModel", "Error occurred in ViewModel:$exception")
    }

    init {
        viewModelScope.launch(exceptionHandler) {
            _uiState
                .map { it.isStockListAscOrder }
                .distinctUntilChanged()
                .onStart { stockInfoRepo.invalidateStockInfoListCache() }
                .flatMapLatest { it ->
                    stockInfoRepo
                        .observeStockInfoMap()
                        .map { it.values.map(StockListItemDomainModel::toUiModel) }
                        .map {
                            if (_uiState.value.isStockListAscOrder) {
                                it.sortedBy { it.stockId }
                            } else {
                                it.sortedByDescending { it.stockId }
                            }.toPersistentList()
                        }
                }.collectLatest { stockListItemUiModelList ->
                    _uiState.update { it.copy(stockListItemUiModelList = stockListItemUiModelList) }
                }
        }
    }

    fun onUiAction(action: MainUiAction) {
        Log.d("MainViewModel", "onUiAction: $action")
        when (action) {
            MainUiAction.OnBurgerClick -> onTopBurgerClick()
            MainUiAction.OnDialogDismiss -> onDialogDismiss()
            is MainUiAction.OnSortOrderItemClick -> onSortOrderItemClick(action.isAscOrder)
            is MainUiAction.OnStockItemClick -> onStockItemClick(action.stockId)
            MainUiAction.OnPullRefresh -> onPullRefresh()
        }
    }

    fun onTopBurgerClick() {
        _uiState.update {
            it.copy(currentDialog = MainDialog.StockSortOrderBottomSheet)
        }
    }

    private fun onDialogDismiss() {
        _uiState.value = _uiState.value.copy(currentDialog = null)
    }

    private fun onSortOrderItemClick(isAscOrder: Boolean) {
        _uiState.update { it.copy(isStockListAscOrder = isAscOrder) }
    }

    private fun onStockItemClick(stockId: String) {
        viewModelScope.launch(exceptionHandler) {
            _uiState.update { it.copy(loading = true) }
            val stockDetailUiModel = stockInfoRepo.fetchStockDetailInfoDomainData(stockId = stockId)?.toUiModel()
            if (stockDetailUiModel == null) {
                _uiState.update {
                    val currentDialog = MainDialog.ErrorDialog(msgResId = R.string.no_stock_info)
                    it.copy(currentDialog = currentDialog, loading = false)
                }
                return@launch
            }

            _uiState.update {
                it.copy(
                    currentDialog = MainDialog.StockDetailDialog(stockDetailUiModel = stockDetailUiModel),
                    loading = false
                )
            }
        }
    }

    private fun onPullRefresh() {
        viewModelScope.launch(CoroutineExceptionHandler { _, exception ->
            _uiState.update { it.copy(isRefreshing = false) }
            Log.d("MainViewModel", "Error occurred in ViewModel:$exception")
        }) {
            _uiState.update { it.copy(isRefreshing = true) }
            stockInfoRepo.invalidateStockInfoListCache()
            _uiState.update { it.copy(isRefreshing = false) }
        }
    }
}
