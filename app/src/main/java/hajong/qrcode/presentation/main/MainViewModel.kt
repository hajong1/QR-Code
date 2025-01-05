package hajong.qrcode.presentation.main

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
class MainViewModel @Inject constructor(
    private val repository: QrHistoryRepository
) : ViewModel() {

    fun addQrHistory(content :String): Long? {
        var insertedId: Long? = null

        viewModelScope.launch {
            try {
                insertedId = repository.insertHistory(content)
            } catch (e: Exception) {

            }
        }
        return insertedId
    }
}