package hajong.qrcode.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hajong.qrcode.data.domain.QrHistory
import hajong.qrcode.data.repository.QrHistoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: QrHistoryRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HistoryUiState())
    val uiState: StateFlow<HistoryUiState> = _uiState.asStateFlow()

    private val pageSize = 10

    private val historyFetchingIndex: MutableStateFlow<Int> = MutableStateFlow(0)
    val historyList: StateFlow<List<QrHistory>> = historyFetchingIndex.flatMapLatest { page ->
        repository.fetchHistory(
            page = page,
            pageSize = pageSize,
            onStart = { _uiState.update { it.copy(isLoading = true) } },
            onComplete = { _uiState.update { it.copy(isLoading = false) } },
            onError = { e ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e
                    )
                }
            }
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    fun loadMoreHistories() {
        if (!_uiState.value.isLoading) {
            historyFetchingIndex.value++
        }
    }

//    private fun loadHistories() {
//        if (_uiState.value.isLoading) return
//
//        viewModelScope.launch {
//            _uiState.update { it.copy(isLoading = true) }
//
//            repository.fetchHistory(_uiState.value.currentPage, pageSize)
//                .catch { e ->
//                    _uiState.update {
//                        it.copy(
//                            isLoading = false,
//                            errorMessage = e.message
//                        )
//                    }
//                }
//                .collect { histories ->
//                    _uiState.update {
//                        it.copy(
//                            histories = if (it.currentPage == 1) histories else it.histories + histories,
//                            isLoading = false,
//                            hasNextPage = histories.isNotEmpty()
//                        )
//                    }
//                }
//        }
//    }
}