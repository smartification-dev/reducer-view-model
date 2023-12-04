package dev.smartification.reducers

interface Reducer<VM : ReducerViewModel<out Any, out Any>, T : Any> {
    suspend fun VM.reduce(action: T)
}
