package ir.dorantech

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CircleImage(
    url: String,
    size: Dp = 100.dp,
    contentDescription: String? = null,
    onClick: () -> Unit = {}
) {
    val isLoading = remember { mutableStateOf(true) }

    val painter = rememberAsyncImagePainter(
        model = url,
        onLoading = { _ ->
            isLoading.value = true
        },
        onSuccess = { _ ->
            isLoading.value = false
        },
        onError = { _ ->
            isLoading.value = false
        }
    )

    Surface(
        modifier = Modifier
            .size(size)
            .clip(CircleShape),
        shape = CircleShape,
        color = Color.LightGray,
        onClick = onClick,
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            if (isLoading.value) {
                CircularProgressIndicator(
                    modifier = Modifier.size(32.dp),
                    strokeWidth = 3.dp
                )
            }

            AsyncImage(
                model = url,
                contentDescription = contentDescription,
                modifier = Modifier
                    .size(size)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                placeholder = painter
            )
        }
    }
}