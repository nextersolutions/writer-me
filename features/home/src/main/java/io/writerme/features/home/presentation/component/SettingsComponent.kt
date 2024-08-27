package io.writerme.app.ui.screen

import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import io.writerme.core.common.GlobalConstants.HistoryDefaults.LINK_CHANGES_HISTORY_KEY
import io.writerme.core.common.GlobalConstants.HistoryDefaults.MEDIA_CHANGES_HISTORY_KEY
import io.writerme.core.common.GlobalConstants.HistoryDefaults.TASK_CHANGES_HISTORY_KEY
import io.writerme.core.common.GlobalConstants.HistoryDefaults.TEXT_CHANGES_HISTORY_KEY
import io.writerme.core.common.GlobalConstants.HistoryDefaults.VOICE_CHANGES_HISTORY_KEY
import io.writerme.core.models.viewdata.SettingsViewData
import io.writerme.resources.R
import io.writerme.resources.common.Dimens.GRID_0
import io.writerme.resources.common.Dimens.GRID_10
import io.writerme.resources.common.Dimens.GRID_12
import io.writerme.resources.common.Dimens.GRID_150
import io.writerme.resources.common.Dimens.GRID_16
import io.writerme.resources.common.Dimens.GRID_20
import io.writerme.resources.common.Dimens.GRID_25
import io.writerme.resources.common.Dimens.GRID_4
import io.writerme.resources.common.Dimens.GRID_40
import io.writerme.resources.common.Dimens.GRID_48
import io.writerme.resources.common.Dimens.GRID_8
import io.writerme.resources.common.Dimens.WEIGHT_06
import io.writerme.resources.common.Dimens.bigRadius
import io.writerme.resources.common.Dimens.blurRadius
import io.writerme.resources.common.Dimens.screenPadding
import io.writerme.resources.common.Dimens.shadowRadius
import io.writerme.resources.themes.AppTheme
import io.writerme.resources.themes.WriterMeTheme
import io.writerme.resources.widgets.Cards.SettingsCounterRow
import io.writerme.resources.widgets.Cards.SettingsSectionTitle
import io.writerme.resources.widgets.Images.ProfileImage
import io.writerme.resources.widgets.Spacers.SpacerVertical
import java.util.Calendar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingsComponent(
    state: SettingsViewData,
    onLanguageChange: (String) -> Unit,
    onDarkModeChange: (Boolean) -> Unit,
    onTermsClick: () -> Unit,
    onCounterChange: (String, Boolean) -> Unit,
    updateProfileImage: (String) -> Unit,
    dismissScreen: () -> Unit
) {
    val scrollState = rememberScrollState()
    val scaffoldState = rememberScaffoldState()

    val updateProfileImagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let { updateProfileImage(it.toString()) }
        }
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background_main),
            contentScale = ContentScale.Crop,
            contentDescription = stringResource(id = R.string.background_image),
            modifier = Modifier.fillMaxSize()
        )

        Scaffold(
            scaffoldState = scaffoldState,
            backgroundColor = Color.Transparent,
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    backgroundColor = Color.Transparent,
                    elevation = GRID_0,
                    title = {
                        Text(
                            text = stringResource(id = R.string.settings),
                            style = MaterialTheme.typography.h2,
                            color = WriterMeTheme.colors.light
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = dismissScreen) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_back),
                                contentDescription = stringResource(id = R.string.back_button),
                                tint = WriterMeTheme.colors.light
                            )
                        }
                    }
                )
            }
        ) {
            val shape = RoundedCornerShape(bigRadius)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(screenPadding),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AnimatedVisibility(visible = true) {
                        if (state.profilePictureUrl.isNotEmpty()) {
                            ProfileImage(
                                url = state.profilePictureUrl,
                                onClick = { updateProfileImagePicker.launch("image/*") }
                            )
                        } else {
                            IconButton(
                                modifier = Modifier
                                    .clip(shape)
                                    .background(WriterMeTheme.colors.backgroundGrey)
                                    .padding(GRID_4, GRID_12)
                                    .shadow(
                                        elevation = shadowRadius,
                                        shape = shape
                                    ),
                                onClick = {
                                    updateProfileImagePicker.launch("image/*")
                                }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_camera),
                                    contentDescription = stringResource(id = R.string.add_cover_image_button),
                                    modifier = Modifier.size(GRID_40),
                                    tint = WriterMeTheme.colors.light
                                )
                            }
                        }
                    }

                    Column(
                        modifier = Modifier
                            .weight(WEIGHT_06)
                            .padding(horizontal = screenPadding)
                    ) {
                        Text(
                            text = state.fullName,
                            style = MaterialTheme.typography.h1,
                            color = WriterMeTheme.colors.light
                        )

                        Text(
                            text = state.email,
                            style = MaterialTheme.typography.body1,
                            color = WriterMeTheme.colors.light,
                            modifier = Modifier.padding(top = GRID_10)
                        )
                    }
                }

                // TODO: add the dialog to edit email, photo and the full name

                Card(
                    modifier = Modifier.padding(screenPadding, GRID_8),
                    shape = RoundedCornerShape(GRID_25),
                    elevation = GRID_20,
                    backgroundColor = WriterMeTheme.colors.cardBackground
                ) {
                    Column {
                        SettingsSectionTitle(
                            titleRes = R.string.changes_history,
                            iconRes = R.drawable.ic_history
                        )

                        Divider(
                            modifier = Modifier.padding(
                                start = screenPadding,
                                end = screenPadding,
                                bottom = screenPadding
                            ),
                            color = WriterMeTheme.colors.strokeLight
                        )

                        Text(
                            text = stringResource(id = R.string.changes_history_description),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = screenPadding,
                                    top = GRID_8,
                                    end = screenPadding,
                                    bottom = GRID_20
                                ),
                            style = MaterialTheme.typography.body2,
                            color = WriterMeTheme.colors.light
                        )

                        SettingsCounterRow(
                            stringId = R.string.number_of_text_changes,
                            value = state.textChanges,
                            increaseValueId = R.string.increase_number_of_text_changes,
                            decreaseValueId = R.string.decrease_number_of_text_changes,
                            onChange = {
                                onCounterChange(TEXT_CHANGES_HISTORY_KEY, it)
                            }
                        )

                        SettingsCounterRow(
                            stringId = R.string.number_of_voice_changes,
                            value = state.voiceChanges,
                            increaseValueId = R.string.increase_number_of_voice_changes,
                            decreaseValueId = R.string.decrease_number_of_voice_changes,
                            onChange = {
                                onCounterChange(VOICE_CHANGES_HISTORY_KEY, it)
                            }
                        )

                        SettingsCounterRow(
                            stringId = R.string.number_of_tasks_changes,
                            value = state.taskChanges,
                            increaseValueId = R.string.increase_number_of_tasks_changes,
                            decreaseValueId = R.string.decrease_number_of_tasks_changes,
                            onChange = {
                                onCounterChange(TASK_CHANGES_HISTORY_KEY, it)
                            }
                        )

                        SettingsCounterRow(
                            stringId = R.string.number_of_media_changes,
                            value = state.mediaChanges,
                            increaseValueId = R.string.increase_number_of_media_changes,
                            decreaseValueId = R.string.decrease_number_of_media_changes,
                            onChange = {
                                onCounterChange(MEDIA_CHANGES_HISTORY_KEY, it)
                            }
                        )

                        SettingsCounterRow(
                            stringId = R.string.number_of_link_changes,
                            value = state.linkChanges,
                            increaseValueId = R.string.increase_number_of_link_changes,
                            decreaseValueId = R.string.decrease_number_of_link_changes,
                            onChange = {
                                onCounterChange(LINK_CHANGES_HISTORY_KEY, it)
                            }
                        )

                        SpacerVertical(GRID_8)
                    }
                }

                Card(
                    shape = shape,
                    modifier = Modifier
                        .padding(screenPadding, GRID_8)
                        .shadow(
                            elevation = shadowRadius,
                            shape = shape
                        ),
                    backgroundColor = Color.Transparent
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
                                titleRes = R.string.appearance
                            )

                            Divider(
                                modifier = Modifier.padding(
                                    screenPadding,
                                    GRID_0,
                                    screenPadding,
                                    screenPadding
                                ),
                                color = WriterMeTheme.colors.strokeLight
                            )

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(screenPadding, GRID_8),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = stringResource(id = R.string.language),
                                    style = MaterialTheme.typography.body1,
                                    color = WriterMeTheme.colors.light
                                )

                                var isLanguagesListExpanded by remember {
                                    mutableStateOf(false)
                                }

                                ExposedDropdownMenuBox(
                                    expanded = isLanguagesListExpanded,
                                    onExpandedChange = {
                                        isLanguagesListExpanded = !isLanguagesListExpanded
                                    },
                                    modifier = Modifier
                                        .width(GRID_150)
                                        .height(GRID_48)
                                ) {
                                    TextField(
                                        value = state.currentLanguage,
                                        onValueChange = onLanguageChange,
                                        readOnly = true,
                                        trailingIcon = {
                                            ExposedDropdownMenuDefaults.TrailingIcon(
                                                expanded = isLanguagesListExpanded
                                            )
                                        },
                                        colors = ExposedDropdownMenuDefaults.textFieldColors(
                                            focusedIndicatorColor = Color.Transparent,
                                            unfocusedIndicatorColor = Color.Transparent,
                                            backgroundColor = WriterMeTheme.colors.light

                                        ),
                                        shape = RoundedCornerShape(bigRadius),
                                        textStyle = MaterialTheme.typography.body2
                                    )

                                    MaterialTheme(
                                        colors = MaterialTheme.colors.copy(
                                            surface = WriterMeTheme.colors.light,
                                            background = Color.Blue
                                        ),
                                        shapes = MaterialTheme.shapes.copy(
                                            medium = RoundedCornerShape(bigRadius)
                                        )
                                    ) {
                                        ExposedDropdownMenu(
                                            expanded = isLanguagesListExpanded,
                                            onDismissRequest = { isLanguagesListExpanded = false }
                                        ) {
                                            state.languages.forEach { selectedOption ->
                                                DropdownMenuItem(onClick = {
                                                    onLanguageChange(selectedOption)
                                                    isLanguagesListExpanded = false
                                                }) {
                                                    Text(
                                                        text = selectedOption,
                                                        style = MaterialTheme.typography.body2
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            /*Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(screenPadding, 8.dp)
                                    .clickable {

                                    },
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = stringResource(id = R.string.dark_mode),
                                    style = MaterialTheme.typography.body1,
                                    color = MaterialTheme.colors.light
                                )

                                Switch(
                                    checked = state.value.isDarkMode,
                                    onCheckedChange = { onDarkModeChange(it) }
                                )
                            }*/
                        }
                    }
                }

                Card(
                    modifier = Modifier.padding(screenPadding, GRID_8),
                    shape = RoundedCornerShape(GRID_25),
                    elevation = GRID_16,
                    backgroundColor = WriterMeTheme.colors.cardBackground
                ) {
                    Column {
                        SettingsSectionTitle(
                            titleRes = R.string.help,
                            iconRes = R.drawable.ic_help
                        )

                        Divider(
                            modifier = Modifier.padding(horizontal = screenPadding),
                            color = WriterMeTheme.colors.strokeLight
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(screenPadding)
                                .clickable { onTermsClick() },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = stringResource(id = R.string.terms),
                                style = MaterialTheme.typography.body1,
                                color = WriterMeTheme.colors.light
                            )

                            Icon(
                                painter = painterResource(id = R.drawable.ic_globe),
                                contentDescription = stringResource(id = R.string.globe_icon),
                                tint = WriterMeTheme.colors.light
                            )
                        }
                    }
                }

                val text = stringResource(id = R.string.copyright)
                val year = Calendar.getInstance().get(Calendar.YEAR)

                Text(
                    text = String.format(text, year),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(GRID_0, GRID_16),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    val state = SettingsViewData(
        currentLanguage = "English",
        fullName = "Florian Hermes",
        email = "florian.hermes@email.com",
        profilePictureUrl = "",
        languages = listOf("English", "Deutsch", "Українська")
    )

    AppTheme {
        SettingsComponent(
            state,
            {},
            {},
            {},
            { _, _ -> },
            {},
            {}
        )
    }
}
