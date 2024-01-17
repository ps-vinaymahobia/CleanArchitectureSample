package com.vinmahob.domain.architecture.usecase

import com.vinmahob.domain.architecture.exception.DomainException
import com.vinmahob.domain.architecture.exception.UnknownDomainException
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
        onException: (DomainException) -> Unit = {}, //default impl for the case if the operation doesn't need to process the result any further
    ) {
        coroutineScope.launch {
            try {
                useCase(input, onResult)
            } catch (ignore: CancellationException) {
                //ignore or print log - assuming these exceptions are intentional
            } catch (throwable: Throwable) {
                //we first check for a meaningful DomainException (likely thrown by repo or useCase else fallback to an UnknownDomain Exception
                onException(throwable as? DomainException ?: UnknownDomainException(throwable))
            }
        }
    }
}