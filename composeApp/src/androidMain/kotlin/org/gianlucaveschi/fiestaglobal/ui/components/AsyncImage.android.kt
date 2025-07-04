package org.gianlucaveschi.fiestaglobal.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent

@Composable
actual fun AsyncImage(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier,
    onLoading: @Composable () -> Unit,
    onError: @Composable () -> Unit
) {
    if (imageUrl.isBlank()) {
        return
    }
    
    SubcomposeAsyncImage(
        model = imageUrl,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = ContentScale.Crop
    ) {
        when (painter.state) {
            is AsyncImagePainter.State.Loading -> {
                onLoading()
            }
            is AsyncImagePainter.State.Error -> {
                onError()
            }
            else -> {
                SubcomposeAsyncImageContent()
            }
        }
    }
} 