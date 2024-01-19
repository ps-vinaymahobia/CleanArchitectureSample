package com.vinmahob.domain.architecture.usecase

import com.vinmahob.domain.architecture.coroutine.CoroutineContextProvider
import com.vinmahob.domain.architecture.model.UseCaseResult
import kotlinx.coroutines.withContext

abstract class BackgroundExecutingUseCase<REQUEST, RESULT>(
    private val coroutineContextProvider: CoroutineContextProvider
) : UseCase<REQUEST, RESULT> {
    final override suspend fun invoke(     //function is final to prevent child useCase from overriding it
        input: REQUEST,
    ): UseCaseResult<RESULT> {
        val result = withContext(coroutineContextProvider.io) {
            executeInBackground(input)
        }
        return result
    }

    abstract suspend fun executeInBackground(
        request: REQUEST
    ): UseCaseResult<RESULT>
}