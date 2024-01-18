package com.vinmahob.presentation.productlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.vinmahob.presentation.R
import com.vinmahob.presentation.productlist.model.ProductListItemPresentationModel
import com.vinmahob.presentation.productlist.model.ProductListPresentationModel
import com.vinmahob.presentation.productlist.model.ProductListViewIntent
import com.vinmahob.presentation.productlist.model.ProductListViewState
import com.vinmahob.presentation.productlist.viewmodel.ProductListViewModel
import com.vinmahob.presentation.architecture.theme.DEFAULT_PADDING_SIZE
import com.vinmahob.presentation.architecture.theme.DEFAULT_TEXT_PADDING_SIZE
import com.vinmahob.presentation.architecture.theme.SIZE_150DP
import com.vinmahob.presentation.architecture.theme.SIZE_200DP
import com.vinmahob.presentation.architecture.theme.SIZE_340DP
import com.vinmahob.presentation.architecture.ui.state.DefaultErrorState
import com.vinmahob.presentation.architecture.ui.state.DefaultIdleState
import com.vinmahob.presentation.architecture.ui.state.DefaultLoadingState
import com.vinmahob.presentation.architecture.ui.widget.ImageWidget
import com.vinmahob.presentation.architecture.ui.widget.TextWidget
import com.vinmahob.presentation.architecture.ui.widget.TopAppToolbar
import com.vinmahob.presentation.productlist.model.ProductListSideEffect
import kotlinx.coroutines.launch

const val LAUNCHED_EFFECT_KEY = "ProductList"

@Composable
fun ProductListRoute(
    onGoToItem: (Int) -> Unit,
    onCloseIconPressed: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProductListViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsState()
    var shouldCallLandingApi by rememberSaveable {
        mutableStateOf(true)
    }
    if (shouldCallLandingApi) {
        LaunchedEffect(LAUNCHED_EFFECT_KEY) {
            viewModel.viewIntent.send(ProductListViewIntent.LoadProductList)
            shouldCallLandingApi = false
        }
    }
    LaunchedEffect(LAUNCHED_EFFECT_KEY) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is ProductListSideEffect.NavigateToProductDetails -> {
                    onGoToItem(sideEffect.id)
                }
            }
        }
    }
    TopAppToolbar(
        title = stringResource(id = R.string.product_list_screen_title), content = { innerPadding ->
            ProductListScreen(
                uiState = state,
                modifier = modifier,
                innerPadding = innerPadding,
                viewModel = viewModel
            )
        }, onCloseIconPressed = onCloseIconPressed
    )
}

@Composable
internal fun ProductListScreen(
    modifier: Modifier,
    innerPadding: PaddingValues,
    uiState: ProductListViewState,
    viewModel: ProductListViewModel
) {
    Box(
        modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        when (uiState) {
            is ProductListViewState.Error -> {
                DefaultErrorState(modifier, uiState.error)
            }

            ProductListViewState.Idle -> DefaultIdleState(modifier)
            ProductListViewState.Loading -> DefaultLoadingState(modifier)

            is ProductListViewState.ProductListLoaded -> {
                ProductList(productList = uiState.productList, viewModel = viewModel)
            }
        }
    }
}

@Composable
private fun ProductList(
    productList: ProductListPresentationModel, viewModel: ProductListViewModel
) {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(), color = Color.White
        ) {
            Column {
                LazyVerticalGrid(columns = GridCells.Adaptive(minSize = SIZE_150DP), content = {
                    itemsIndexed(productList.productList) { _, product ->
                        ProductListItem(product, viewModel)
                    }
                })
            }
        }
    }
}

@Composable
private fun ProductListItem(
    product: ProductListItemPresentationModel, viewModel: ProductListViewModel
) {
    val scope = rememberCoroutineScope()
    OutlinedCard(modifier = Modifier
        .size(width = SIZE_340DP, height = SIZE_200DP)
        .padding(DEFAULT_PADDING_SIZE)
        .clickable {
            scope.launch {
                viewModel.viewIntent.send(ProductListViewIntent.OnProductItemClicked(product.id))
            }
        }) {
        ImageWidget(
            imageUrl = product.thumbnail,
            modifier = Modifier
                .fillMaxWidth()
                .height(SIZE_150DP)
                .padding(DEFAULT_PADDING_SIZE)
        )

        TextWidget(
            text = product.title,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(DEFAULT_TEXT_PADDING_SIZE)
        )
    }
}

