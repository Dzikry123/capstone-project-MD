package com.example.jobharbor.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun CompanyJobLayout(
    companyLogo: Int,
    companyName: String,
    jobTitle: String,
    jobLocation: String,
    jobCategory: List<String>,
    jobSalary : String,
) {

    // Layout
    Column(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Company Logo
            Image(
                painter = painterResource(companyLogo),
                contentDescription = "Company Logo",
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(48.dp)
                    .background(Color.White)
            )

            // Company Name
            Text(
                text = companyName,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )

            // Bookmark Icon
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Bookmark Icon",
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Job Title
        Text(
            text = jobTitle,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp, start = 4.dp)
        )

        // Job Location
        Text(
            text = jobLocation,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 4.dp, start = 4.dp)
        )

        // Job Category
        Box(
            modifier = Modifier
                .padding(top = 12.dp, bottom = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState()),
            ) {
                jobCategory.forEach {
                    Box(
                        modifier = Modifier
                            .width(100.dp)
                            .height(25.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .shadow(2.dp, shape = RoundedCornerShape(8.dp))
                            .background(Color.White)
                    ) {
                        Text(
                            text = it,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(bottom = 3.dp),
                            fontSize = 12.sp
                        )
                    }
                    Spacer(modifier = Modifier.padding(start = 16.dp))
                }
            }
        }

        // Job Salary
        Text(
            text = jobSalary,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
    }
}

@Preview
@Composable
fun CompanyJobLayoutPrev() {
    CompanyJobLayout(
        companyLogo = 1,
        companyName = "as",
        jobTitle = "asd",
        jobLocation = "asdas",
        jobCategory = listOf("asda","asda"),
        jobSalary = "12"
    )
}