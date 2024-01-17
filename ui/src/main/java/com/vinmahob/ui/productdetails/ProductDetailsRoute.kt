package com.vinmahob.ui.productdetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.vinmahob.presentation.productdetails.model.ProductDetailsPresentationModel
import com.vinmahob.presentation.productdetails.model.ProductDetailsViewIntent
import com.vinmahob.presentation.productdetails.model.ProductDetailsViewState
import com.vinmahob.presentation.productdetails.viewmodel.ProductDetailsViewModel
import com.vinmahob.ui.R
import com.vinmahob.ui.architecture.ui.widget.DefaultErrorState
import com.vinmahob.ui.architecture.ui.widget.DefaultIdleState
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
            ProductDetailsViewState.Loading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
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
    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = modifier.padding(32.dp)) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(productDetailsPresentationModel.images[0]).crossfade(true).build()
                ),
                contentDescription = null,
                modifier = modifier
                    .fillMaxWidth()
                    .height(340.dp)
                    .padding(10.dp)
            )

            Text(
                text = productDetailsPresentationModel.title,
                style = MaterialTheme.typography.headlineLarge,
                modifier = modifier.padding(vertical = 10.dp)
            )
            Text(
                text = productDetailsPresentationModel.description,
                modifier = modifier.padding(vertical = 10.dp)
            )
            Button(modifier = modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 10.dp),
                onClick = {}) {
                Text(text = "Buy Now")
            }
        }
    }
}