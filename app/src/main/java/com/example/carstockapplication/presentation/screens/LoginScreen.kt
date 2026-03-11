package com.example.carstockapplication.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.carstockapplication.presentation.viewmodel.AuthViewModel
import com.example.carstockapplication.ui.theme.*

@Composable
fun LoginScreen(
    viewModel: AuthViewModel,
    onLoginSuccess: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var visible by remember { mutableStateOf(false) }

    val isLoading by viewModel.isLoading.observeAsState(false)
    val error by viewModel.error.observeAsState(null)

    LaunchedEffect(Unit) { visible = true }

    val loginSuccess by viewModel.loginSuccess.observeAsState(false)
    LaunchedEffect(loginSuccess) {
        if (loginSuccess) onLoginSuccess()
    }

    LoginContent(
        email = email,
        password = password,
        passwordVisible = passwordVisible,
        isLoading = isLoading,
        error = error,
        onEmailChange = { email = it },
        onPasswordChange = { password = it },
        onPasswordVisibleChange = { passwordVisible = !passwordVisible },
        onLoginClick = { viewModel.login(email, password) }
    );

}

@Composable
fun LoginContent(
    email: String,
    password: String,
    passwordVisible: Boolean,
    isLoading: Boolean,
    error: String?,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordVisibleChange: () -> Unit,
    onLoginClick: () -> Unit
) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        // Gradient orb background
        Box(
            modifier = Modifier
                .size(400.dp)
                .offset(x = (-80).dp, y = (-60).dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            AccentOrange.copy(alpha = 0.15f),
                            Color.Transparent
                        )
                    )
                )
        )
        Box(
            modifier = Modifier
                .size(300.dp)
                .align(Alignment.BottomEnd)
                .offset(x = 60.dp, y = 80.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            AccentGold.copy(alpha = 0.10f),
                            Color.Transparent
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // ── Logo / título ──────────────────────────────────────────────
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(600)) + slideInVertically(tween(600)) { -40 }
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(18.dp))
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(AccentOrange, AccentGold),
                                    start = Offset(0f, 0f),
                                    end = Offset(100f, 100f)
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("CS", color = Color.White, fontWeight = FontWeight.Black, fontSize = 22.sp)
                    }

                    Spacer(Modifier.height(20.dp))

                    Text(
                        text = "CarStock",
                        color = TextPrimary,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = (-0.5).sp
                    )
                    Text(
                        text = "Gestão inteligente de estoque",
                        color = TextMuted,
                        fontSize = 13.sp,
                        letterSpacing = 0.3.sp
                    )
                }
            }

            Spacer(Modifier.height(48.dp))

            // ── Card do formulário ─────────────────────────────────────────
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(700, delayMillis = 200)) + slideInVertically(tween(700, delayMillis = 200)) { 60 }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                        .background(Surface1)
                        .padding(24.dp)
                ) {
                    Text(
                        text = "Entrar",
                        color = TextPrimary,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Acesse sua conta para continuar",
                        color = TextMuted,
                        fontSize = 13.sp
                    )

                    Spacer(Modifier.height(28.dp))

                    // Email
                    StyledTextField(
                        value = email,
                        onValueChange = onEmailChange,
                        label = "E-mail",
                        leadingIcon = {
                            Icon(Icons.Default.Email, contentDescription = null, tint = TextMuted, modifier = Modifier.size(18.dp))
                        },
                        keyboardType = KeyboardType.Email
                    )

                    Spacer(Modifier.height(14.dp))

                    // Senha
                    StyledTextField(
                        value = password,
                        onValueChange = onPasswordChange,
                        label = "Senha",
                        leadingIcon = {
                            Icon(Icons.Default.Lock, contentDescription = null, tint = TextMuted, modifier = Modifier.size(18.dp))
                        },
                        keyboardType = KeyboardType.Password,
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = onPasswordVisibleChange) {
                                Icon(
                                    if (passwordVisible) Icons.Default.Lock else Icons.Default.Check,
                                    contentDescription = null,
                                    tint = TextMuted,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    )

                    // Erro
                    AnimatedVisibility(visible = error != null) {
                        Text(
                            text = error ?: "",
                            color = Color(0xFFFF4D4D),
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

                    Spacer(Modifier.height(24.dp))

                    // Botão entrar
                    Button(
                        onClick = onLoginClick,
                        enabled = !isLoading && email.isNotBlank() && password.isNotBlank(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent
                        ),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    if (!isLoading && email.isNotBlank() && password.isNotBlank())
                                        Brush.linearGradient(listOf(AccentOrange, AccentGold))
                                    else
                                        Brush.linearGradient(listOf(Surface2, Surface2)),
                                    shape = RoundedCornerShape(14.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            if (isLoading) {
                                CircularProgressIndicator(
                                    color = Color.White,
                                    modifier = Modifier.size(22.dp),
                                    strokeWidth = 2.dp
                                )
                            } else {
                                Text(
                                    text = "Entrar",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    letterSpacing = 0.5.sp
                                )
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(800, delayMillis = 400))
            ) {
                Text(
                    text = "CarStock © 2026",
                    color = TextMuted.copy(alpha = 0.5f),
                    fontSize = 11.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}


@Preview
@Composable
fun LoginPreview() {
    LoginContent(
        email = "teste@email.com",
        password = "123456",
        passwordVisible = false,
        isLoading = false,
        error = null,
        onEmailChange = {},
        onPasswordChange = {},
        onPasswordVisibleChange = {},
        onLoginClick = {}
    )
}


@Composable
private fun StyledTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, fontSize = 13.sp) },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = AccentOrange,
            unfocusedBorderColor = BorderColor,
            focusedLabelColor = AccentOrange,
            unfocusedLabelColor = TextMuted,
            cursorColor = AccentOrange,
            focusedTextColor = TextPrimary,
            unfocusedTextColor = TextPrimary,
            focusedContainerColor = Surface2,
            unfocusedContainerColor = Surface2
        ),
        singleLine = true
    )
}