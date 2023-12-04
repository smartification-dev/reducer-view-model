package dev.smartification.feature.list.reducer

import dev.smartification.core.ui.Phase
import dev.smartification.data.NamesRepository
import dev.smartification.feature.list.ListAction.LoadNames
import dev.smartification.feature.list.ListViewModel
import dev.smartification.reducers.HiltReducer
import dev.smartification.reducers.Reducer
import javax.inject.Inject

@HiltReducer(LoadNames::class)
class LoadNamesReducer @Inject constructor(
    private val repository: NamesRepository,
) : Reducer<ListViewModel, LoadNames> {

    override suspend fun ListViewModel.reduce(action: LoadNames) {
        state { it.copy(phase = Phase.LOADING) }
        runCatching { repository.names() }
            .onSuccess { names ->
                state { it.copy(phase = Phase.LOADED, names = names) }
            }
            .onFailure {
                state { it.copy(phase = Phase.ERROR) }
            }
    }
}