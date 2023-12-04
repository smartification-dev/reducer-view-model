package dev.smartification.feature.details.reducer

import dev.smartification.feature.details.DetailsAction.OnThumbsUpClick
import dev.smartification.feature.details.DetailsViewModel
import dev.smartification.reducers.HiltReducer
import dev.smartification.reducers.Reducer
import javax.inject.Inject

@HiltReducer(OnThumbsUpClick::class)
class OnThumbsUpClickReducer @Inject constructor() : Reducer<DetailsViewModel, OnThumbsUpClick> {

    override suspend fun DetailsViewModel.reduce(action: OnThumbsUpClick) {
        state { currentState ->
            currentState.copy(thumbsUpCount = currentState.thumbsUpCount + 1)
        }
    }
}