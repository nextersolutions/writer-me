package io.writerme.resources.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import io.writerme.core.common.FormatUtils.VALUE_1
import io.writerme.resources.common.Dimens.EDIT_TEXT_HEIGHT
import io.writerme.resources.common.Dimens.GRID_12
import io.writerme.resources.common.Dimens.GRID_6
import io.writerme.resources.themes.AppTheme
import io.writerme.resources.themes.SharemyTheme
import io.writerme.resources.widgets.EditTexts.EditTextWidget
import io.writerme.resources.widgets.Spacers.SpacerVerticalView

@Composable
fun EditTextWithLabelWidget(
    labelText: String,
    modifier: Modifier = Modifier.fillMaxWidth(),
    text: TextFieldValue,
    onTextChange: (TextFieldValue) -> Unit,
    placeholder: String,
    maxLines: Int = VALUE_1,
    height: Dp = EDIT_TEXT_HEIGHT,
    error: Int? = null,
    readOnly: Boolean = false,
    enabled: Boolean = true,
    isError: Boolean = false,
    imeAction: ImeAction = ImeAction.Done,
    keyboardType: KeyboardType = KeyboardType.Email,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onClick: () -> Unit = {},
    focusRequester: FocusRequester? = null
) {
    var isFocused by remember {
        mutableStateOf(false)
    }

    Column {
        Text(
            text = labelText,
            style = SharemyTheme.typography.editText
        )

        SpacerVerticalView(GRID_6)

        EditTextWidget(
            modifier = modifier,
            value = text,
            onValueChange = onTextChange,
            isError = isError,
            error = error,
            readOnly = readOnly,
            enabled = enabled,
            placeholder = placeholder,
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = false,
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            focusRequester = focusRequester,
            onFocusChanged = {
                isFocused = it.isFocused
            },
            maxLines = maxLines,
            onClick = onClick,
            visualTransformation = visualTransformation
        )
    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
private fun EditTextWithLabelWidget_Preview() {
    AppTheme {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(GRID_12)
        ) {
            EditTextWithLabelWidget(
                labelText = "Phone number",
                text = TextFieldValue(),
                onTextChange = {},
                placeholder = "00000"
            )
        }
    }
}
