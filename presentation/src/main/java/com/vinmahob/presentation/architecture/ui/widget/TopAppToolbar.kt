package com.vinmahob.presentation.architecture.ui.widget

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.vinmahob.presentation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppToolbar(
    title: String,
    content: @Composable (PaddingValues) -> Unit,
    onBackIconPressed: (() -> Unit)? = null,
    onCloseIconPressed: (() -> Unit)? = null
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    ToolbarTitle(title = title)
                },
                navigationIcon = {
                    if (onBackIconPressed != null) {
                        IconButton(onClick = { onBackIconPressed() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = stringResource(id = R.string.back_btn_content_description)
                            )
                        }
                    }
                },
                actions = {
                    if (onCloseIconPressed != null) {
                        IconButton(onClick = { onCloseIconPressed() }) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = stringResource(id = R.string.back_btn_content_description)
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            )
        }
    ) { innerPadding ->
        content(innerPadding)
    }
}

@Composable
private fun ToolbarTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineSmall
    )
}