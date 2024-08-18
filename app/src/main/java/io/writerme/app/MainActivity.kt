package io.writerme.app

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import io.writerme.resources.themes.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import io.writerme.app.navigation.navigationLaunchGraph
import ru.alexgladkov.odyssey.compose.setup.OdysseyConfiguration
import ru.alexgladkov.odyssey.compose.setup.setNavigationContent
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // installSplashScreen(): TODO

        setContent {
            AppTheme {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        val configuration = OdysseyConfiguration(
                            canvas = this@MainActivity,
                            backgroundColor = Color.Transparent
                        )

                        setNavigationContent(
                            configuration = configuration,
                            onApplicationFinish = {
                                finishAffinity()
                            }
                        ) {
                            navigationLaunchGraph()
                        }
                    }
                }
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        val configuration = Configuration(resources.configuration)
        val locale = Locale.ENGLISH
        configuration.setLocale(locale)
        super.onConfigurationChanged(configuration)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(updateBaseContextLocale(base))
    }

    private fun updateBaseContextLocale(context: Context): Context? {
        val locale = Locale.ENGLISH
        Locale.setDefault(locale)
        return updateResourcesLocale(context, locale)
    }

    private fun updateResourcesLocale(context: Context, locale: Locale): Context? {
        val configuration = Configuration(context.resources.configuration)
        configuration.setLocale(locale)
        return context.createConfigurationContext(configuration)
    }
}
