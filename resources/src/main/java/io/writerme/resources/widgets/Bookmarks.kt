package io.writerme.resources.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import coil.compose.AsyncImage
import io.writerme.core.common.FormatUtils.VALUE_1
import io.writerme.core.models.enums.ComponentType
import io.writerme.core.models.viewdata.BookmarksFolderViewData
import io.writerme.core.models.viewdata.ComponentViewData
import io.writerme.resources.R
import io.writerme.resources.common.Dimens.GRID_0
import io.writerme.resources.common.Dimens.GRID_100
import io.writerme.resources.common.Dimens.GRID_12
import io.writerme.resources.common.Dimens.GRID_125
import io.writerme.resources.common.Dimens.GRID_16
import io.writerme.resources.common.Dimens.GRID_25
import io.writerme.resources.common.Dimens.GRID_48
import io.writerme.resources.common.Dimens.GRID_60
import io.writerme.resources.common.Dimens.GRID_8
import io.writerme.resources.themes.AppTheme
import io.writerme.resources.themes.WriterMeTheme
import io.writerme.resources.widgets.Bookmarks.Folder
import io.writerme.resources.widgets.Bookmarks.Link

object Bookmarks {

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun Link(
        link: ComponentViewData,
        onClick: () -> Unit,
        onLongClick: () -> Unit,
        modifier: Modifier = Modifier,
        height: Dp = GRID_0
    ) {
        if (link.type == ComponentType.Link) {
            val shape = RoundedCornerShape(GRID_25)

            // TODO: !!!!!!!!!!!!
            /*checkAndRequestPermission(
                permission = Manifest.permission.INTERNET,
                onSuccess = {},
                onNotGrantedMessage = R.string.we_wont_load_images
            )*/

            Card(
                shape = shape,
                modifier = modifier
                    .wrapContentHeight()
                    .combinedClickable(
                        onLongClick = onLongClick,
                        onClick = onClick
                    )
                    .shadow(GRID_125, shape),
                backgroundColor = WriterMeTheme.colors.dialogBackground
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    val imageModifier = if (height > GRID_0) {
                        Modifier
                            .height(height)
                            .fillMaxWidth()
                    } else {
                        Modifier.fillMaxWidth()
                    }

                    AnimatedVisibility(visible = true) {
                        if (link.mediaUrl == null) {
                            Image(
                                painter = painterResource(id = R.drawable.link),
                                contentDescription = null,
                                modifier = imageModifier,
                                contentScale = ContentScale.Fit
                            )
                        }

                        if (link.mediaUrl != null) {
                            AsyncImage(
                                model = link.mediaUrl,
                                contentDescription = link.content,
                                modifier = imageModifier,
                                contentScale = ContentScale.Crop
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(GRID_48)
                            .align(Alignment.BottomStart)
                            .padding(GRID_12, GRID_0, GRID_12, GRID_12)
                            .clip(
                                RoundedCornerShape(GRID_25)
                            )
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(WriterMeTheme.colors.lightGrey)
                                .blur(GRID_60)
                        )

                        Text(
                            text = link.title.ifEmpty { link.url },
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(GRID_8, GRID_0),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun Folder(
        folder: BookmarksFolderViewData,
        modifier: Modifier = Modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_folder),
                contentDescription = stringResource(id = R.string.folder_icon)
            )

            Text(
                text = folder.name,
                style = MaterialTheme.typography.body1, // TODO: update typography
                maxLines = VALUE_1,
                modifier = Modifier
                    .width(GRID_100)
                    .padding(
                        horizontal = GRID_0,
                        vertical = GRID_8
                    ),
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                color = WriterMeTheme.colors.light
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FolderPreview() {
    val folder = BookmarksFolderViewData(name = "Long")

    val modifier = Modifier.padding(GRID_16)

    AppTheme {
        Folder(
            folder = folder,
            modifier = modifier
        )
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun LinkPreview() {
    val link = ComponentViewData(
        type = ComponentType.Link,
        title = "Top Travel Guide Top Travel Guide Top Travel Guide Top Travel Guide",
        url = "https://writerme.io",
        mediaUrl = "file:///android_asset/tropical_1.jpg"
    )

    val modifier = Modifier
        .fillMaxWidth()
        .padding(GRID_16)

    AppTheme {
        Link(link = link, {}, {}, modifier = modifier)
    }
}
