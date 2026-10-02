package com.lihan.core.presentation.designsystem.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext


private val DarkColorScheme = darkColorScheme(
    primary = GongchaDarkPrimary,
    onPrimary = GongchaDarkOnPrimary,
    primaryContainer = GongchaDarkPrimaryContainer,
    onPrimaryContainer = GongchaDarkOnPrimaryContainer,
    secondary = GongchaDarkSecondary,
    onSecondary = GongchaDarkOnSecondary,
    secondaryContainer = GongchaDarkSecondaryContainer,
    onSecondaryContainer = GongchaDarkOnSecondaryContainer,
    tertiary = GongchaDarkTertiary,
    onTertiary = GongchaDarkOnTertiary,
    tertiaryContainer = GongchaDarkTertiaryContainer,
    onTertiaryContainer = GongchaDarkOnTertiaryContainer,
    error = GongchaDarkError,
    onError = GongchaDarkOnError,
    errorContainer = GongchaDarkErrorContainer,
    onErrorContainer = GongchaDarkOnErrorContainer,
    background = GongchaDarkBackground,
    onBackground = GongchaDarkOnBackground,
    surface = GongchaDarkSurface,
    onSurface = GongchaDarkOnSurface,
    surfaceVariant = GongchaDarkSurfaceVariant,
    onSurfaceVariant = GongchaDarkOnSurfaceVariant,
    outline = GongchaDarkOutline,
    outlineVariant = GongchaDarkOutlineVariant
)

private val LightColorScheme = lightColorScheme(
    primary = GongchaLightPrimary,
    onPrimary = GongchaLightOnPrimary,
    primaryContainer = GongchaLightPrimaryContainer,
    onPrimaryContainer = GongchaLightOnPrimaryContainer,
    secondary = GongchaLightSecondary,
    onSecondary = GongchaLightOnSecondary,
    secondaryContainer = GongchaLightSecondaryContainer,
    onSecondaryContainer = GongchaLightOnSecondaryContainer,
    tertiary = GongchaLightTertiary,
    onTertiary = GongchaLightOnTertiary,
    tertiaryContainer = GongchaLightTertiaryContainer,
    onTertiaryContainer = GongchaLightOnTertiaryContainer,
    error = GongchaLightError,
    onError = GongchaLightOnError,
    errorContainer = GongchaLightErrorContainer,
    onErrorContainer = GongchaLightOnErrorContainer,
    background = GongchaLightBackground,
    onBackground = GongchaLightOnBackground,
    surface = GongchaLightSurface,
    onSurface = GongchaLightOnSurface,
    surfaceVariant = GongchaLightSurfaceVariant,
    onSurfaceVariant = GongchaLightOnSurfaceVariant,
    outline = GongchaLightOutline,
    outlineVariant = GongchaLightOutlineVariant
)

@Composable
fun GongchaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // 若要維持固定品牌視覺，建議預設改為 false，避免被 Android 12+ 的動態桌布取色覆蓋
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}