package dev.smartification.feature.list.reducer

import dev.smartification.feature.list.ListAction.OnNameClick
import dev.smartification.feature.list.ListViewModel
import dev.smartification.reducers.HiltReducer
import dev.smartification.reducers.Reducer
import javax.inject.Inject

@HiltReducer(OnNameClick::class)
class OnNameClickReducer @Inject constructor() : Reducer<ListViewModel, OnNameClick> {

    override suspend fun ListViewModel.reduce(action: OnNameClick) {
        navigateToDetails.emit(action.name)
    }
}