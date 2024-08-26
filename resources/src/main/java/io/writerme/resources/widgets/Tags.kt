package io.writerme.resources.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.dokar.chiptextfield.BasicChipTextField
import com.dokar.chiptextfield.Chip
import com.dokar.chiptextfield.ChipTextFieldDefaults
import com.dokar.chiptextfield.rememberChipTextFieldState
import io.writerme.core.common.FormatUtils.VALUE_1
import io.writerme.resources.R
import io.writerme.resources.common.Dimens.GRID_1
import io.writerme.resources.common.Dimens.GRID_13
import io.writerme.resources.common.Dimens.GRID_4
import io.writerme.resources.common.Dimens.GRID_8
import io.writerme.resources.common.Dimens.screenPadding
import io.writerme.resources.themes.AppTheme
import io.writerme.resources.themes.WriterMeTheme
import io.writerme.resources.widgets.Spacers.SpacerVertical

@Composable
fun TagsBar(
    tags: List<String>,
    addNewTag: (String) -> Unit,
    deleteTag: (String) -> Unit,
    focusRequester: FocusRequester,
    modifier: Modifier = Modifier
) {
    val initialText = stringResource(id = R.string.add_hashtags)
    var text by remember {
        mutableStateOf(initialText)
    }

    val state = rememberChipTextFieldState<Chip>(
        chips = tags.map { Chip(it) }
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = screenPadding)
    ) {
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = WriterMeTheme.colors.strokeLight,
            thickness = GRID_1
        )

        SpacerVertical(GRID_8)

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.tags),
                style = MaterialTheme.typography.h4,
                color = WriterMeTheme.colors.light,
                modifier = Modifier.padding(
                    top = GRID_13,
                    end = screenPadding
                )
            )

            BasicChipTextField(
                modifier = Modifier
                    .focusable()
                    .focusRequester(focusRequester)
                    .fillMaxWidth(),
                state = state,
                value = text,
                onValueChange = { text = it },
                onSubmit = {
                    addNewTag(it)
                    Chip(it)
                },
                textStyle = MaterialTheme.typography.body1.copy(
                    color = WriterMeTheme.colors.light
                ),
                chipStyle = ChipTextFieldDefaults.chipStyle(
                    shape = RoundedCornerShape(screenPadding),
                    focusedBackgroundColor = WriterMeTheme.colors.backgroundGrey,
                    unfocusedBackgroundColor = WriterMeTheme.colors.backgroundGrey,
                    focusedTextColor = WriterMeTheme.colors.light,
                    unfocusedTextColor = WriterMeTheme.colors.light,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
                chipTrailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        tint = WriterMeTheme.colors.light,
                        modifier = Modifier
                            .clickable { deleteTag(it.text) }
                            .padding(end = GRID_4)
                    )
                },
                decorationBox = { innerTextField ->
                    Row {
                        if (text.isEmpty() && state.chips.isEmpty()) {
                            Text(
                                text = stringResource(id = R.string.add_hashtags),
                                style = MaterialTheme.typography.body1,
                                color = WriterMeTheme.colors.light,
                                maxLines = VALUE_1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        innerTextField()
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TagsBarPreview() {
    AppTheme {
        TagsBar(listOf("traveling", "unstoppable", "dreamer"), {}, {}, FocusRequester())
    }
}
