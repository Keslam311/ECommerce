package com.example.ecommerce

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import cafe.adriel.voyager.navigator.Navigator
import com.example.ecommerce.interceptor.AuthInterceptor
import com.example.ecommerce.presentation.ui.HomeScreen
import com.example.ecommerce.presentation.ui.LoginScreen
import com.example.ecommerce.presentation.ui.Start
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var authInterceptor: AuthInterceptor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
            val token = sharedPreferences.getString("token", null)
                if (sharedPreferences.getString("token", null) != null) {
                    authInterceptor.setToken(token ?: "")
                    Navigator(screen = HomeScreen())
                }else{
                    Navigator(screen = Start())
                }

        }
    }
}