package org.gianlucaveschi.fiestaglobal.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

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
} 