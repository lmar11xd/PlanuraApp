package com.lmar.planuraapp.presentation.common.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.lmar.planuraapp.R
import com.lmar.planuraapp.core.ui.theme.PlanuraAppTheme
import com.lmar.planuraapp.presentation.common.components.AppBar
import com.lmar.planuraapp.presentation.common.components.DividerTextComponent
import com.lmar.planuraapp.presentation.common.components.FormCheckbox
import com.lmar.planuraapp.presentation.common.components.FormPasswordTextField
import com.lmar.planuraapp.presentation.common.components.FormTextField
import com.lmar.planuraapp.presentation.common.components.GradientButton
import com.lmar.planuraapp.presentation.common.components.NormalTextComponent
import com.lmar.planuraapp.presentation.common.components.ShadowText
import com.lmar.planuraapp.presentation.common.event.AuthEvent
import com.lmar.planuraapp.presentation.common.state.AuthState
import com.lmar.planuraapp.presentation.common.viewmodel.auth.AuthViewModel
import com.lmar.planuraapp.presentation.navigation.handleUiEvents

@Composable
fun SignUpScreenContainer(
    navController: NavHostController,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val authState by authViewModel.authState.collectAsState(AuthState())

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        navController.handleUiEvents(
            scope = coroutineScope,
            uiEventFlow = authViewModel.eventFlow
        )
    }

    SignUpScreen(
        authState,
        onEvent = {
            authViewModel.onEvent(it)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    authState: AuthState = AuthState(),
    onEvent: (AuthEvent) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg1),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )

        Column {
            AppBar(
                "Registrarse",
                onBackAction = { onEvent(AuthEvent.ToBack) },
                state = rememberTopAppBarState()
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ShadowText(
                        text = "Crear una Cuenta,",
                        fontFamily = MaterialTheme.typography.displayLarge.fontFamily!!,
                        fontSize = 32.sp,
                        textAlign = TextAlign.Start,
                        textColor = MaterialTheme.colorScheme.onPrimary,
                        shadowColor = MaterialTheme.colorScheme.primary
                    )

                    NormalTextComponent(
                        "¡Regístrate para comenzar!",
                        textAlign = TextAlign.Start,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(horizontal = 5.dp)
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                FormTextField(
                    value = authState.names,
                    label = "Nombres",
                    icon = Icons.Default.Person,
                    onValueChange = {
                        onEvent(AuthEvent.EnteredNames(it))
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                FormTextField(
                    value = authState.email,
                    label = "Correo",
                    icon = Icons.Default.Email,
                    onValueChange = {
                        onEvent(AuthEvent.EnteredEmail(it))
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                FormPasswordTextField(
                    value = authState.password,
                    label = "Contraseña",
                    icon = Icons.Default.Lock,
                    onValueChange = {
                        onEvent(AuthEvent.EnteredPassword(it))
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                FormPasswordTextField(
                    value = authState.confirmPassword,
                    label = "Contraseña",
                    icon = Icons.Default.Lock,
                    onValueChange = {
                        onEvent(AuthEvent.EnteredConfirmPassword(it))
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))
                FormCheckbox(text = stringResource(R.string.terms_and_conditions))

                Spacer(modifier = Modifier.height(24.dp))

                GradientButton(
                    text = "Registrarse",
                    onClick = { onEvent(AuthEvent.SignUp) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                DividerTextComponent()

                Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("¿Ya tienes una cuenta? ", color = MaterialTheme.colorScheme.onPrimary)
                    Text(
                        text = "Login",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .clickable {
                                onEvent(AuthEvent.ToLogin)
                            }
                    )
                }
            }
        }

    }

}

@Preview(showBackground = true)
@Composable
private fun SignUpScreenPreview() {
    PlanuraAppTheme { SignUpScreen() }
}