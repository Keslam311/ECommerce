package com.example.ecommerce.presentation.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.ecommerce.R
import com.example.ecommerce.data.model.LoginRequest
import com.example.ecommerce.interceptor.AuthInterceptor
import com.example.ecommerce.presentation.viewModel.LoginViewModel
import javax.inject.Inject
import javax.inject.Singleton

class LoginScreen : Screen {


    @SuppressLint("StateFlowValueCalledInComposition", "CommitPrefEdits", "SuspiciousIndentation")
    @Composable
    override fun Content() {
        var email by remember { mutableStateOf(TextFieldValue("")) }
        var password by remember { mutableStateOf(TextFieldValue("")) }
        var passwordVisible by remember { mutableStateOf(false) }
        val passwordIcon = if (passwordVisible) Icons.Default.Close else Icons.Default.Done
        val context = LocalContext.current
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: LoginViewModel = hiltViewModel()
        val sharedPreferences = context.getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val token = sharedPreferences.getString("token", null)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Welcome Back", fontSize = 26.sp, fontWeight = FontWeight.Bold)
            Text(
                text = "Sign in with your email or password\nor continue with social media.",
                color = Color.Gray, // Update to appropriate color
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(50.dp))
            // Email TextField
            OutlinedTextField(
                value = email,
                onValueChange = { newEmail -> email = newEmail },
                label = { Text("Email", color = Color.Black) },
                placeholder = { Text("example@email.com", color = Color.Gray) },
                textStyle = TextStyle(color = Color.Black),
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email, // Example icon
                        contentDescription = "Email Icon",
                        tint = Color.Black
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFF9800), // Set border color when focused
                    unfocusedBorderColor = Color(0xFFFF9800), // Set border color when not focused
                    cursorColor = Color(0xFFFF9800) // Cursor color
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Password TextField
            OutlinedTextField(
                value = password,
                onValueChange = { newPass -> password = newPass },
                label = { Text("Password", color = Color.Black) },
                placeholder = { Text("********", color = Color.Gray) },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                textStyle = TextStyle(color = Color.Black),
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = {
                        passwordVisible = !passwordVisible
                    }) {
                        Icon(
                            imageVector = passwordIcon, // Icon to toggle visibility
                            contentDescription = if (passwordVisible) "Hide password" else "Show password",
                            tint = Color.Black
                        )
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFF9800), // Set border color when focused
                    unfocusedBorderColor = Color(0xFFFF9800), // Set border color when not focused
                    cursorColor = Color(0xFFFF9800) // Cursor color
                )
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Row(horizontalArrangement = Arrangement.Start) {
                }
                Text(
                    text = "Forget Password",
                    style = TextStyle(textDecoration = TextDecoration.Underline),
                    modifier = Modifier.clickable {
                        // Forget Password
                    }
                )
            }
            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    viewModel.login(
                        LoginRequest(email = email.text, password = password.text),
                        onSuccess = {
                            Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT)
                                .show()
                            navigator.push(HomeScreen())
                            sharedPreferences.edit().putString("Password", password.text).apply()
                        },
                        onError = {
                            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                        }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF9800)
                )
            ) {
                Text(text = "Login", color = Color.White, fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.height(30.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "Don't have an account? ", color = Color.Black)
                Text(
                    text = "Sign Up",
                    color = Color(0xFFFF9800),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        navigator.push(SignUpScreen())
                    })
            }
        }

    }
}

