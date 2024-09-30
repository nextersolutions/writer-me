package io.writerme.features.home.presentation.widgets

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import io.writerme.resources.common.Dimens.GRID_20
import io.writerme.resources.common.Dimens.GRID_8
import io.writerme.resources.common.Dimens.bigRadius
import io.writerme.resources.common.Dimens.blurRadius
import io.writerme.resources.common.Dimens.screenPadding
import io.writerme.resources.common.Dimens.shadowRadius
import io.writerme.resources.themes.WriterMeTheme
import io.writerme.resources.widgets.Cards.SettingsSectionTitle

@Composable
fun SettingsWrapperCard(
    @StringRes cardTitleRes: Int,
    @DrawableRes cardIconRes: Int? = null,
    backgroundColor: Color = WriterMeTheme.colors.cardBackground,
    content: @Composable ColumnScope.() -> Unit = {}
) {
    val shape = RoundedCornerShape(bigRadius)

    Card(
        shape = shape,
        modifier = Modifier
            .padding(screenPadding, GRID_8)
            .shadow(
                elevation = shadowRadius,
                shape = shape
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = GRID_20),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor,
            contentColor = backgroundColor
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(WriterMeTheme.colors.cardBackground)
                    .blur(blurRadius)
            )

            Column {
                SettingsSectionTitle(
                    titleRes = cardTitleRes,
                    iconRes = cardIconRes
                )

                HorizontalDivider(
                    modifier = Modifier.padding(
                        start = screenPadding,
                        end = screenPadding,
                        bottom = screenPadding
                    ),
                    color = WriterMeTheme.colors.strokeLight
                )

                content.invoke(this)
            }
        }
    }
}