package dev.smartification.feature.list

import dev.smartification.core.ui.Phase

data class ListState(
    val phase: Phase = Phase.UNKNOWN,
    val names: List<String> = emptyList(),
)
