package dev.smartification.reducers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class ReducerViewModel<State : Any, Action : Any>(
    initialState: State,
    private val reducers: Reducers<Action>,
) : ViewModel() {

    val state = MutableStateFlow(initialState)

    fun state(newState: (State) -> State) {
        state.update(newState)
    }

    fun action(newAction: Action) {
        viewModelScope.launch {
            with(reducers[newAction]) {
                reduce(newAction)
            }
        }
    }
}
