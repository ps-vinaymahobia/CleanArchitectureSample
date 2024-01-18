package com.vinmahob.presentation.architecture.viewmodel.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vinmahob.presentation.architecture.viewmodel.usecase.UseCaseExecutorProvider
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<VIEW_STATE : ViewState, VIEW_INTENT : ViewIntent, SIDE_EFFECT : SideEffect>(
    useCaseExecutorProvider: UseCaseExecutorProvider
) : ViewModel() {
    private val _viewState = MutableStateFlow(value = this.initialState())
    val viewState = _viewState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<SIDE_EFFECT>()
    val sideEffect = _sideEffect.asSharedFlow()

    val viewIntent = Channel<VIEW_INTENT>(Channel.UNLIMITED)

    private val currentViewState: VIEW_STATE
        get() = viewState.value

    internal abstract fun initialState(): VIEW_STATE

    protected abstract fun handleViewIntent()

    protected val useCaseExecutor by lazy {
        useCaseExecutorProvider(viewModelScope)
    }

    protected fun updateViewState(newViewState: VIEW_STATE) {
        _viewState.value = newViewState
    }

    protected fun emitSideEffect(sideEffect: SIDE_EFFECT) {
        viewModelScope.launch {
            _sideEffect.emit(sideEffect)
        }
    }
}