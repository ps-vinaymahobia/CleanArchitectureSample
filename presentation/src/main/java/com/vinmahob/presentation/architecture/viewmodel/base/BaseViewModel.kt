package com.vinmahob.presentation.architecture.viewmodel.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vinmahob.presentation.architecture.viewmodel.usecase.UseCaseExecutorProvider
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel<VIEW_STATE : ViewState, VIEW_INTENT : ViewIntent>(
    useCaseExecutorProvider: UseCaseExecutorProvider
) : ViewModel() {
    private val _viewState = MutableStateFlow(value = this.initialState())
    val viewState = _viewState.asStateFlow()

    val viewIntent = Channel<VIEW_INTENT>(Channel.UNLIMITED)

    private val currentViewState: VIEW_STATE
        get() = viewState.value

    internal abstract fun initialState(): VIEW_STATE

    internal abstract fun handleViewIntent() //protected?

    internal val useCaseExecutor by lazy {
        useCaseExecutorProvider(viewModelScope)
    }

    internal fun updateViewState(newViewState: VIEW_STATE) {
        _viewState.value = newViewState
    }

    var name : String? = null

    fun test(){
        if(name != null){
            name += "1"
        }

        name?.let {
            name += "1"
        }
    }

    internal fun updateViewState(updatedState: VIEW_STATE.() -> VIEW_STATE) =
        updateViewState(currentViewState.updatedState())

}