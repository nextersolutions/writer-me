package io.writerme.resources.widgets

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import coil.compose.AsyncImage
import io.writerme.resources.common.Dimens.GRID_16
import io.writerme.resources.common.Dimens.GRID_40
import io.writerme.resources.themes.AppTheme
import io.writerme.resources.widgets.Images.RoundImage

object Images {

    @Composable
    fun ImageWidget(
        @DrawableRes drawableRes: Int,
        modifier: Modifier = Modifier,
        contentScale: ContentScale = ContentScale.Fit
    ) {
        Image(
            painter = painterResource(id = drawableRes),
            contentDescription = null,
            modifier = modifier,
            contentScale = contentScale
        )
    }

    @Composable
    fun RoundImage(
        imageUrl: String?,
        size: Dp = GRID_40,
        onClick: () -> Unit
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .clickable { onClick.invoke() }
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(size)
            )
        }
    }
}

@Preview
@Composable
private fun Preview_Images() {
    AppTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
                .padding(GRID_16)
        ) {
            RoundImage(
                imageUrl = null,
                onClick = {}
            )
        }
    }
}
