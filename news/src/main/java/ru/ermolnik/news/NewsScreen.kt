package ru.ermolnik.news

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import ru.mts.data.news.repository.models.News

@Composable
fun NewsScreen(viewModel: NewsViewModel) {
    val state = viewModel.state.collectAsState()
    Box(modifier = Modifier.fillMaxSize()) {
        when (state.value) {
            is NewsState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.Center)
                )
            }
            is NewsState.Error -> {
                Text(
                    text = "Error is occurred. Please press Refresh button",
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.Center),
                    textAlign = TextAlign.Center
                )
            }
            is NewsState.Content -> {
                Button(
                    onClick = { viewModel.onRefresh() },
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                ) {
                    Text("Refresh", fontSize = 25.sp)
                }
                val contentState = state.value as NewsState.Content
                if (contentState.news.isEmpty()) {
                    Text(
                        text = "No news",
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                } else {
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                    ) {
                        contentState.news.forEach {
                            ItemRow(it)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ItemRow(news: News) {
    Image(
        painter = rememberAsyncImagePainter(news.urlToImage),
        contentDescription = null,
        modifier = Modifier.size(128.dp)
    )
    Text(
        text = news.title ?: "",
        modifier = Modifier
            .padding(4.dp)
            .wrapContentSize(),
        fontSize = 25.sp,
        fontWeight = FontWeight.W700,
        textAlign = TextAlign.Start
    )
    Text(
        text = news.content ?: "",
        modifier = Modifier
            .padding(4.dp)
            .wrapContentSize(),
        textAlign = TextAlign.Start
    )
}




