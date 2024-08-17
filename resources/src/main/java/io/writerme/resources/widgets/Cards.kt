package io.writerme.resources.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import io.writerme.resources.common.Dimens.GRID_12
import io.writerme.resources.themes.AppTheme

object Cards

@Preview
@Composable
private fun Cards_Preview() {
    AppTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.LightGray)
                .padding(GRID_12)
        ) {
        }
    }
}
