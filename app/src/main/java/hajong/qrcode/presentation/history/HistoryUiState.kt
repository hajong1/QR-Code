package hajong.qrcode.presentation.history

import hajong.qrcode.data.domain.QrHistory

data class HistoryUiState(
    val histories: List<QrHistory> = emptyList(), // QrHistory를 정의 해야 함
    val currentPage: Int = 1,
    val totalPages: Int = 1,
    val totalItems: Int = 0,
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val hasNextPage: Boolean = false,
    val errorMessage: String? = null
)
