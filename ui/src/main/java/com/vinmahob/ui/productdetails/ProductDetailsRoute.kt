package com.vinmahob.ui.productdetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.vinmahob.presentation.productdetails.model.ProductDetailsPresentationModel
import com.vinmahob.presentation.productdetails.model.ProductDetailsViewIntent
import com.vinmahob.presentation.productdetails.model.ProductDetailsViewState
import com.vinmahob.presentation.productdetails.viewmodel.ProductDetailsViewModel
import com.vinmahob.ui.R
import com.vinmahob.ui.architecture.theme.DEFAULT_PADDING_SIZE
import com.vinmahob.ui.architecture.theme.SIZE_32DP
import com.vinmahob.ui.architecture.theme.SIZE_340DP
import com.vinmahob.ui.architecture.ui.state.DefaultErrorState
import com.vinmahob.ui.architecture.ui.state.DefaultIdleState
import com.vinmahob.ui.architecture.ui.state.DefaultLoadingState
import com.vinmahob.ui.architecture.ui.widget.ImageWidget
import com.vinmahob.ui.architecture.ui.widget.TextWidget
import com.vinmahob.ui.architecture.ui.widget.TopAppToolbar

const val LAUNCHED_EFFECT_KEY = "ProductDetails"

@Composable
fun ProductDetailsRoute(
    onGoBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProductDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsState()
    LaunchedEffect(LAUNCHED_EFFECT_KEY) {
        viewModel.viewIntent.send(ProductDetailsViewIntent.LoadSelectedProductDetails)
    }
    TopAppToolbar(
        title = stringResource(id = R.string.product_details_screen_title),
        content = { innerPadding ->
            ProductListScreen(uiState = state, modifier = modifier, innerPadding = innerPadding)
        },
        onBackIconPressed = onGoBack
    )
}

@Composable
internal fun ProductListScreen(
    modifier: Modifier, innerPadding: PaddingValues, uiState: ProductDetailsViewState
) {
    Box(
        modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        when (uiState) {
            is ProductDetailsViewState.Error -> DefaultErrorState(modifier, uiState.error)
            ProductDetailsViewState.Idle -> DefaultIdleState(modifier)
            ProductDetailsViewState.Loading -> DefaultLoadingState(modifier)
            is ProductDetailsViewState.ProductDetailsLoaded -> {
                ProductDetails(
                    modifier = modifier, productDetailsPresentationModel = uiState.productDetails
                )
            }
        }
    }
}

@Composable
private fun ProductDetails(
    modifier: Modifier, productDetailsPresentationModel: ProductDetailsPresentationModel
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Column(modifier = modifier.padding(SIZE_32DP)) {
            ImageWidget(
                imageUrl = productDetailsPresentationModel.images[0],
                modifier = modifier
                    .fillMaxWidth()
                    .height(SIZE_340DP)
                    .padding(DEFAULT_PADDING_SIZE)
            )

            TextWidget(
                text = productDetailsPresentationModel.title,
                style = MaterialTheme.typography.headlineLarge,
                modifier = modifier.padding(vertical = DEFAULT_PADDING_SIZE)
            )

            TextWidget(
                text = productDetailsPresentationModel.description,
                modifier = modifier.padding(vertical = DEFAULT_PADDING_SIZE)
            )
        }
    }
}