package com.vinmahob.presentation.architecture.viewmodel.usecase

import com.vinmahob.domain.architecture.usecase.UseCaseExecutor
import kotlinx.coroutines.CoroutineScope


typealias UseCaseExecutorProvider = @JvmSuppressWildcards (coroutineScope: CoroutineScope) -> UseCaseExecutor