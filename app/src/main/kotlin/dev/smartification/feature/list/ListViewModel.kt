package dev.smartification.feature.list

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.smartification.feature.list.ListAction.LoadNames
import dev.smartification.reducers.ReducerViewModel
import dev.smartification.reducers.Reducers
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    reducers: Reducers<ListAction>,
) : ReducerViewModel<ListState, ListAction>(ListState(), reducers) {

    val navigateToDetails = MutableSharedFlow<String>()

    init {
        action(LoadNames)
    }
}