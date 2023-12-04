package dev.smartification.reducers

import javax.inject.Inject
import javax.inject.Provider

class Reducers<Action : Any> @Inject constructor(
    private val reducers: Map<Class<out Action>, @JvmSuppressWildcards Provider<Reducer<*, *>>>,
) {

    @Suppress("UNCHECKED_CAST")
    operator fun get(action: Action): Reducer<ReducerViewModel<*, *>, Action> =
        reducers[action::class.java]!!.get() as Reducer<ReducerViewModel<*, *>, Action>
}
