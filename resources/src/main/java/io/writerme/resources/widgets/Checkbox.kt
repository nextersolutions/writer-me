package io.writerme.resources.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import io.writerme.core.models.enums.ComponentType
import io.writerme.core.models.viewdata.ComponentViewData
import io.writerme.resources.R
import io.writerme.resources.common.Dimens.GRID_16
import io.writerme.resources.common.Dimens.GRID_22
import io.writerme.resources.common.Dimens.GRID_3
import io.writerme.resources.common.Dimens.screenPadding
import io.writerme.resources.themes.AppTheme
import io.writerme.resources.themes.WriterMeTheme

@Composable
fun Checkbox(
    component: ComponentViewData,
    onValueChange: (ComponentViewData) -> Unit,
    onCheckedChange: () -> Unit,
    onAddNewCheckbox: () -> Unit,
    onDeleteCheckBox: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (component.type == ComponentType.Checkbox) {
        var localText by remember {
            mutableStateOf(component.content)
        }

        val requester = remember { FocusRequester() }

        LaunchedEffect(Unit) {
            requester.requestFocus()
        }

        val iconRes = when (component.isChecked) {
            true -> R.drawable.ic_checked
            false -> R.drawable.ic_unchecked
        }

        Row(
            modifier = modifier.fillMaxWidth()
        ) {
            Icon(
                modifier = Modifier
                    .size(GRID_22)
                    .padding(top = GRID_3)
                    .clickable(onClick = onCheckedChange),
                painter = painterResource(id = iconRes),
                contentDescription = component.content,
                tint = WriterMeTheme.colors.light
            )

            BasicTextField(
                value = localText,
                onValueChange = {
                    localText = it
                    onValueChange(component.copy(content = it))
                },
                modifier = Modifier
                    .padding(start = GRID_16)
                    .fillMaxWidth()
                    .onKeyEvent { event ->
                        when {
                            event.type == KeyEventType.KeyUp && event.key == Key.Backspace &&
                                localText.isEmpty() -> {
                                onDeleteCheckBox()
                                return@onKeyEvent true
                            }

                            event.type == KeyEventType.KeyDown && event.key == Key.Enter -> {
                                onAddNewCheckbox()
                                return@onKeyEvent true
                            }

                            else -> false
                        }
                    }
                    .focusRequester(requester)
                    .focusable(),
                textStyle = MaterialTheme.typography.subtitle1.copy(
                    color = WriterMeTheme.colors.light,
                    textDecoration = when (component.isChecked) {
                        true -> TextDecoration.LineThrough
                        false -> TextDecoration.None
                    }
                ),
                cursorBrush = SolidColor(WriterMeTheme.colors.light)
            )
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun CheckboxPreview() {
    val component = ComponentViewData(
        type = ComponentType.Checkbox,
        content = "Complete writing post for Instagram, Complete writing post for Instagram, Complete writing post for Instagram",
        isChecked = false
    )

    val modifier = Modifier.padding(screenPadding)

    AppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.DarkGray)
        ) {
            Checkbox(component = component, {}, {}, {}, {}, modifier = modifier)
        }
    }
}
