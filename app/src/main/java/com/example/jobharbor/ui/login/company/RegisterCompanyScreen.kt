package com.example.jobharbor.ui.login.company

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.jobharbor.R
import com.example.jobharbor.ui.login.components.WarningCircleIcon

@Composable
fun RegisterCompanyScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
        ) {
            // Gambar
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "Large Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(shape = RoundedCornerShape(16.dp))
                    .padding(vertical = 16.dp)
            )

            // Baris teks
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text("Welcome", fontSize = 56.sp, fontWeight = FontWeight.Bold)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Please",
                        color = Color.Gray,
                        fontSize = 52.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(end = 6.dp)
                    )
                    Text(
                        "Register",
                        color = Color(0xFFFCA34D),
                        fontSize = 44.sp,
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline,
                        textAlign = TextAlign.Center
                    )
                }
                Text("Here!",
                    fontSize = 56.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
            // Input Section
            val passwordState = remember { mutableStateOf(TextFieldValue()) }

            // Email Input Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .background(Color.White)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Username")
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
                        value = "",
                        onValueChange = { /* Handle email input */ },
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
                        value = "",
                        onValueChange = { },
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
                Button(
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(48.dp)
                ) {
                    Text(
                        "Register",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

        }
    }
}


@Preview
@Composable
fun RegisterCompanyScreenPreview() {
    RegisterCompanyScreen()
}
