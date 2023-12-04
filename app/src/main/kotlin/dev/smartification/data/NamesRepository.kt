package dev.smartification.data

import kotlinx.coroutines.delay
import javax.inject.Inject

class NamesRepository @Inject constructor() {

    private val names = mutableListOf(
        "John",
        "Sophia",
        "George",
        "Emma",
    )

    suspend fun names(): List<String> {
        delay(1000)
        return names
    }

    suspend fun add(name: String): List<String> {
        delay(1000)
        names += name
        return names
    }
}