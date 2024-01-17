package com.vinmahob.presentation.architecture.ui.widget

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle

@Composable
fun TextWidget(
    text: String,
    style: TextStyle = LocalTextStyle.current,
    modifier: Modifier
) {
    Text(
        text = text,
        style = style,
        modifier = modifier
    )
}