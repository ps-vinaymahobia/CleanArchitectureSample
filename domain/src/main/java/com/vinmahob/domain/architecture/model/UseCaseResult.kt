package com.vinmahob.domain.architecture.model

sealed interface UseCaseResult<T> {
    data class OnSuccess<T>(val data: T) : UseCaseResult<T>
    data class OnError<T>(val throwable: Throwable) : UseCaseResult<T>
}