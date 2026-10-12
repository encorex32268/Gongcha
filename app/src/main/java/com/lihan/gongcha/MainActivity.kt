package com.lihan.gongcha

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lihan.core.presentation.designsystem.ui.theme.GongchaTheme
import com.lihan.gongcha.navigation.AuthGraph
import com.lihan.gongcha.navigation.rememberAppState
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition {
            !viewModel.state.value.isReady
        }

        enableEdgeToEdge()
        setContent {
            val state by viewModel.state.collectAsStateWithLifecycle()
            val appState = rememberAppState()
            GongchaTheme {
                if (state.isReady) {
                    NavRoot(
                        appState = appState,
                        state = state,
                        signOut = {
                            viewModel.signOut()
                            appState.navController.navigate(AuthGraph) {
                                popUpTo(appState.navController.graph.id) {
                                    inclusive = true
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}
