package com.example.ecommerce.presentation.ui

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.ecommerce.data.model.LoginRequest
import com.example.ecommerce.presentation.viewModel.LoginViewModel
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

class LoginScreen : Screen {
    @SuppressLint("StateFlowValueCalledInComposition")
    @Composable
    override fun Content() {
        var email by remember { mutableStateOf(TextFieldValue("")) }
        var password by remember { mutableStateOf(TextFieldValue("")) }
        var passwordVisible by remember { mutableStateOf(false) }
        val passwordIcon = if (passwordVisible) Icons.Default.Close else Icons.Default.Done
        val context = LocalContext.current
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: LoginViewModel = hiltViewModel()

        // Set up back press handling
        val activity = context as? ComponentActivity// Set up back press handling
        DisposableEffect(Unit) {
            val onBackPressedCallback = object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // إنهاء التطبيق بدلاً من استخدام navigator.pop()
                    activity?.finish()
                }
            }
            // Register the callback
            activity?.onBackPressedDispatcher?.addCallback(onBackPressedCallback)
            // Clean up the callback when the Composable is removed from composition
            onDispose {
                onBackPressedCallback.remove()
            }
        }

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
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(50.dp))

            // Email TextField
            OutlinedTextField(
                value = email,
                onValueChange = { newEmail -> email = newEmail },
                label = { Text("Email", color = Color.Black) },
                placeholder = { Text("example@email.com", color = Color.Gray) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFF9800),
                    unfocusedBorderColor = Color(0xFFFF9800),
                    cursorColor = Color(0xFFFF9800)
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
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = passwordIcon,
                            contentDescription = if (passwordVisible) "Hide password" else "Show password",
                            tint = Color.Black
                        )
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFF9800),
                    unfocusedBorderColor = Color(0xFFFF9800),
                    cursorColor = Color(0xFFFF9800)
                )
            )
            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Forget Password",
                color = Color(0xFFFF9800),
                modifier = Modifier.clickable {
                    // Handle forget password action
                }
            )
            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    viewModel.login(
                        LoginRequest(email = email.text, password = password.text),
                        onSuccess = {
                            Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                            navigator.push(HomeScreen())
                        },
                        onError = {
                            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                        }
                    )
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800))
            ) {
                Text(text = "Login", color = Color.White, fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.height(30.dp))
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 30.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "Don't have an account? ", color = Color.Black)
                Text(
                    text = "Sign Up",
                    color = Color(0xFFFF9800),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        navigator.push(SignUpScreen())
                    }
                )
            }
        }
    }
}

