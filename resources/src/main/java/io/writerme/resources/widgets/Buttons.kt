package io.writerme.resources.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import io.writerme.resources.common.Dimens.GRID_12
import io.writerme.resources.themes.AppTheme

object Buttons {

    @Composable
    fun DefaultButton() {
        // TODO
        /*val multipleEventsCutter by remember {
            mutableStateOf(MultipleEventsCutter.get())
        }

        Button(
            onClick = {
                multipleEventsCutter.processEvent { onClick.invoke() }
            },
            enabled = enabled,
            modifier = if (useDefaultModifierSettings) {
                modifier
                    .fillMaxWidth()
                    .height(BUTTON_HEIGHT)
            } else {
                modifier
            },
            colors = colors,
            shape = shape,
            elevation = elevation,
            border = border
        ) {
            buttonContent.invoke(this)
        }*/
    }

    @Composable
    fun FlatButton(
        title: String,
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        // textColor: Color = YoniTheme.colors.primaryText,
        useDefaultModifierSettings: Boolean = true,
        enabled: Boolean = true,
        enableBorder: Boolean = true,
        buttonContent: (@Composable RowScope.() -> Unit)? = null
    ) {
        // TODO
    }
}

@Preview
@Composable
private fun Buttons_Preview() {
    AppTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
                .padding(GRID_12)
        ) {
        }
    }
}
