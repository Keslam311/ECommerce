package com.example.ecommerce

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.os.LocaleList
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import cafe.adriel.voyager.navigator.Navigator
import com.example.ecommerce.interceptor.AuthInterceptor
import com.example.ecommerce.presentation.ui.HomeScreen
import com.example.ecommerce.presentation.ui.Start
import com.example.ecommerce.util.PreferencesManager
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale
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
            val savedLanguage = PreferencesManager.getSelectedLanguage(this) ?: "en"
            setLocale(this, savedLanguage)

                if (sharedPreferences.getString("token", null) != null) {
                    authInterceptor.setToken(token ?: "")
                    Navigator(screen = HomeScreen())
                }else{
                    Navigator(screen = Start())
                }

        }
    }
    private fun setLocale(context: Context, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)

        // For API level 24 and above
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            val localeList = LocaleList(locale)
            LocaleList.setDefault(localeList)
            config.setLocales(localeList)
        }

        // Update the configuration
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }
}