package hajong.qrcode.presentation.history

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hajong.qrcode.data.repository.QrHistoryRepository
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: QrHistoryRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow()

}