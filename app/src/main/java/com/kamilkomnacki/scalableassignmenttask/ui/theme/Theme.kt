package com.kamilkomnacki.scalableassignmenttask.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val ScalableColorScheme = darkColorScheme(
    primary = ScalableColors.primary,
    onPrimary = ScalableColors.onPrimary,
    primaryContainer = ScalableColors.surfaceTinted,
    onPrimaryContainer = ScalableColors.textPrimary,
    background = ScalableColors.background,
    onBackground = ScalableColors.textPrimary,
    surface = ScalableColors.surface,
    onSurface = ScalableColors.textPrimary,
    surfaceVariant = ScalableColors.surface,
    onSurfaceVariant = ScalableColors.textSecondary,
    // Matching the surface keeps tonal elevation from changing the supplied neutral color.
    surfaceTint = ScalableColors.surface,
    error = ScalableColors.negative,
    onError = ScalableColors.onNegative,
    errorContainer = ScalableColors.negative,
    onErrorContainer = ScalableColors.onNegative,
    outline = ScalableColors.textSecondary,
    outlineVariant = ScalableColors.textSecondary,
    scrim = ScalableColors.background,
    surfaceContainer = ScalableColors.surface,
    surfaceContainerHigh = ScalableColors.surface,
    surfaceContainerHighest = ScalableColors.surface,
    surfaceContainerLow = ScalableColors.surface,
    surfaceContainerLowest = ScalableColors.background,
)

@Composable
fun ScalableAssignmentTaskTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = ScalableColorScheme,
        typography = Typography,
        content = content
    )
}
