package io.writerme.resources.widgets

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Text
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import io.writerme.resources.themes.SharemyTheme

object AppBars {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AppBarTitleCentered(
        titleRes: Int? = null,
        titleStyle: TextStyle = SharemyTheme.typography.welcomeTitle,
        backgroundColor: Color = SharemyTheme.colors.primaryBackground,
        navigationIcon: @Composable () -> Unit = {},
        actions: @Composable RowScope.() -> Unit = {}
    ) {
        CenterAlignedTopAppBar(
            title = if (titleRes != null) {
                {
                    Text(
                        text = stringResource(id = titleRes),
                        style = titleStyle
                    )
                }
            } else {
                {}
            },
            navigationIcon = {
                navigationIcon.invoke()
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = backgroundColor,
                scrolledContainerColor = backgroundColor
            ),
            actions = actions
        )
    }
}
