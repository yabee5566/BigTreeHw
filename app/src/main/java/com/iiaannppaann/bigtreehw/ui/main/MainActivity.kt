package com.iiaannppaann.bigtreehw.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iiaannppaann.bigtreehw.data.api.ExchangeReportApi
import com.iiaannppaann.bigtreehw.ui.theme.BigTreeHwTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BigTreeHwTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val mainViewModel by viewModels<MainViewModel>()
                    val uiState by mainViewModel.uiState.collectAsStateWithLifecycle()
                    MainRoute(
                        modifier =
                            Modifier
                                .padding(innerPadding)
                                .fillMaxSize(),
                        uiState = uiState,
                    )
                }
            }
        }
    }
}
