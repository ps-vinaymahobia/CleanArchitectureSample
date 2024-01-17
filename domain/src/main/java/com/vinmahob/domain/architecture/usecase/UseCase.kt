package com.vinmahob.domain.architecture.usecase

interface UseCase<REQUEST, RESULT> {
    suspend operator fun invoke(input: REQUEST, onResult: (RESULT) -> Unit)
}