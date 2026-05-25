package io.writerme.app.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import io.writerme.app.R
import io.writerme.app.data.viewdata.BookmarksFolderViewData
import io.writerme.app.ui.theme.WriterMeTheme
import io.writerme.app.ui.theme.dialogBackground
import io.writerme.app.ui.theme.light
import io.writerme.app.ui.theme.strokeLight
import io.writerme.app.utils.textFieldBackground

@Composable
fun CreateBookmarkDialog(
    createBookmark: (url: String, title: String) -> Unit,
    bookmarksFolder: BookmarksFolderViewData,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(dimensionResource(id = R.dimen.big_radius))
    val padding = dimensionResource(id = R.dimen.screen_padding)

    var url by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = shape,
            modifier = modifier
                .wrapContentHeight()
                .shadow(dimensionResource(id = R.dimen.shadow), shape)
        ) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(MaterialTheme.colors.dialogBackground)
                        .blur(dimensionResource(id = R.dimen.blur_radius))
                )

                Column(modifier = Modifier.padding(padding)) {
                    Text(
                        text = bookmarksFolder.name.ifEmpty { "/" },
                        style = MaterialTheme.typography.h4,
                        color = MaterialTheme.colors.light,
                        modifier = Modifier.padding(0.dp, 8.dp, 0.dp, padding)
                    )

                    Divider(
                        thickness = 1.dp,
                        color = MaterialTheme.colors.strokeLight
                    )

                    Spacer(modifier = Modifier.height(padding))

                    BasicTextField(
                        value = url,
                        maxLines = 1,
                        onValueChange = { url = it },
                        modifier = Modifier.textFieldBackground().fillMaxWidth(),
                        singleLine = true,
                        textStyle = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.light),
                        cursorBrush = SolidColor(MaterialTheme.colors.light),
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                if (url.isEmpty()) {
                                    Text(
                                        text = "URL",
                                        style = MaterialTheme.typography.body1,
                                        color = MaterialTheme.colors.light
                                    )
                                }
                                innerTextField()
                            }
                        },
                    )

                    Spacer(modifier = Modifier.height(padding))

                    BasicTextField(
                        value = title,
                        maxLines = 1,
                        onValueChange = { title = it },
                        singleLine = true,
                        modifier = Modifier.textFieldBackground().fillMaxWidth(),
                        textStyle = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.light),
                        cursorBrush = SolidColor(MaterialTheme.colors.light),
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                if (title.isEmpty()) {
                                    Text(
                                        text = "Title",
                                        style = MaterialTheme.typography.body1,
                                        color = MaterialTheme.colors.light
                                    )
                                }
                                innerTextField()
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(padding))

                    Text(
                        text = "Create",
                        modifier = Modifier.textFieldBackground()
                            .wrapContentHeight()
                            .padding(20.dp, 0.dp)
                            .align(Alignment.CenterHorizontally)
                            .clickable {
                                createBookmark(url, title)
                                onDismiss()
                            },
                        color = MaterialTheme.colors.light
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateBookmarkDialogPreview() {
    WriterMeTheme {
        CreateBookmarkDialog(
            createBookmark = { _, _ -> },
            onDismiss = {},
            bookmarksFolder = BookmarksFolderViewData.empty()
        )
    }
}
