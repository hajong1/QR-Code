package hajong.qrcode.presentation.history

import hajong.qrcode.data.domain.QrHistory

// 화면에 표시될 상태만을 담을 클래스
data class HistoryUiState(
    val histories: List<QrHistory> = emptyList(),
    val totalItems: Int = 0, // 총 아이템 수
    val isLoading: Boolean = false, // 로딩 상태
    val errorMessage: String? = null // 에러 메시지 (보류)
)
