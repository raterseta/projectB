package com.example.myapplication

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
//                LazyColumnAbs()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LazyColumnAbs()
                }
            }
        }
    }
}

@Composable
fun LazyColumnAbs(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel(factory = ViewModelFactory(ProfileLibrary()))
) {
    val sortedProfile by viewModel.sortedProfile.collectAsState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().background(Color(0xFFD8D2C2))
    ) {
        LazyColumn(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(sortedProfile) { _, item ->
                ProfileItem(
                    name = item.name,
                    photoUrl = item.photoUrl,
                    deskripsi = item.deskripsi
                )
            }
        }
    }
}
@SuppressLint("RememberReturnType")
@Composable
fun ProfileItem(
    name: String,
    photoUrl: Int,
    deskripsi: String,
    modifier: Modifier = Modifier
) {
    val (showDialog, setShowDialog) = remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = photoUrl),
            contentDescription = name,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .clickable { setShowDialog(true) }
        )
        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = name,
                style = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
            Text(
                text = "Dosen FILKOM UB",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    }

    if (showDialog) {
        ProfileDialog(
            name = name,
            photoUrl = photoUrl,
            deskripsi = deskripsi,
            onDismiss = { setShowDialog(false) }
        )
    }
}

@Composable
fun ProfileDialog(
    name: String,
    photoUrl: Int,
    deskripsi: String,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = name) },
        text = {
            Column {
                Image(
                    painter = painterResource(id = photoUrl),
                    contentDescription = name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = deskripsi)
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        LazyColumnAbs()
    }
}
