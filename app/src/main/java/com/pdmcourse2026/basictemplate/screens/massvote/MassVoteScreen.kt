package com.pdmcourse2026.basictemplate.screens.massvote

import androidx.compose.foundation.lazy.items
import com.pdmcourse2026.basictemplate.screens.home.HomeViewModel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pdmcourse2026.basictemplate.component.VotingOption

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MassVoteScreen(
    onNavigateToResults: () -> Unit,
    viewModel: MassVoteViewModel = viewModel()
) {
    val options by viewModel.options.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()
    val refresh by viewModel.refresh.collectAsState()
    val votedOptionId by viewModel.votedOptionId.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { Text("RankeUCA • Votá") },
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (error != null) {
                Text(
                    text = error ?: "",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    textAlign = TextAlign.Center
                )
            }

            PullToRefreshBox(
                isRefreshing = refresh,
                onRefresh = { viewModel.refreshResults() },
                modifier = Modifier.weight(1f)
            ) {
                if (options.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        if (loading) {
                            CircularProgressIndicator()
                        } else if (error != null) {
                            Button(onClick = { viewModel.loadOptions() }) {
                                Text("Reintentar")
                            }
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        item {
                            Text(
                                text = "↻ deslizá para actualizar",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF4CAF50),
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                        items(options) { option ->
                            VotingOption(
                                option = option,
                                selected = votedOptionId == option.id,
                                loading = loading && votedOptionId == null && error == null,
                                showVotes = false,
                                onClick = {
                                    viewModel.selectItem(option.id)
                                }
                            )
                        }

                        item {
                            Text(
                                text = "(sin mostrar votos)",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    }
                }
            }

            Button(
                onClick = {
                    onNavigateToResults()
                    viewModel.resetSelection()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = votedOptionId != null
            ) {
                Text("Ir a resultados →")
            }
        }
    }
}
