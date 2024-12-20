package hajong.qrcode.presentation.common

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun Aim(
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite")
    val animatedValue by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "size"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val scannerWidth = size.width * 0.7f
            val scannerHeight = size.height * 0.7f
            val centerX = size.width / 2
            val centerY = size.height / 2

            // 메인 스캐너 사각형
//            drawRect(
//                color = Color.White,
//                topLeft = Offset(
//                    centerX - scannerWidth / 2,
//                    centerY - scannerHeight / 2
//                ),
//                size = Size(scannerWidth, scannerHeight),
//                style = Stroke(width = 3.dp.toPx())
//            )

            // 모서리 강조
            val cornerLength = 20.dp.toPx()
            val corners = listOf(
                // 왼쪽 위
                Pair(
                    Offset(centerX - scannerWidth / 2, centerY - scannerHeight / 2),
                    listOf(
                        Offset(cornerLength, 0f),
                        Offset(0f, cornerLength)
                    )
                ),
                // 오른쪽 위
                Pair(
                    Offset(centerX + scannerWidth / 2, centerY - scannerHeight / 2),
                    listOf(
                        Offset(-cornerLength, 0f),
                        Offset(0f, cornerLength)
                    )
                ),
                // 왼쪽 아래
                Pair(
                    Offset(centerX - scannerWidth / 2, centerY + scannerHeight / 2),
                    listOf(
                        Offset(cornerLength, 0f),
                        Offset(0f, -cornerLength)
                    )
                ),
                // 오른쪽 아래
                Pair(
                    Offset(centerX + scannerWidth / 2, centerY + scannerHeight / 2),
                    listOf(
                        Offset(-cornerLength, 0f),
                        Offset(0f, -cornerLength)
                    )
                )
            )

            corners.forEach { (point, lines) ->
                lines.forEach { line ->
                    drawLine(
                        color = Color.White,
                        start = point,
                        end = point.plus(line),
                        strokeWidth = 5.dp.toPx()
                    )
                }
            }

            // 애니메이션 효과
            val animValue = animatedValue
            val expandedWidth = scannerWidth + (50.dp.toPx() * animValue)
            val expandedHeight = scannerHeight + (50.dp.toPx() * animValue)

            drawRect(
                color = Color.White.copy(alpha = 1f - animValue),
                topLeft = Offset(
                    centerX - expandedWidth / 2,
                    centerY - expandedHeight / 2
                ),
                size = Size(expandedWidth, expandedHeight),
                style = Stroke(width = 2.dp.toPx())
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AimPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Aim(
            modifier = Modifier
                .size(300.dp)
        )
    }

}

