package hajong.qrcode.presentation.common

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Aim(
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite")
    val animatedValue by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "size"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val centerX = size.width / 2
            val centerY = size.height / 2
            val lineLength = 20.dp.toPx()
            val currentLength = lineLength * (1f + (animatedValue - 1f) * 0.5f)  // 스케일 효과 적용
            val strokeWidth = 2.dp.toPx()
            // val shadowOffset = 1.dp.toPx() // 그림자 오프셋
            
            /** 그림자 주석 시작
            drawLine(
                color = Color.Black.copy(alpha = 0.5f),
                start = Offset(centerX + shadowOffset, centerY - currentLength + shadowOffset),
                end = Offset(centerX + shadowOffset, centerY + currentLength + shadowOffset),
                strokeWidth = strokeWidth,
                cap = StrokeCap.Round
            )
            drawLine(
                color = Color.Black.copy(alpha = 0.5f),
                start = Offset(centerX - currentLength + shadowOffset, centerY + shadowOffset),
                end = Offset(centerX + currentLength + shadowOffset, centerY + shadowOffset),
                strokeWidth = strokeWidth,
                cap = StrokeCap.Round
            )
             그림자 주석 끝 **/


            // 수직선
            drawLine(
                color = Color.White,
                start = Offset(centerX, centerY - currentLength),
                end = Offset(centerX, centerY + currentLength),
                strokeWidth = strokeWidth,
                cap = StrokeCap.Round
            )

            // 수평선
            drawLine(
                color = Color.White,
                start = Offset(centerX - currentLength, centerY),
                end = Offset(centerX + currentLength, centerY),
                strokeWidth = strokeWidth,
                cap = StrokeCap.Round
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

