package com.lihan.core.presentation.designsystem.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

private val DarkColorScheme = darkColorScheme(
    primary = GongchaDarkPrimary,
    onPrimary = GongchaDarkOnPrimary,
    primaryContainer = GongchaDarkPrimaryContainer,
    onPrimaryContainer = GongchaDarkOnPrimaryContainer,
    inversePrimary = GongchaDarkInversePrimary,
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
    surfaceTint = GongchaDarkSurfaceTint,
    inverseSurface = GongchaDarkInverseSurface,
    inverseOnSurface = GongchaDarkInverseOnSurface,
    outline = GongchaDarkOutline,
    outlineVariant = GongchaDarkOutlineVariant,
    scrim = GongchaDarkScrim,
    surfaceDim = GongchaDarkSurfaceDim,
    surfaceBright = GongchaDarkSurfaceBright,
    surfaceContainerLowest = GongchaDarkSurfaceContainerLowest,
    surfaceContainerLow = GongchaDarkSurfaceContainerLow,
    surfaceContainer = GongchaDarkSurfaceContainer,
    surfaceContainerHigh = GongchaDarkSurfaceContainerHigh,
    surfaceContainerHighest = GongchaDarkSurfaceContainerHighest
)

private val LightColorScheme = lightColorScheme(
    primary = GongchaLightPrimary,
    onPrimary = GongchaLightOnPrimary,
    primaryContainer = GongchaLightPrimaryContainer,
    onPrimaryContainer = GongchaLightOnPrimaryContainer,
    inversePrimary = GongchaLightInversePrimary,
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
    surfaceTint = GongchaLightSurfaceTint,
    inverseSurface = GongchaLightInverseSurface,
    inverseOnSurface = GongchaLightInverseOnSurface,
    outline = GongchaLightOutline,
    outlineVariant = GongchaLightOutlineVariant,
    scrim = GongchaLightScrim,
    surfaceDim = GongchaLightSurfaceDim,
    surfaceBright = GongchaLightSurfaceBright,
    surfaceContainerLowest = GongchaLightSurfaceContainerLowest,
    surfaceContainerLow = GongchaLightSurfaceContainerLow,
    surfaceContainer = GongchaLightSurfaceContainer,
    surfaceContainerHigh = GongchaLightSurfaceContainerHigh,
    surfaceContainerHighest = GongchaLightSurfaceContainerHighest
)

/**
 * Artisanal Washi & Tea Design System Shapes
 * 以日系溫潤和紙與圓滾波霸珍珠為靈感的圓角設定
 */
val Shapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(32.dp)
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
        colorScheme = LightColorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}