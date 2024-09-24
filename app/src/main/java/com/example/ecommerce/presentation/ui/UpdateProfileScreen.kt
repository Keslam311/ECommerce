package com.example.ecommerce.presentation.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.ecommerce.R
import com.example.ecommerce.data.model.ProfileDataClass
import com.example.ecommerce.presentation.viewModel.ChangeProfileViewModel
import com.example.ecommerce.presentation.viewModel.ProfileViewModel
import com.example.ecommerce.ui.theme.Poppins
import com.example.ecommerce.ui.theme.PrimaryColor
import com.example.ecommerce.ui.theme.SecondaryColor
import com.example.ecommerce.ui.theme.Shapes


class UpdateProfile(): Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current

        ChangeProfileScreen(
            onSuccess = {
                Toast.makeText(
                    context,
                    "Profile changed successfully",
                    Toast.LENGTH_SHORT
                ).show()
                navigator.pop()
            },
            onError = { errorMessage ->
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        )
    }
}
/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeProfileScreen(
    viewModel: ChangeProfileViewModel = hiltViewModel(),
    onSuccess: () -> Unit,
    onError: (String) -> Unit
) {

    val profileViewModel : ProfileViewModel = hiltViewModel()
    val profileResponse by profileViewModel.profileState.collectAsState()
    val navigator = LocalNavigator.currentOrThrow


    LaunchedEffect(Unit) {
        profileViewModel.getProfile { errorMessage ->
            Log.w("Error", "Error fetching profile: $errorMessage")
        }
    }
    profileResponse?.let {
        ProfileContent(profile = it.data, onBackClick = { navigator.pop() })
    }
    val isLoading by viewModel.isLoading.collectAsState()
    var name by remember { mutableStateOf(profileResponse?.data?.name ?: "") }
    var email by remember { mutableStateOf(profileResponse?.data?.email ?: "") }
    var phone by remember { mutableStateOf(profileResponse?.data?.phone ?: "") }
    val sharedPreferences = LocalContext.current.getSharedPreferences("MyPrefs", android.content.Context.MODE_PRIVATE)


    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back to Profile")
                    }
                },
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.updata),
                contentDescription = "",
                modifier = Modifier.size(240.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 20.dp),
                colors = TextFieldDefaults.textFieldColors(
                    SecondaryColor,
                    cursorColor = PrimaryColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = Shapes.medium,
                singleLine = true,
                placeholder = {
                    Text(text = "Name", color = Color.Gray)
                },
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = Poppins
                )
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 20.dp),
                colors = TextFieldDefaults.textFieldColors(
                    SecondaryColor,
                    cursorColor = PrimaryColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = Shapes.medium,
                singleLine = true,
                placeholder = {
                    Text(text = "Email", color = Color.Gray)
                },
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = Poppins
                )
            )

            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 20.dp),
                colors = TextFieldDefaults.textFieldColors(
                    SecondaryColor,
                    cursorColor = PrimaryColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = Shapes.medium,
                singleLine = true,
                placeholder = {
                    Text(text = "Phone", color = Color.Gray)
                },
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = Poppins
                )
            )




            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                        viewModel.changeProfile(
                            email=email,image = profileResponse?.data?.image?:"", name = name,
                            password = sharedPreferences.getString("Password", "")?:"", phone = phone,
                            onSuccess = {
                                onSuccess()
                            },
                            onError = onError
                        )
                },
                enabled = !isLoading,
                colors = ButtonDefaults.buttonColors(PrimaryColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 20.dp),
                contentPadding = PaddingValues(vertical = 14.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 2.dp
                ),
                shape = Shapes.medium
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                } else {
                    Text("Update Profile")
                }
            }
        }
    }
}
*/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeProfileScreen(
    viewModel: ChangeProfileViewModel = hiltViewModel(),
    onSuccess: () -> Unit,
    onError: (String) -> Unit
) {
    val profileViewModel: ProfileViewModel = hiltViewModel()
    val profileResponse by profileViewModel.profileState.collectAsState()
    val navigator = LocalNavigator.currentOrThrow
    val context = LocalContext.current

    // Fetch profile data only once when the screen is first displayed
    LaunchedEffect(Unit) {
        profileViewModel.getProfile { errorMessage ->
            Log.e("ChangeProfileScreen", "Error fetching profile: $errorMessage")
            onError("Failed to fetch profile data.")
        }
    }

    // Handle loading state
    val isLoading by viewModel.isLoading.collectAsState()
    var name by remember { mutableStateOf(profileResponse?.data?.name ?: "") }
    var email by remember { mutableStateOf(profileResponse?.data?.email ?: "") }
    var phone by remember { mutableStateOf(profileResponse?.data?.phone ?: "") }
    val sharedPreferences = context.getSharedPreferences("MyPrefs", android.content.Context.MODE_PRIVATE)

    // Profile Content
    profileResponse?.let { profileData ->
        ProfileContent(profile = profileData.data, onBackClick = { navigator.pop() })
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Back to Profile")
                        }
                    },
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.updata),
                    contentDescription = null,
                    modifier = Modifier.size(240.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Repeated OutlinedTextField code can be reduced to a function
                ProfileTextField(value = name, onValueChange = { name = it }, placeholder = "Name")
                ProfileTextField(value = email, onValueChange = { email = it }, placeholder = "Email")
                ProfileTextField(value = phone, onValueChange = { phone = it }, placeholder = "Phone")

                Spacer(modifier = Modifier.height(16.dp))

                UpdateProfileButton(
                    isLoading = isLoading,
                    onClick = {
                        viewModel.changeProfile(
                            email = email,
                            image = profileData.data.image ?: "",
                            name = name,
                            password = sharedPreferences.getString("Password", "") ?: "",
                            phone = phone,
                            onSuccess = { onSuccess() },
                            onError = { error ->
                                Log.e("ChangeProfileScreen", "Profile update failed: $error")
                                onError("Failed to update profile.")
                            }
                        )
                    }
                )
            }
        }
    } ?: run {
        // Handle the case where profileResponse is null, perhaps show a loading spinner or an error message
        CircularProgressIndicator()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTextField(value: String, onValueChange: (String) -> Unit, placeholder: String) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(top = 20.dp),
        colors = TextFieldDefaults.textFieldColors(
            SecondaryColor,
            cursorColor = PrimaryColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        shape = Shapes.medium,
        singleLine = true,
        placeholder = {
            Text(text = placeholder, color = Color.Gray)
        },
        textStyle = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = Poppins
        )
    )
}

@Composable
fun UpdateProfileButton(isLoading: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        enabled = !isLoading,
        colors = ButtonDefaults.buttonColors(PrimaryColor),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(top = 20.dp),
        contentPadding = PaddingValues(vertical = 14.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 2.dp
        ),
        shape = Shapes.medium
    ) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.size(24.dp))
        } else {
            Text("Update Profile")
        }
    }
}

