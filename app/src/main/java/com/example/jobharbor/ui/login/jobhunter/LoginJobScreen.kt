package com.example.jobharbor.ui.login.jobhunter

import android.util.Log
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jobharbor.R
import com.example.jobharbor.ui.login.AuthEvent
import com.example.jobharbor.ui.login.LoginViewModel
import com.example.jobharbor.ui.login.components.WarningCircleIcon
import kotlinx.coroutines.launch

@Composable
fun LoginJobScreen(event: (AuthEvent) -> Unit) {
    val viewModel : LoginViewModel = viewModel()
    val scrollState = rememberScrollState()
    val emailState = remember { mutableStateOf(TextFieldValue()) }
    val passwordState = remember { mutableStateOf(TextFieldValue()) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState),
        ) {
            // Gambar
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "Large Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(shape = RoundedCornerShape(16.dp))
                    .padding(top = 32.dp, bottom = 16.dp)
            )
            // Baris teks
            Column(modifier = Modifier.padding(start = 16.dp, top = 8.dp)) {
                Text("Welcome", fontSize = 48.sp, fontWeight = FontWeight.Bold)
                Row(verticalAlignment = Alignment.Bottom) {
                    Text("Please",
                        color = Color.Gray,
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(end = 8.dp)
                    )
                    Text(
                        "Login",
                        color = Color(0xFFFCA34D),
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline
                    )
                }
                Text("Here!",
                    fontSize = 44.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            // Input Section
            // Email Input Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .background(Color.White)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Email")
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(4.dp)
                        .clip(shape = RoundedCornerShape(16.dp))
                        .padding(horizontal = 2.dp)
                    ,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BasicTextField(
                        value = emailState.value,
                        onValueChange = { emailState.value = it },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .clip(shape = RoundedCornerShape(16.dp))
                            .padding(vertical = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text("Password")
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(4.dp)
                        .clip(shape = RoundedCornerShape(16.dp))
                        .shadow(4.dp, shape = RoundedCornerShape(32.dp), clip = true)
                        .padding(horizontal = 2.dp)
                        .background(Color.White)
                                        ,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BasicTextField(
                        value = passwordState.value,
                        onValueChange = { passwordState.value = it },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .background(Color.White)
                            .padding(vertical = 8.dp)
                            .clip(shape = RoundedCornerShape(16.dp))
                            .fillMaxWidth()
                    )
                    // Password Warning
                    if (passwordState.value.text.length < 8) {
                        Box(
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            WarningCircleIcon()
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                // Password Length Warning Text
                if (passwordState.value.text.length < 8) {
                    Text(
                        "Password must be at least 8 characters",
                        color = Color.Red,
                        fontSize = 12.sp,
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                // Button Login
                val scope = rememberCoroutineScope()
                Button(
                    onClick = {
                        scope.launch {
                            val login = viewModel.loginVM(emailState.value.text, passwordState.value.text)
                            if(login.error != "Invalid credentials") {
                                event(AuthEvent.SaveAppEntry)
                                login.token
                            } else {
                                Log.d("Login Message", login.error)
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 12.dp)
                        .height(48.dp)
                ) {
                    Text(
                        "Login",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                    )
                }

                // Baris setelah Button Login
                Text(
                    "Do not have an account ?",
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                )

                // Teks Register Here
                Text(
                    text = "Register Here",
                    color = Color(0xFFFCA34D),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .clickable {

                        }
                )
            }

        }
    }
}




@Preview
@Composable
fun LoginScreenPreview() {
    LoginJobScreen(event = { })
}
