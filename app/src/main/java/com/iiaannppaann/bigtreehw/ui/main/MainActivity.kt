package com.iiaannppaann.bigtreehw.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iiaannppaann.bigtreehw.R
import com.iiaannppaann.bigtreehw.ui.theme.BigTreeHwTheme
import com.iiaannppaann.bigtreehw.ui.theme.BigTreeOrange
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val mainViewModel by viewModels<MainViewModel>()

            BigTreeHwTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { BigTreeTopBar(mainViewModel::onTopBurgerClick) },
                ) { innerPadding ->
                    val uiState by mainViewModel.uiState.collectAsStateWithLifecycle()
                    MainRoute(
                        modifier =
                        Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        uiState = uiState,
                        onAction = mainViewModel::onUiAction,
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BigTreeTopBar(
    onBurgerClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = stringResource(R.string.top_bar_title),
                fontSize = 20.sp,
                color = BigTreeOrange,
                fontWeight = FontWeight.Bold
            )
        },
        actions = {
            IconButton(onClick = onBurgerClick) {
                Image(
                    painter = painterResource(id = R.drawable.burger),
                    contentDescription = null,
                )
            }
        },
    )
}
