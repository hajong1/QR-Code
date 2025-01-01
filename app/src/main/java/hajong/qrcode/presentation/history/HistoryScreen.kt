package hajong.qrcode.presentation.history

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hajong.qrcode.presentation.common.InfinityLazyColumn

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    onBackClick: () -> Unit,
    onItemClick: (String) -> Unit,
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val scrollState = rememberLazyListState()

    val list = viewModel.historyList.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("내 기록") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, "Back")
                    }
                }
            )
        }
    ) { padding ->
        InfinityLazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            state = scrollState,
            loadMore = {
                Log.d("[지용]", "call loadMore")
            }
        ) {
            items(
                count = list.value.size,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column() {
                        Text(
                            text = "content : ${list.value[it].content}",
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(bottom = 4.dp)
                        )
                        Text(
                            text = "time : ${list.value[it].timestamp}",
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(bottom = 4.dp)
                        )
                    }
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete",
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .size(24.dp)
                    )
                }
            }
//            Column() {
//
//            }
//            items(
//                items = scanHistory,
//                key = { it.id }
//            ) {
//                Text(
//                    text = it.url,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(16.dp)
//                )
//            }
        }
    }
}