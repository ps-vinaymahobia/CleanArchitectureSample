package com.vinmahob.ui.architecture.ui.widget

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.vinmahob.ui.R

@Composable
fun DefaultErrorState(modifier: Modifier) {
    Row (
        modifier = modifier
            .fillMaxSize()
    ){
        Text(
            text = stringResource(R.string.error_in_loading_the_content),
            textAlign = TextAlign.Center,
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.CenterVertically)
        )
    }
}
