package dev.smartification.feature.details

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.smartification.reducers.ReducerViewModel
import dev.smartification.reducers.Reducers
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    reducers: Reducers<DetailsAction>,
) : ReducerViewModel<DetailsState, DetailsAction>(DetailsState(name = savedStateHandle["name"]!!), reducers)
