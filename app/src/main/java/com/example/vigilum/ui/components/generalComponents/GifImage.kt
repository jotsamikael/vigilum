package com.example.vigilum.ui.components.generalComponents

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder

@Composable
fun GifImage(modifier: Modifier, image:Any) {
    val context = LocalContext.current

    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    Image(
        modifier = modifier,
        painter = rememberAsyncImagePainter(
            model = image,
            imageLoader = imageLoader,
            contentScale = ContentScale.FillBounds
        ),
        contentDescription = null,

        )
}

