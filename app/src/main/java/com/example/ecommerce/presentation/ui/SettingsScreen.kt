package com.example.ecommerce.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import android.widget.Toast
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.ecommerce.ui.theme.LightPrimaryColor
import com.example.ecommerce.ui.theme.Poppins
import com.example.ecommerce.ui.theme.PrimaryColor
import com.example.ecommerce.ui.theme.SecondaryColor
import com.example.ecommerce.ui.theme.Shapes
import com.example.ecommerce.R
import com.example.ecommerce.presentation.viewModel.LogoutViewModel
import com.example.ecommerce.presentation.viewModel.ProfileViewModel

class Settings : Screen {
    @Composable
    override fun Content() {
        SettingsScreen()
    }
}
@Composable
fun SettingsScreen() {
    Column() {
        HeaderWithBackButton()
        ProfileCardUI()
        GeneralOptionsUI()
        SupportOptionsUI()
    }
}


@Composable
fun HeaderWithBackButton() {
    val navigator = LocalNavigator.currentOrThrow
    Spacer(modifier = Modifier.height(25.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
        ,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            modifier = Modifier
                .size(25.dp)
                .clickable(
                    indication = null, // إخفاء تأثير الضغط الافتراضي
                    interactionSource = remember { MutableInteractionSource() }
                    ,onClick = {
                    navigator.pop()
                })
        )
    }
}

@Composable
fun ProfileCardUI() {
    val navigator = LocalNavigator.currentOrThrow
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(10.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(0.dp),
        shape = Shapes.large
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier.padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column() {
                Text(
                    text = "Check Your Profile",
                    fontFamily = Poppins,
                    color = SecondaryColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )

                Button(
                    modifier = Modifier.padding(top = 10.dp),
                    onClick = {
                        navigator.push(ProfileScreen())
                    },
                    colors = ButtonDefaults.buttonColors(
                          PrimaryColor
                    ),
                    contentPadding = PaddingValues(horizontal = 30.dp),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 2.dp
                    ),
                    shape = Shapes.medium
                ) {
                    Text(
                        text = "View",
                        fontFamily = Poppins,
                        color = SecondaryColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.ic_profile_card_image),
                contentDescription = "",
                modifier = Modifier.height(120.dp)
            )
        }
    }
}

@Composable
fun GeneralOptionsUI() {
    val navigator = LocalNavigator.currentOrThrow
    Column(
        modifier = Modifier
            .padding(horizontal = 14.dp)
            .padding(top = 10.dp)
    ) {
        Text(
            text = "General",
            fontFamily = Poppins,
            color = SecondaryColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(vertical = 8.dp)
        )
        GeneralSettingItem(
            icon = R.drawable.ic_more,
            mainText = "More customization",
            subText = "Customize it more to fit your usage",
            onClick = {
                navigator.push(PreviewCustomizationScreen())
            }
        )
        GeneralSettingItem(
            icon = R.drawable.ic_more,
            mainText = "Address",
            subText = "Address to your account",
            onClick = {
                navigator.push(GetAddressesScreen())
            }
        )
    }
}
@Composable
fun GeneralSettingItem(icon: Int, mainText: String, subText: String, onClick: () -> Unit) {
    Card(
        onClick = { onClick() },
        colors = CardDefaults.cardColors(Color.White),
        modifier = Modifier
            .padding(bottom = 8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Row(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(shape = Shapes.medium)
                        .background(LightPrimaryColor)
                ) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = "",
                        tint = Color.Unspecified,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))
                Column(
                    modifier = Modifier.offset(y = (2).dp)
                ) {
                    Text(
                        text = mainText,
                        fontFamily = Poppins,
                        color = SecondaryColor,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                    )

                    Text(
                        text = subText,
                        fontFamily = Poppins,
                        color = Color.Gray,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.offset(y = (-4).dp)
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))

            Icon(
                painter = painterResource(id = R.drawable.ic_right_arrow),
                contentDescription = "",
                modifier = Modifier.size(16.dp)
            )
        }
    }
}


@Composable
fun SupportOptionsUI() {
    Column(
        modifier = Modifier
            .padding(horizontal = 14.dp)
            .padding(top = 10.dp)
    ) {
        Text(
            text = "Support",
            fontFamily = Poppins,
            color = SecondaryColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(vertical = 8.dp)
        )
        SupportItem(
            icon = R.drawable.ic_whatsapp,
            mainText = "Contact",
            onClick = {}
        )
        SupportItem(
            icon = R.drawable.ic_feedback,
            mainText = "Feedback",
            onClick = {}
        )
        SupportItem(
            icon = R.drawable.ic_privacy_policy,
            mainText = "Privacy Policy",
            onClick = {}
        )
        SupportItem(
            icon = R.drawable.ic_about,
            mainText = "About",
            onClick = {}
        )
        LogoutButton()
    }
}
@Composable
fun SupportItem(icon: Int, mainText: String, onClick: () -> Unit) {
    Card(
        onClick = { onClick() },
        colors = CardDefaults.cardColors(Color.White),
        modifier = Modifier
            .padding(bottom = 8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Row(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(shape = Shapes.medium)
                        .background(LightPrimaryColor)
                ) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = "",
                        tint = Color.Unspecified,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Text(
                    text = mainText,
                    fontFamily = Poppins,
                    color = SecondaryColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                painter = painterResource(id = R.drawable.ic_right_arrow),
                contentDescription = "",
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
fun LogoutButton() {
    val navigator = LocalNavigator.currentOrThrow
    val viewModel: ProfileViewModel = hiltViewModel()
    val LOGOUTViewModel: LogoutViewModel = hiltViewModel()
    val logoutResponse = LOGOUTViewModel.logoutResponse.collectAsState()
    val context = LocalContext.current


    Button(
        onClick = {
            LOGOUTViewModel.logout(
                onSuccess = {
                    navigator.push(LoginScreen())
                    Toast.makeText(context, "Logout successful", Toast.LENGTH_SHORT).show()
                },
                onError = { errorMessage ->
                    Toast.makeText(context,errorMessage, Toast.LENGTH_SHORT).show()
                }
            )
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Red
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Logout",
                fontFamily = Poppins,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Default.ExitToApp,
                contentDescription = "Logout",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

