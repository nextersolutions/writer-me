package io.writerme.resources.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.DropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import io.writerme.core.models.viewdata.DropdownViewData
import io.writerme.resources.R
import io.writerme.resources.common.Dimens.GRID_20
import io.writerme.resources.themes.WriterMeTheme

object DropDowns {
    @Composable
    fun DropDownMenuWidget(
        items: List<DropdownViewData>,
        content: @Composable () -> Unit
    ) {
        var isExpanded by remember {
            mutableStateOf(false)
        }

        Box(
            modifier = Modifier.wrapContentSize(Alignment.TopStart)
        ) {
            content.invoke()

            MaterialTheme(
                colors = MaterialTheme.colors.copy(
                    surface = WriterMeTheme.colors.light
                ),
                shapes = MaterialTheme.shapes.copy(
                    medium = RoundedCornerShape(
                        dimensionResource(id = R.dimen.small_radius)
                    )
                )
            ) {
                DropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = !isExpanded }
                ) {
                    items.forEach { item ->
                        DropdownItem(item)
                    }
                }
            }
        }
    }

    @Composable
    fun DropDownMenuWidget(
        isExpanded: Boolean,
        onExpandedToggle: () -> Unit,
        items: List<DropdownViewData>,
        content: @Composable () -> Unit
    ) {
        Box(
            modifier = Modifier.wrapContentSize(Alignment.TopStart)
        ) {
            content.invoke()

            MaterialTheme(
                colors = MaterialTheme.colors.copy(
                    surface = WriterMeTheme.colors.light
                ),
                shapes = MaterialTheme.shapes.copy(
                    medium = RoundedCornerShape(
                        dimensionResource(id = R.dimen.small_radius)
                    )
                )
            ) {
                DropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { onExpandedToggle.invoke() }
                ) {
                    items.forEach { item ->
                        DropdownItem(item)
                    }
                }
            }
        }
    }

    @Composable
    private fun DropdownItem(item: DropdownViewData) {
        DropdownMenuItem(onClick = {
            item.onClick.invoke()
        }) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = item.titleRes),
                    style = MaterialTheme.typography.body1
                )


                Icon(
                    painter = painterResource(id = item.iconRes),
                    contentDescription = stringResource(id = R.string.delete),
                    modifier = Modifier.size(GRID_20),
                    tint = Color.DarkGray
                )
            }
        }
    }
}