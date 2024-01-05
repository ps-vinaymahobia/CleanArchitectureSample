package com.vinmahob.ui.productdetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.vinmahob.presentation.productdetails.model.ProductDetailsPresentationModel
import com.vinmahob.presentation.productdetails.model.ProductDetailsViewIntent
import com.vinmahob.presentation.productdetails.model.ProductDetailsViewState
import com.vinmahob.presentation.productdetails.viewmodel.ProductDetailsViewModel

@Composable
fun ProductDetailsRoute(
    onGoBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProductDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsState()
    LaunchedEffect(UInt) {
        viewModel.viewIntent.send(ProductDetailsViewIntent.LoadSelectedProductDetails)
    }
    ProductListScreen(uiState = state, modifier = modifier)
}

@Composable
internal fun ProductListScreen(
    modifier: Modifier,
    uiState: ProductDetailsViewState
) {
    Box(modifier.fillMaxSize()) {
        when (uiState) {
            is ProductDetailsViewState.Error -> TODO()
            ProductDetailsViewState.Idle -> {}
            ProductDetailsViewState.Loading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
            is ProductDetailsViewState.ProductDetailsLoaded -> {
                ProductDetails(
                    modifier = modifier,
                    productDetailsPresentationModel = uiState.productDetails
                )
            }
        }
    }
}

@Composable
private fun ProductDetails(
    modifier: Modifier,
    productDetailsPresentationModel: ProductDetailsPresentationModel
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = modifier.padding(32.dp)) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(productDetailsPresentationModel.images[0])
                        .crossfade(true)
                        .build()
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
            Button(
                modifier = modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 10.dp),
                onClick = {}
            ) {
                Text(text = "Buy Now")
            }
        }
    }
}