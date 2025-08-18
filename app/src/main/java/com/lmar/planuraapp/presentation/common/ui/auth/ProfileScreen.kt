package com.lmar.planuraapp.presentation.common.ui.auth

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.lmar.planuraapp.R
import com.lmar.planuraapp.core.ui.theme.PlanuraAppTheme
import com.lmar.planuraapp.core.utils.Constants.PHOTO_SIZE
import com.lmar.planuraapp.domain.model.User
import com.lmar.planuraapp.presentation.common.components.FormTextField
import com.lmar.planuraapp.presentation.common.components.GlowingCard
import com.lmar.planuraapp.presentation.common.components.GradientButton
import com.lmar.planuraapp.presentation.common.components.GradientCircleImage
import com.lmar.planuraapp.presentation.common.components.HeadingTextComponent
import com.lmar.planuraapp.presentation.common.components.Loading
import com.lmar.planuraapp.presentation.common.components.NormalTextComponent
import com.lmar.planuraapp.presentation.common.event.ProfileEvent
import com.lmar.planuraapp.presentation.common.state.ProfileState
import com.lmar.planuraapp.presentation.common.viewmodel.auth.ProfileViewModel
import com.lmar.planuraapp.presentation.navigation.handleUiEvents
import com.lmar.planuraapp.presentation.ui.component.ScreenScaffold

@Composable
fun ProfileScreenContainer(
    navController: NavHostController,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val profileState by profileViewModel.profileState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        navController.handleUiEvents(
            scope = coroutineScope,
            uiEventFlow = profileViewModel.eventFlow
        )
    }

    ProfileScreen(
        profileState,
        onEvent = { event ->
            profileViewModel.onEvent(event)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileScreen(
    profileState: ProfileState = ProfileState(),
    onEvent: (ProfileEvent) -> Unit = {}
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        onEvent(ProfileEvent.EnteredImageUri(uri!!))
    }

    ScreenScaffold(
        title = "Perfil"
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp, vertical = 16.dp),
        ) {
            Spacer(modifier = Modifier.size(16.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (profileState.isAuthenticated) {
                    //Información del Usuario
                    Box {
                        if (profileState.imageUri == null) {
                            profileState.user.imageUrl.let { imageUrl ->
                                if (imageUrl.isEmpty()) {
                                    GradientCircleImage(
                                        painter = painterResource(R.drawable.default_avatar),
                                        imageSize = PHOTO_SIZE,
                                        strokeWidth = 6.dp
                                    )
                                } else {
                                    GradientCircleImage(
                                        imageUrl = imageUrl,
                                        imageSize = PHOTO_SIZE,
                                        strokeWidth = 6.dp
                                    )
                                }
                            }
                        } else {
                            GradientCircleImage(
                                painter = rememberAsyncImagePainter(profileState.imageUri),
                                imageSize = PHOTO_SIZE,
                                strokeWidth = 6.dp
                            )
                        }

                        if (profileState.isShowingForm) {
                            IconButton(
                                onClick = { launcher.launch("image/*") },
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.tertiary)
                                    .align(Alignment.CenterEnd)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CameraAlt,
                                    contentDescription = "Camara",
                                    tint = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.size(8.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically, // Alinea los elementos verticalmente
                        horizontalArrangement = Arrangement.SpaceBetween // Distribuye los elementos en la fila
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            HeadingTextComponent(
                                value = profileState.user.names,
                                textColor = MaterialTheme.colorScheme.tertiary,
                                fontSize = 16.sp
                            )

                            NormalTextComponent(
                                value = profileState.user.email, fontSize = 14.sp
                            )
                        }

                        if (!profileState.isShowingForm) {
                            IconButton(
                                onClick = { onEvent(ProfileEvent.ShowForm(true)) },
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Edit",
                                    tint = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        } else {
                            IconButton(
                                onClick = { onEvent(ProfileEvent.ShowForm(false)) },
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.tertiary)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Edit",
                                    tint = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    if (profileState.isShowingForm) {
                        //Formulario
                        FormTextField(
                            value = profileState.user.names,
                            label = "Nombres",
                            icon = Icons.Default.Person,
                            onValueChange = {
                                onEvent(ProfileEvent.EnteredNames(it))
                            },
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Done
                            )
                        )
                    }
                } else {
                    //Default
                    GlowingCard(
                        modifier = Modifier
                            .size(PHOTO_SIZE)
                            .padding(5.dp),
                        glowingColor = MaterialTheme.colorScheme.tertiary,
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        cornerRadius = Int.MAX_VALUE.dp
                    ) {
                        Image(
                            painter = painterResource(R.drawable.default_avatar),
                            contentDescription = "Logo",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(PHOTO_SIZE)
                                .clip(CircleShape)
                                .border(5.dp, MaterialTheme.colorScheme.tertiary, CircleShape),
                        )
                    }

                    Spacer(modifier = Modifier.size(8.dp))

                    NormalTextComponent(
                        "¡Inicia sesión y juega en línea con tus amigos!", fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.size(18.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                if (profileState.isShowingForm) {
                    Spacer(modifier = Modifier.size(4.dp))

                    Button(
                        onClick = {
                            onEvent(ProfileEvent.SaveForm)
                            onEvent(ProfileEvent.ShowForm(false))
                        },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                        modifier = Modifier.width(200.dp)
                    ) {
                        Text("Guardar")
                    }
                }

                Spacer(modifier = Modifier.size(4.dp))

                if (profileState.isAuthenticated) {
                    GradientButton(
                        text = "Cerrar Sesión",
                        onClick = { onEvent(ProfileEvent.SignOut) },
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    GradientButton(
                        text = "Iniciar Sesión",
                        onClick = { onEvent(ProfileEvent.ToLogin) },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.size(4.dp))

                    GradientButton(
                        text = "Registrarse",
                        onClick = { onEvent(ProfileEvent.ToSignUp) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.size(32.dp))
        }
    }

    if (profileState.isLoading) {
        Loading()
    }

}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    PlanuraAppTheme {
        ProfileScreen(
            ProfileState(
                isAuthenticated = true,
                user = User(id = "01", names = "Test 01", email = "test01@gmail.com", score = 100)
            )
        )
    }
}