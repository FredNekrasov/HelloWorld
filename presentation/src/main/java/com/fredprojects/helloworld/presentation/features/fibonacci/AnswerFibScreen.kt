package com.fredprojects.helloworld.presentation.features.fibonacci

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.fredprojects.helloworld.presentation.core.*

@Composable
fun AnswerFibScreen(result: String, goBack: Action) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()), Arrangement.Center, Alignment.CenterHorizontally) {
        FredText(result)
        FredIconButton(
            goBack,
            Icons.AutoMirrored.Default.KeyboardArrowLeft,
            Modifier.border(ButtonDefaults.outlinedBorder).align(Alignment.Start),
            MaterialTheme.colors.onBackground
        )
    }
}