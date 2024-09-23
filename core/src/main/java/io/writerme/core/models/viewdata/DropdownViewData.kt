package io.writerme.core.models.viewdata

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class DropdownViewData(
    @StringRes val titleRes: Int,
    @DrawableRes val iconRes: Int,
    val onClick: () -> Unit
)
