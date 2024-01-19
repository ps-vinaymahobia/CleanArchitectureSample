package com.vinmahob.domain.architecture.usecase

import com.vinmahob.domain.architecture.model.UseCaseResult

interface UseCase<REQUEST, RESULT> {
    suspend operator fun invoke(input: REQUEST): UseCaseResult<RESULT>
}