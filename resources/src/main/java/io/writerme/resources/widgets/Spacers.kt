package io.writerme.resources.widgets

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import io.writerme.resources.common.Dimens.GRID_10
import io.writerme.resources.common.Dimens.WEIGHT_MAX

object Spacers {
    @Composable
    fun RowScope.SpacerWeightView(value: Float = WEIGHT_MAX) {
        Spacer(modifier = Modifier.weight(value))
    }

    @Composable
    fun ColumnScope.SpacerWeightView(value: Float = WEIGHT_MAX) {
        Spacer(modifier = Modifier.weight(value))
    }

    @Composable
    fun SpacerHorizontalView(value: Dp = GRID_10) {
        Spacer(modifier = Modifier.width(value))
    }

    @Composable
    fun SpacerVerticalView(value: Dp = GRID_10) {
        Spacer(modifier = Modifier.height(value))
    }
}
