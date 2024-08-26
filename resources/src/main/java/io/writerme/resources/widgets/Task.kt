package io.writerme.resources.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import io.writerme.core.models.enums.ComponentType
import io.writerme.core.models.viewdata.ComponentViewData
import io.writerme.resources.R
import io.writerme.resources.common.Dimens.GRID_16
import io.writerme.resources.common.Dimens.WEIGHT_08
import io.writerme.resources.common.Dimens.bigRadius
import io.writerme.resources.common.Dimens.shadowRadius
import io.writerme.resources.extensions.toDateDescription
import io.writerme.resources.themes.AppTheme
import io.writerme.resources.themes.WriterMeTheme
import java.util.Date

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Task(
    task: ComponentViewData,
    onClick: () -> Unit,
    onValueChange: (ComponentViewData) -> Unit,
    modifier: Modifier = Modifier
) {
    if (task.type == ComponentType.Task) {
        val shape = RoundedCornerShape(bigRadius)

        var localText by remember {
            mutableStateOf(task.content)
        }

        Card(
            shape = shape,
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.Transparent)
                .shadow(
                    elevation = shadowRadius,
                    shape = shape
                ),
            onClick = onClick
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(WriterMeTheme.colors.backgroundGrey)
                )

                Row(
                    modifier = modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(GRID_16),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(WEIGHT_08)
                    ) {
                        Text(
                            text = task.time.toDateDescription(),
                            style = MaterialTheme.typography.h5,
                            color = WriterMeTheme.colors.light
                        )
                        BasicTextField(
                            value = localText,
                            onValueChange = {
                                localText = it

                                onValueChange(
                                    task.copy(content = it)
                                )
                            },
                            textStyle = MaterialTheme.typography.h3.copy(
                                color = WriterMeTheme.colors.light
                            ),
                            cursorBrush = SolidColor(WriterMeTheme.colors.light)
                        )
                    }
                    Icon(
                        painter = painterResource(id = R.drawable.ic_proceed),
                        contentDescription = null,
                        tint = WriterMeTheme.colors.light
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun TaskPreview() {
    val component = ComponentViewData(
        content = "Meeting with Anna",
        time = Date(),
        type = ComponentType.Task
    )

    AppTheme {
        Task(component, {}, {})
    }
}
