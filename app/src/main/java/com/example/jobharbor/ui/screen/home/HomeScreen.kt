package com.example.jobharbor.ui.screen.home


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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.jobharbor.R
import com.example.jobharbor.ViewModelFactory
import com.example.jobharbor.di.Injection
import com.example.jobharbor.model.OrderJob
import com.example.jobharbor.ui.common.CompanyJobLayout
import com.example.jobharbor.ui.common.UiState
import com.example.jobharbor.ui.navgraph.Route

@Composable
fun HomeScreen(
    navController: NavController,
    navigateToDetail: (Long) -> Unit
) {
    val viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    )
    val scrollState = rememberScrollState()

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllJobs()
            }

            is UiState.Success -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 16.dp, end = 16.dp, top = 28.dp)
                        .verticalScroll(scrollState)
                ) {
                    TopBar()
                    LargeImageWithSearchBar(navController)
                    JobListLayout(navController = navController, uiState.data, navigateToDetail)
                    PostSection(
                        profilePic = painterResource(id = R.drawable.profile_icon),
                        userName = "William Rosewood",
                        postTime = "08:39 pm",
                        description =
                        "Seeking the perfect work fit? Discover diverse opportunities that align with your skills and passions. Let's connect and explore together! #CareerExploration #JobOpportunities",
                        postImage = painterResource(id = R.drawable.dummy_post)
                    )
                }

            }

            is UiState.Error -> {
                "Error"
            }

            else -> ""
        }
    }


}

@Composable
fun TopBar() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        ProfilePicture(image = R.drawable.profile_icon, modifier = Modifier.padding(end = 8.dp))
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        ) {
            Column {
                Text(
                    text = "Hello, Welcome",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "William Rosewood",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        NotificationIcon(
            icon = Icons.Default.Notifications,
            tint = Color(0xFF292B5C),
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun ProfilePicture(image: Int, modifier: Modifier = Modifier) {
    val image: Painter = painterResource(id = image)
    Image(
        painter = image,
        contentDescription = "Profile Picture",
        modifier = modifier
            .size(40.dp)
            .padding(4.dp),
        alpha = 1f
    )
}

@Composable
fun NotificationIcon(
    icon: ImageVector,
    tint: Color,
    modifier: Modifier = Modifier
) {
    Icon(
        imageVector = icon,
        contentDescription = "Notification",
        tint = tint,
        modifier = modifier
    )
}


// Image Banner

@Composable
fun LargeImageWithSearchBar(navController: NavController) {
    val context = LocalContext.current

    // Image yang besar
    val image: Painter = painterResource(id = R.drawable.hero2)
    Box(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Image(
            painter = image,
            contentDescription = "Hero",
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .clickable {
                    navController.navigate(Route.SearchScreen.route)
                },
            contentScale = ContentScale.Crop
        )
    }

    // SearchBar
    Spacer(modifier = Modifier.height(50.dp))
}




@Composable
fun JobListLayout(
    navController: NavController,
    jobs: List<OrderJob>,
    navigateToDetail: (Long) -> Unit,
) {
    val filteredList = remember {
        mutableStateOf(listOf<OrderJob>())
    }

    LaunchedEffect(jobs) {
        filteredList.value = jobs
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .offset(y = -50.dp)
    ) {
        // Judul Job List
        Text(
            text = "Job List",
            style = TextStyle(fontWeight = FontWeight.Bold),
            modifier = Modifier
                .offset(y = 10.dp),
            fontSize = 20.sp
        )

        // Teks "See more"
        Text(
            text = "See All",
            color = Color.Blue,
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 8.dp, top = 8.dp)
                .offset(y = (-20).dp)
                .clickable {
                    navController.navigate(Route.SearchScreen.route)
                }
        )

        // Looping box-box horizontal
        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            items(filteredList.value.size) {
                val data = filteredList.value[it]
                Box(
                    modifier = Modifier
                        .size(width = 300.dp, height = 220.dp)
                        .background(Color.LightGray.copy(alpha = 0.55f))
                        .clip(RoundedCornerShape(16.dp))
                        .clickable {
                            navigateToDetail(data.job.id)
                        }

                ) {
                    // Menggunakan CompanyJobLayout di dalam Box
                    CompanyJobLayout(
                        companyLogo = data.job.logo,
                        companyName = data.job.companyName,
                        jobTitle = data.job.jobTitle,
                        jobLocation = data.job.location,
                        jobCategory = data.job.category,
                        jobSalary = data.job.salary,
                    )
                }
                Spacer(modifier = Modifier.padding(horizontal = 8.dp))
            }
        }

    }
}


@Composable
fun PostSection(
    profilePic: Painter,
    userName: String,
    postTime: String,
    description: String,
    postImage: Painter
) {
    Column(
        modifier = Modifier
            .offset(y = -30.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            // Judul Job List
            Text(
                text = "People",
                style = TextStyle(fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .padding(start = 8.dp)
                    .offset(y = 10.dp),
                fontSize = 20.sp
            )

            // Teks "See more"
            Text(
                text = "See All",
                color = Color.Blue,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 8.dp, top = 8.dp)
                    .offset(y = (-20).dp)
                    .clickable {

                    }
            )
        }
        Surface(
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
            ) {
                // Profile Section
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Image(
                        painter = profilePic,
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = userName,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        Text(
                            text = postTime,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                ) {
                    // Description
                    Text(
                        text = getFormattedDescription(description),
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }


                Box(
                    modifier = Modifier
                        .padding(16.dp)
                ) {

                    // Post Image
                    Image(
                        painter = postImage,
                        contentDescription = "Post Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

            }
        }
    }
}


@Composable
fun getFormattedDescription(description: String): String {
    val words = description.split(" ")
    val shortened = words.take(20).joinToString(" ")
    return if (words.size > 20) "$shortened..." else shortened
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    val navController = rememberNavController()
    HomeScreen(navController = navController, {})
}



