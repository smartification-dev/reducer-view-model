package dev.smartification.feature.list

sealed class ListAction {
    data object LoadNames : ListAction()
    data class OnNameClick(val name: String) : ListAction()
    data class OnAddClick(val name: String) : ListAction()
}