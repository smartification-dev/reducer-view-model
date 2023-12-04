package dev.smartification.feature.list.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import dagger.hilt.android.AndroidEntryPoint
import dev.smartification.core.ui.FullscreenError
import dev.smartification.core.ui.FullscreenLoading
import dev.smartification.core.ui.Phase
import dev.smartification.feature.details.ui.DetailsActivity
import dev.smartification.feature.list.ListViewModel

@AndroidEntryPoint
class ListActivity : ComponentActivity() {

    private val viewModel: ListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                LaunchedEffect(Unit) {
                    viewModel.navigateToDetails.collect { navigateToDetails(it) }
                }
                val state by viewModel.state.collectAsState()
                when (state.phase) {
                    Phase.LOADING -> FullscreenLoading()
                    Phase.LOADED -> ListContent(state, viewModel::action)
                    Phase.ERROR -> FullscreenError()
                    Phase.UNKNOWN -> Unit
                }
            }
        }
    }

    private fun navigateToDetails(name: String) {
        val intent = Intent(this@ListActivity, DetailsActivity::class.java)
            .putExtra("name", name)
        startActivity(intent)
    }
}