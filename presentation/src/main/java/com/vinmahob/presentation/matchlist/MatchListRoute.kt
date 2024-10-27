package com.vinmahob.presentation.matchlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.vinmahob.presentation.R
import com.vinmahob.presentation.architecture.theme.DEFAULT_PADDING_SIZE
import com.vinmahob.presentation.architecture.theme.DEFAULT_TEXT_PADDING_SIZE
import com.vinmahob.presentation.architecture.theme.SIZE_200DP
import com.vinmahob.presentation.architecture.theme.SIZE_340DP
import com.vinmahob.presentation.architecture.ui.state.DefaultErrorState
import com.vinmahob.presentation.architecture.ui.state.DefaultIdleState
import com.vinmahob.presentation.architecture.ui.state.DefaultLoadingState
import com.vinmahob.presentation.architecture.ui.widget.TextWidget
import com.vinmahob.presentation.architecture.ui.widget.TopAppToolbar
import com.vinmahob.presentation.matchlist.model.MatchListItemPresentationModel
import com.vinmahob.presentation.matchlist.model.MatchListPresentationModel
import com.vinmahob.presentation.matchlist.model.MatchListSideEffect
import com.vinmahob.presentation.matchlist.model.MatchListViewIntent
import com.vinmahob.presentation.matchlist.model.MatchListViewState
import com.vinmahob.presentation.matchlist.viewmodel.MatchListViewModel
import kotlinx.coroutines.launch

const val LAUNCHED_EFFECT_MATCH_LIST_KEY = "MatchList"

@Composable
fun MatchListRoute(
    onGoToItem: (String) -> Unit,
    onCloseIconPressed: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MatchListViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsState()
    var shouldCallLandingApi by rememberSaveable {
        mutableStateOf(true)
    }
    if (shouldCallLandingApi) {
        LaunchedEffect(LAUNCHED_EFFECT_MATCH_LIST_KEY) {
            viewModel.viewIntent.send(MatchListViewIntent.LoadMatchList)
            shouldCallLandingApi = false
        }
    }
    LaunchedEffect(LAUNCHED_EFFECT_MATCH_LIST_KEY) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is MatchListSideEffect.NavigateToMatchDetails -> {
                    onGoToItem(sideEffect.id)
                }
            }
        }
    }
    TopAppToolbar(
        title = stringResource(id = R.string.product_list_screen_title), content = { innerPadding ->
            MatchListScreen(
                uiState = state,
                modifier = modifier,
                innerPadding = innerPadding,
                viewModel = viewModel
            )
        }, onCloseIconPressed = onCloseIconPressed
    )
}

@Composable
internal fun MatchListScreen(
    modifier: Modifier,
    innerPadding: PaddingValues,
    uiState: MatchListViewState,
    viewModel: MatchListViewModel
) {
    Box(
        modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        when (uiState) {
            is MatchListViewState.Error -> {
                DefaultErrorState(modifier, uiState.error)
            }

            MatchListViewState.Idle -> DefaultIdleState(modifier)
            MatchListViewState.Loading -> DefaultLoadingState(modifier)

            is MatchListViewState.MatchListLoaded -> {
                MatchList(
                    matchList = uiState.matchList,
                    viewModel = viewModel,
                    modifier = modifier
                )
            }
        }
    }
}

@Composable
private fun MatchList(
    matchList: MatchListPresentationModel,
    viewModel: MatchListViewModel,
    modifier: Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter
    ) {
        Surface(
            modifier = modifier.fillMaxSize(), color = Color.White
        ) {
            Column(
                modifier = modifier
            ) {
                LazyColumn {
                    itemsIndexed(matchList.matchList) { _, match ->
                        MatchListItem(match, viewModel, modifier)
                    }
                }
            }
        }
    }
}

@Composable
private fun MatchListItem(
    match: MatchListItemPresentationModel,
    viewModel: MatchListViewModel,
    modifier: Modifier
) {
    val scope = rememberCoroutineScope()
    OutlinedCard(modifier = modifier
        .fillMaxWidth()
        .padding(DEFAULT_PADDING_SIZE)
        .clickable {
            scope.launch {
                viewModel.viewIntent.send(MatchListViewIntent.OnMatchItemClicked(match.id))
            }
        }) {
        TextWidget(
            text = match.name,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(DEFAULT_TEXT_PADDING_SIZE)
        )
    }
}

