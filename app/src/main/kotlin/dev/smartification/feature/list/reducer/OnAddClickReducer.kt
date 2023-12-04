package dev.smartification.feature.list.reducer

import dev.smartification.core.ui.Phase
import dev.smartification.data.NamesRepository
import dev.smartification.feature.list.ListAction.OnAddClick
import dev.smartification.feature.list.ListViewModel
import dev.smartification.reducers.HiltReducer
import dev.smartification.reducers.Reducer
import javax.inject.Inject

@HiltReducer(OnAddClick::class)
class OnAddClickReducer @Inject constructor(
    private val repository: NamesRepository,
) : Reducer<ListViewModel, OnAddClick> {

    override suspend fun ListViewModel.reduce(action: OnAddClick) {
        state { it.copy(phase = Phase.LOADING) }
        runCatching { repository.add(action.name) }
            .onSuccess { names ->
                state { it.copy(phase = Phase.LOADED, names = names) }
            }
            .onFailure {
                state { it.copy(phase = Phase.ERROR) }
            }
    }
}