package dev.smartification.feature.list.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.smartification.core.ui.Phase
import dev.smartification.feature.list.ListAction
import dev.smartification.feature.list.ListAction.OnAddClick
import dev.smartification.feature.list.ListAction.OnNameClick
import dev.smartification.feature.list.ListState

@Composable
fun ListContent(state: ListState, action: (ListAction) -> Unit) {
    Column {
        AddName(onAddClick = { action(OnAddClick(it)) })
        Names(state, onNameClick = { action(OnNameClick(it)) })
    }
}

@Composable
private fun AddName(onAddClick: (String) -> Unit) {
    var newName by remember { mutableStateOf("") }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = newName,
            onValueChange = { newName = it },
            placeholder = { Text("New name") }
        )
        Button(onClick = {
            onAddClick(newName)
            newName = ""
        }) {
            Text("Add")
        }
    }
}

@Composable
private fun Names(state: ListState, onNameClick: (String) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(state.names) { name ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 56.dp)
                    .clickable { onNameClick(name) }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(name)
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ListPreview() {
    ListContent(ListState(phase = Phase.LOADED, names = listOf("John"))) {}
}