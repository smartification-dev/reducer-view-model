package dev.smartification.feature.details

sealed class DetailsAction {
    data object OnThumbsUpClick : DetailsAction()
}
