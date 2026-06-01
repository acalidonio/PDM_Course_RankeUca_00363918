package com.pdmcourse2026.basictemplate.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pdmcourse2026.basictemplate.component.VotingOption
import com.pdmcourse2026.basictemplate.screens.AppScaffold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToResults: () -> Unit,
    //selectItem: () -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    val options by viewModel.options.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()
    val refreshing by viewModel.refresh.collectAsState()

    if (loading) {
        AppScaffold(title = "RankeUca - Vota") { padding ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(modifier = Modifier.padding(padding))
            }
        }
        return
    }

    if (error != null) {
        AppScaffold(title = "RankeUca - Vota") { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically)
            ) {
                Text(
                    text = "$error"
                )
                Button(onClick = { viewModel.loadOptions() }) {
                    Text(
                        text = "Reintentar",
                        color = Color.Black
                    )

                }
            }
        }
        return
    }

    AppScaffold(
        title = "RankeUca - Vota",
        bottomBarText = "Vota",
        onFabClick = navigateToResults,
    ) { padding ->
        PullToRefreshBox(
            isRefreshing = refreshing,
            onRefresh = { viewModel.refreshOptions() },
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                items(options) { option ->
                    Button(
                        onClick = { viewModel.selectItem(option.id) }
                    ) {
                        VotingOption(
                            option = option
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
            Button(
                onClick = navigateToResults
            ) {
                Text(
                    text = "Ver resultados",
                    color = Color.Black
                )
            }
        }
    }
}