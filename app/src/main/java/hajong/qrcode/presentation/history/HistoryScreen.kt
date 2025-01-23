package hajong.qrcode.presentation.history

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hajong.qrcode.presentation.common.History
import hajong.qrcode.presentation.common.InfinityLazyColumn

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    onBackClick: () -> Unit,
    onItemClick: (String) -> Unit,
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val scrollState = rememberLazyListState()
    val interactionSource = remember { MutableInteractionSource() }

    val uiState = viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("내 기록") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, "Back")
                    }
                },
//                actions = {
//                    Text(
//                        text = "전체삭제",
//                        modifier = Modifier
//                            .clickable (
//                                interactionSource = interactionSource,
//                                indication = null,
//                                onClick = { viewModel.deleteAllHistory() }
//                            )
//                    )
//                }
            )
        }
    ) { padding ->
        InfinityLazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEFF1F3))
                .padding(padding),
            contentPadding = PaddingValues(vertical = 12.dp),
            state = scrollState,
            loadMore = { viewModel.loadMoreHistories() }
        ) {
            itemsIndexed(
                items = uiState.value.histories,
            ) { index, item ->
                History(
                    image = item.id.toString(),
                    link = item.content,
                    time = item.timestamp.toString(),
                    onClick = { onItemClick(item.content) },
                    onClickDelete = { viewModel.deleteHistory(item.id) },
                    modifier = Modifier,
                )
            }
        }
    }
}