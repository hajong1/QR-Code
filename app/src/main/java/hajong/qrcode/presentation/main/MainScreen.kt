package hajong.qrcode.presentation.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen(
    onHistoryClick: () -> Unit,
    onScanResult: (String) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // QR Scanner camera preview
//        QRScannerPreview(
//            onScanResult = onScanResult
//        )

        // History button overlay
        SmallFloatingActionButton(
            onClick = onHistoryClick,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopEnd)
        ) {
//            Icon(
//                imageVector = Icons.Default.History,
//                contentDescription = "History"
//            )
        }
    }
}
