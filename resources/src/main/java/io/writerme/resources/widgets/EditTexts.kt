package io.writerme.resources.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import io.writerme.core.common.FormatUtils.EMPTY
import io.writerme.core.common.FormatUtils.OTP_CODE_LENGTH
import io.writerme.core.common.FormatUtils.VALUE_1
import io.writerme.resources.R
import io.writerme.resources.common.Dimens.EDIT_TEXT_HEIGHT
import io.writerme.resources.common.Dimens.GRID_0
import io.writerme.resources.common.Dimens.GRID_1
import io.writerme.resources.common.Dimens.GRID_15
import io.writerme.resources.common.Dimens.GRID_16
import io.writerme.resources.common.Dimens.GRID_24
import io.writerme.resources.common.Dimens.GRID_6
import io.writerme.resources.common.Dimens.GRID_60
import io.writerme.resources.common.Dimens.GRID_62
import io.writerme.resources.themes.WriterMeTheme
import io.writerme.resources.widgets.Spacers.SpacerVertical

object EditTexts {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun EditTextWidget(
        value: TextFieldValue,
        onValueChange: (TextFieldValue) -> Unit,
        placeholder: String,
        modifier: Modifier = Modifier,
        focusRequester: FocusRequester? = null,
        enabled: Boolean = true,
        readOnly: Boolean = false,
        onClick: () -> Unit = {},
        textStyle: TextStyle = WriterMeTheme.typography.editText,
        placeholderStyle: TextStyle = WriterMeTheme.typography.editText, // TODO
        isError: Boolean = false,
        error: Int? = null,
        keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(
            capitalization = KeyboardCapitalization.Sentences,
            autoCorrect = false,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        textColor: Color = Color.Black,
        maxLines: Int = VALUE_1,
        minLines: Int = VALUE_1,
        label: @Composable (() -> Unit)? = null,
        keyboardActions: KeyboardActions = KeyboardActions(),
        leadingIcon: @Composable (() -> Unit)? = null,
        trailingIcon: @Composable (() -> Unit)? = null,
        visualTransformation: VisualTransformation = VisualTransformation.None,
        onFocusChanged: ((FocusState) -> Unit)? = null
    ) {
        val textError = if (error != null) stringResource(id = error) else null

        var textFieldModifier = modifier
            .height(EDIT_TEXT_HEIGHT)
            .padding(GRID_0)
            .onFocusChanged {
                onFocusChanged?.invoke(it)
            }
            .clickable {
                onClick.invoke()
            }

        focusRequester?.let {
            textFieldModifier = textFieldModifier.focusRequester(it)
        }

        val interactionSource = remember { MutableInteractionSource() }

        if (value.text.isNotEmpty()) {
            LaunchedEffect(key1 = Unit) {
                onValueChange(
                    TextFieldValue(
                        text = value.text,
                        selection = when {
                            value.text.isEmpty() -> TextRange.Zero
                            else -> TextRange(value.text.length, value.text.length)
                        }
                    )
                )
            }
        }

        Column {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                enabled = true,
                singleLine = maxLines == VALUE_1,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                visualTransformation = visualTransformation,
                textStyle = textStyle,
                maxLines = maxLines,
                minLines = minLines,
                modifier = textFieldModifier,
                readOnly = readOnly
            ) {
                OutlinedTextFieldDefaults.DecorationBox(
                    value = value.text,
                    innerTextField = it,
                    singleLine = maxLines == VALUE_1,
                    enabled = enabled,
                    leadingIcon = leadingIcon,
                    trailingIcon = trailingIcon,
                    visualTransformation = visualTransformation,
                    isError = isError,
                    label = label,
                    placeholder = {
                        /*val placeholderText = AnnotatedString.Builder().apply {
                            withStyle(
                                style = SpanStyle(color = SharemyTheme.colors.placeholder)
                            ) {
                                append(placeholder)
                            }
                        }.toAnnotatedString()

                        Text(
                            text = placeholderText,
                            style = placeholderStyle,
                            maxLines = VALUE_1,
                            textAlign = TextAlign.Start,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(horizontal = GRID_1)
                        )*/
                    },
                    interactionSource = interactionSource,
                    colors = OutlinedTextFieldDefaults.colors(),
                    container = {
                        OutlinedTextFieldDefaults.ContainerBox(
                            enabled,
                            isError,
                            interactionSource,
                            colors = TextFieldDefaults.colors(
                                /*focusedIndicatorColor = focusedBorderColor,
                                unfocusedIndicatorColor = unfocusedBorderColor,
                                disabledIndicatorColor = unfocusedBorderColor,
                                cursorColor = SharemyTheme.colors.colorCursor,
                                errorIndicatorColor = SharemyTheme.colors.errorColor,*/
                                focusedTextColor = textColor,
                                unfocusedTextColor = textColor,
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                disabledContainerColor = Color.White
                            ),
                            shape = RoundedCornerShape(GRID_16),
                            focusedBorderThickness = GRID_1,
                            unfocusedBorderThickness = GRID_1
                        )
                    }
                )
            }

            if (isError && !textError.isNullOrEmpty()) {
                Text(
                    modifier = Modifier.padding(top = GRID_6),
                    /*style = SharemyTheme.typography.editTextLabel.copy(
                        color = SharemyTheme.colors.errorColor
                    ),*/
                    text = textError,
                    maxLines = VALUE_1,
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }

    @Composable
    fun OtpCodeWidget(
        modifier: Modifier = Modifier,
        value: String,
        error: Int?,
        focusRequester: FocusRequester,
        onValueChange: (String) -> Unit,
        containerHeight: Dp = GRID_62,
        containerWidth: Dp = GRID_60,
        digitCount: Int = OTP_CODE_LENGTH
    ) {
        var isFocused by remember {
            mutableStateOf(false)
        }

        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BasicTextField(
                value = value,
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .onFocusChanged {
                        isFocused = it.isFocused
                    },
                cursorBrush = SolidColor(Color.Black),
                onValueChange = onValueChange,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                decorationBox = {
                    Row(horizontalArrangement = Arrangement.SpaceBetween) {
                        repeat(digitCount) { index ->
                            DigitView(
                                index = index,
                                value = value,
                                containerHeight = containerHeight,
                                containerWidth = containerWidth
                            )
                        }
                    }
                }
            )

            if (error != null) {
                SpacerVertical(GRID_24)

                Text(
                    text = stringResource(id = R.string.app_name), // TODO: update
                    /*style = SharemyTheme.typography.otpCodeInputError,*/
                    maxLines = VALUE_1,
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }

    @Composable
    private fun DigitView(
        index: Int,
        value: String,
        containerHeight: Dp,
        containerWidth: Dp
    ) {
        val isEmptyPin = index >= value.length
        val text = if (isEmptyPin) EMPTY else value[index].toString()

        val backgroundColor = Color.White

        val borderColor = when {
            text.isEmpty() -> WriterMeTheme.colors.primaryBackground // TODO
            else -> Color.Black
        }

        val shape = RoundedCornerShape(GRID_15)

        Box(
            modifier = Modifier
                .background(
                    color = backgroundColor,
                    shape = shape
                )
                .border(
                    width = GRID_1,
                    color = borderColor,
                    shape = shape
                )
                .height(containerHeight)
                .width(containerWidth),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = WriterMeTheme.typography.editText // TODO
            )
        }
    }
}
