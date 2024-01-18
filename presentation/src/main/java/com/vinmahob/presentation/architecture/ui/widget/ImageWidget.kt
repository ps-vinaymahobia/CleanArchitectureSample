package com.vinmahob.presentation.architecture.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

@Composable
fun ImageWidget(
    imageUrl: String,
    contentDescription: String? = null,
    modifier: Modifier
) {
    Image(
        painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current).data(imageUrl).crossfade(true)
                .build()
        ),
        contentDescription = contentDescription,
        modifier = modifier
    )
}