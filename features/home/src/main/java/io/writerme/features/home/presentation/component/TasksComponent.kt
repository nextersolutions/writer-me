package io.writerme.features.home.presentation.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.writerme.resources.themes.AppTheme

@Composable
fun TaskComponent() {
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TaskScreenPreview() {
    AppTheme {
        TaskComponent()
    }
}
