package io.writerme.resources.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import io.writerme.core.common.FormatUtils.EMPTY
import io.writerme.core.models.enums.ComponentType
import io.writerme.core.models.viewdata.ComponentViewData
import io.writerme.resources.R
import io.writerme.resources.common.Dimens.GRID_16
import io.writerme.resources.common.Dimens.GRID_30
import io.writerme.resources.common.Dimens.GRID_53
import io.writerme.resources.common.Dimens.GRID_66
import io.writerme.resources.common.Dimens.GRID_7
import io.writerme.resources.common.Dimens.bigRadius
import io.writerme.resources.common.Dimens.screenPadding
import io.writerme.resources.common.Dimens.shadowRadius
import io.writerme.resources.themes.AppTheme
import io.writerme.resources.themes.WriterMeTheme
import io.writerme.resources.widgets.Spacers.SpacerVertical

object Images {

    @Composable
    fun Image(
        component: ComponentViewData,
        modifier: Modifier = Modifier
    ) {
        if (component.type == ComponentType.Image) {
            val shape = RoundedCornerShape(bigRadius)

            Card(
                shape = shape,
                modifier = modifier
                    .wrapContentHeight()
                    .shadow(shadowRadius, shape),
                backgroundColor = Color.White
            ) {
                AsyncImage(
                    model = component.mediaUrl,
                    contentDescription = component.content,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }

    @Composable
    fun ProfileImage(url: String, onClick: () -> Unit) {
        val shape = RoundedCornerShape(GRID_30, GRID_7, GRID_30, GRID_30)

        Box(
            modifier = Modifier
                .size(GRID_66)
                .clip(shape)
                .background(WriterMeTheme.colors.light)
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            /*Image(
                painter = painterResource(id = R.drawable.florian),
                contentDescription = stringResource(id = R.string.profile_picture),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(53.dp)
                    .clip(CircleShape)
            )*/

            AsyncImage(
                model = url,
                contentDescription = stringResource(id = R.string.profile_picture),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(GRID_53)
                    .clip(CircleShape)
            )
        }
    }
}

@Preview
@Composable
private fun Preview_Images() {
    val imageComponent = ComponentViewData(
        type = ComponentType.Image
    )

    AppTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
                .padding(GRID_16)
        ) {
            Images.Image(
                component = imageComponent,
                modifier = Modifier.padding(screenPadding)
            )

            SpacerVertical(GRID_16)

            Images.ProfileImage(url = EMPTY) {}
        }
    }
}
