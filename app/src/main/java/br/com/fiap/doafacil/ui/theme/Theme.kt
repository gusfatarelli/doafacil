package br.com.fiap.doafacil.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    // Cores principais
    primary          = BluePrimary,       // botões primários, AppBar
    onPrimary        = PrimaryWhite,      // texto/ícone sobre primary
    primaryContainer = GreenBackground,   // containers com destaque suave
    onPrimaryContainer = DarkBlue,

    secondary        = LightGreen,        // ações secundárias, FAB
    onSecondary      = DarkBlue,
    secondaryContainer = Color(0xFFCCF4E3),
    onSecondaryContainer = DarkBlue,

    tertiary         = GreyText,          // elementos terciários / hints
    onTertiary       = PrimaryWhite,

    // Fundos
    background       = GreenBackground,
    onBackground     = DarkBlue,
    surface          = PrimaryWhite,
    onSurface        = DarkBlue,
    surfaceVariant   = DeepGrey,
    onSurfaceVariant = GreyText,

    // Erros
    error            = Color(0xFFE53935),
    onError          = PrimaryWhite,

    // Outline
    outline          = Color(0xFFD0D5D7),
    outlineVariant   = DeepGrey,
)

private val DarkColorScheme = darkColorScheme(
    primary          = DarkBluePrimary,
    onPrimary        = DarkTextPrimary,
    primaryContainer = DarkSurface,
    onPrimaryContainer = DarkTextPrimary,

    secondary        = DarkLightGreen,
    onSecondary      = DarkBackground,
    secondaryContainer = Color(0xFF1A3D2E),
    onSecondaryContainer = DarkLightGreen,

    tertiary         = DarkTextSecondary,
    onTertiary       = DarkBackground,

    background       = DarkBackground,
    onBackground     = DarkTextPrimary,
    surface          = DarkSurface,
    onSurface        = DarkTextPrimary,
    surfaceVariant   = DarkCard,
    onSurfaceVariant = DarkTextSecondary,

    error            = Color(0xFFCF6679),
    onError          = DarkBackground,

    outline          = DarkDivider,
    outlineVariant   = DarkDivider,
)

@Composable
fun DoafacilTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else      -> LightColorScheme
    }

    // Deixa a status bar com cor igual ao header
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view)
                .isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography  = Typography,
        content     = content
    )
}