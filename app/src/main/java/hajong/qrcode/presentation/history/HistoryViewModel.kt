package hajong.qrcode.presentation.history

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hajong.qrcode.data.repository.QrHistoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: QrHistoryRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HistoryUiState())
    val uiState: StateFlow<HistoryUiState> = _uiState.asStateFlow()

    private var page = 1
    private var hasNext: Boolean = false

    init {
        viewModelScope.launch {
            repository.observeHistory(
                onStart = { _uiState.update { it.copy(isLoading = true) } },
                onComplete = { _uiState.update { it.copy(isLoading = false) } },
                onError = { e ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                        )
                    }
                }
            ).collect { histories ->
                Log.d("[지용]", "collect")
                _uiState.update { currentHistories ->
                    val currentList = currentHistories.histories
                    val updatedList = if (currentList.isEmpty()) {
                        histories.take(page * PageSize)
                    } else {
                        // 유지하면서 반영
                        currentList.filter { item ->
                            histories.any { it.id == item.id }
                        }
                    }
                    currentHistories.copy(histories = updatedList)
                }
            }
        }

        // load
        initHistories()
    }

    private fun initHistories() {
        Log.d("[지용]", "initHistories")
        page = 1
        hasNext = true

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val initialHistories = repository.fetchHistory(
                page = page,
                pageSize = PageSize,
            )

            page++
            hasNext = initialHistories.size == PageSize

            _uiState.update {
                it.copy(
                    histories = initialHistories,
                    isLoading = false,
                    totalItems = initialHistories.size,
                )
            }
        }

    }

    fun loadMoreHistories() {
        Log.d("[지용]", "loadMoreHistories")
        if (!hasNext || _uiState.value.isLoading) return

        viewModelScope.launch {
            Log.d("[지용]", "loadMoreHistories launch")
            _uiState.update { it.copy(isLoading = true) }

            val newHistories = repository.fetchHistory(
                page = page,
                pageSize = PageSize,
            )

            page++
            hasNext = newHistories.size == PageSize

            _uiState.update { currentHistories ->
                currentHistories.copy(
                    histories = currentHistories.histories + newHistories,
                    totalItems = currentHistories.totalItems + newHistories.size,
                    isLoading = false,
                )
            }
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun deleteHistory(id: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            repository.deleteHistory(id)

            _uiState.update { it.copy(isLoading = false) }
        }
    }

    companion object {
        private const val PageSize = 10
    }
}