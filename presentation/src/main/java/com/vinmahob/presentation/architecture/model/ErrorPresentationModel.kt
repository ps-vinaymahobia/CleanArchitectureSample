package com.vinmahob.presentation.architecture.model

sealed interface ErrorPresentationModel {
    data object NetworkNotAvailable : ErrorPresentationModel
    data object Unknown : ErrorPresentationModel
}