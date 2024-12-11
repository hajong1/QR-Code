package hajong.qrcode.presentation.result

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(
    url: String,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Scan Result") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
//                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
        ) {
            Text(
                text = "RESULT"
            )
        }
//        AndroidView(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(padding),
//            factory = { context ->
//                WebView(context).apply {
//                    settings.javaScriptEnabled = true
//                    webViewClient = WebViewClient()
//                }
//            },
//            update = { webView ->
//                webView.loadUrl(url)
//            }
//        )
    }
}