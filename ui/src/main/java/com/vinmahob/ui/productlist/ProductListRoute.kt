package com.vinmahob.ui.productlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.vinmahob.presentation.productlist.model.ProductListItemPresentationModel
import com.vinmahob.presentation.productlist.model.ProductListPresentationModel
import com.vinmahob.presentation.productlist.model.ProductListViewIntent
import com.vinmahob.presentation.productlist.model.ProductListViewState
import com.vinmahob.presentation.productlist.viewmodel.ProductListViewModel

@Composable
fun ProductListRoute(
    onGoToItem: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProductListViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsState()
    LaunchedEffect(UInt) {
        viewModel.viewIntent.send(ProductListViewIntent.LoadProductList)
    }
    ProductListScreen(uiState = state, modifier = modifier, onGoToItem = onGoToItem)
}

@Composable
internal fun ProductListScreen(
    onGoToItem: (Int) -> Unit,
    modifier: Modifier,
    uiState: ProductListViewState
) {
    Box(modifier.fillMaxSize()) {
        when (uiState) {
            is ProductListViewState.Error -> TODO()
            ProductListViewState.Idle -> {}
            ProductListViewState.Loading -> CircularProgressIndicator(
                modifier = modifier.align(
                    Alignment.Center
                )
            )

            is ProductListViewState.ProductListLoaded -> {
                ProductList(productList = uiState.productList, onGoToItem = onGoToItem)
            }
        }
    }
}

@Composable
private fun ProductList(
    onGoToItem: (Int) -> Unit,
    productList: ProductListPresentationModel,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {
            Column {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 150.dp),
                    content = {
                        itemsIndexed(productList.productList) { _, product ->
                            ProductListItem(product, onGoToItem)
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun ProductListItem(
    product: ProductListItemPresentationModel,
    onGoToItem: (Int) -> Unit
) {
    OutlinedCard(
        modifier = Modifier
            .size(width = 340.dp, height = 200.dp)
            .padding(10.dp)
            .clickable { onGoToItem(product.id) }
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(product.thumbnail)
                    .crossfade(true)
                    .build()
            ),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(Dp(10F))
        )

        Text(
            text = product.title,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(5.dp)
        )
    }
}

