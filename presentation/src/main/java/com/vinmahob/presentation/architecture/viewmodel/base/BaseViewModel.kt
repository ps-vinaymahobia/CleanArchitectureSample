package com.vinmahob.presentation.architecture.viewmodel.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vinmahob.presentation.architecture.viewmodel.usecase.UseCaseExecutorProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel<VIEW_STATE : Any>(
    useCaseExecutorProvider: UseCaseExecutorProvider
) : ViewModel() {
    private val _viewState = MutableStateFlow(value = this.initialState())
    val viewState = _viewState.asStateFlow()

    private val currentViewState: VIEW_STATE
        get() = viewState.value

    internal abstract fun initialState(): VIEW_STATE

    internal val useCaseExecutor by lazy {
        useCaseExecutorProvider(viewModelScope)
    }

    internal fun updateViewState(newViewState: VIEW_STATE) {
        _viewState.value = newViewState
    }

    internal fun updateViewState(updatedState: VIEW_STATE.() -> VIEW_STATE) = updateViewState(currentViewState.updatedState())

}