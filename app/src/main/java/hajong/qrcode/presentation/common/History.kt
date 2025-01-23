package hajong.qrcode.presentation.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

@Composable
fun History(
    modifier: Modifier = Modifier,
    image: String = "",
    link: String = "",
    time: String = "",
    onClick: () -> Unit = {},
    onClickDelete: () -> Unit = {},
) {
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp)
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(12.dp)
            .clickable (
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
//        AsyncImage(
//            modifier = Modifier
//                .size(24.dp)
//                .clip(RoundedCornerShape(8.dp))
//                .border(
//                    width = (0.5).dp,
//                    color = MaterialTheme.colorScheme.outline,
//                    shape = RoundedCornerShape(8.dp),
//                ),
//            model = image,
//            contentScale = ContentScale.Crop,
//            contentDescription = null,
//        )
        Column(
            modifier = Modifier
                .weight(6f)
        ) {
            Text(
                text = link,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(bottom = 4.dp)
            )
            Text(
                text = time,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(bottom = 4.dp)
            )
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically),
            contentAlignment = Alignment.CenterEnd
        ) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "Delete",
                modifier = Modifier
                    .size(32.dp)
                    .padding(4.dp)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = onClickDelete
                    )
            )
        }
    }
}

@Composable
@Preview(showBackground = false, showSystemUi = false)
fun HistoryPreview() {
    History(
        image = "",
        link = "www.naver.com",
        time = "2025-03-01 12:00:00",
        onClick = {},
        onClickDelete = { },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    )
}