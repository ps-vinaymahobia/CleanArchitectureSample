package com.vinmahob.domain.architecture.usecase

import com.vinmahob.domain.architecture.model.UseCaseResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

class UseCaseExecutor(
    private val coroutineScope: CoroutineScope
) {
    fun <INPUT, OUTPUT> execute(
        useCase: UseCase<INPUT, OUTPUT>,
        input: INPUT,
        onResult: (OUTPUT) -> Unit = {}, //default impl for the case if the operation doesn't need to process the result any further
        onException: (Throwable) -> Unit = {}, //default impl for the case if the operation doesn't need to process the result any further
    ) {
        coroutineScope.launch {
            try {
                when (val result = useCase(input)) {
                    is UseCaseResult.OnError -> onException(result.throwable)
                    is UseCaseResult.OnSuccess -> onResult(result.data)
                }
            } catch (ignore: CancellationException) {
                //ignore or print log - assuming these exceptions are intentional
            }
        }
    }
}