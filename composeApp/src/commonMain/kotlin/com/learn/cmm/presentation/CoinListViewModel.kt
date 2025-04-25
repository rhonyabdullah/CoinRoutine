package com.learn.cmm.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learn.cmm.core.domain.Result
import com.learn.cmm.domain.GetCoinListUseCase
import com.learn.cmm.domain.GetCoinPriceHistoryUseCase
import com.learn.cmm.utils.formatFiat
import com.learn.cmm.utils.formatPercentage
import com.learn.cmm.utils.toUiText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CoinListViewModel(
    private val getCoinListUseCase: GetCoinListUseCase,
    private val getCoinPriceHistoryUseCase: GetCoinPriceHistoryUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CoinState())
    val state = _state.onStart {
        getCoinList()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = CoinState()
    )

    fun onCoinLongPressed(coinId: String) {
        _state.update {
            it.copy(
                chartState = UiChartState(
                    sparkLine = emptyList(),
                    isLoading = true,
                )
            )
        }

        viewModelScope.launch {
            when(val priceHistory = getCoinPriceHistoryUseCase.execute(coinId)) {
                is Result.Success -> {
                    _state.update { currentState ->
                        currentState.copy(
                            chartState = UiChartState(
                                sparkLine = priceHistory.data.sortedBy { it.timestamp }.map { it.price },
                                isLoading = false,
                                coinName = _state.value.coins.find { it.id == coinId }?.name.orEmpty(),
                            )
                        )
                    }
                }
                is Result.Error -> {
                    _state.update { currentState ->
                        currentState.copy(
                            chartState = UiChartState(
                                sparkLine = emptyList(),
                                isLoading = false,
                                coinName = "",
                            )
                        )
                    }
                }
            }
        }
    }

    fun onDismissChart() {
        _state.update {
            it.copy(chartState = null)
        }
    }

    private suspend fun getCoinList() {
        when (val response = getCoinListUseCase.execute()) {
            is Result.Success -> _state.update {
                CoinState(
                    coins = response.data.map { item ->
                        UiCoinListItem(
                            id = item.coin.id,
                            name = item.coin.name,
                            iconUrl = item.coin.iconUrl,
                            symbol = item.coin.symbol,
                            formattedPrice = formatFiat(item.price),
                            formattedChange = formatPercentage(item.change),
                            isPositive = item.change >= 0,
                        )
                    }
                )
            }

            is Result.Error -> _state.update {
                it.copy(
                    coins = emptyList(),
                    error = response.error.toUiText()
                )
            }
        }
    }

}
