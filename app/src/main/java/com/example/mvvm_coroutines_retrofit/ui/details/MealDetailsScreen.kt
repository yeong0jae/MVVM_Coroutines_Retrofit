package com.codingtroops.mealzapp.ui.details

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.min
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.model.response.MealResponse
import kotlin.math.max
import kotlin.math.min

@Composable
fun MealDetailsScreen(meal: MealResponse?) {
    val scrollState = rememberLazyListState() // lazyColumn의 스크롤 상태를 저장
    val offset = min(1f, 1 -
            (scrollState.firstVisibleItemScrollOffset / 600f
                    + scrollState.firstVisibleItemIndex))
    // firstVisibleItemScrollOffset : 첫번째 아이템의 스크롤 오프셋
    // 매번 재설정되기 때문에 firstVisibleItemIndex를 더해줌
    val size by animateDpAsState(targetValue = max(100.dp, 140.dp * offset), label = "")

    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        Column {
             Surface(
                shadowElevation = 4.dp,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()

                ) {
                    Card(
                        modifier = Modifier.padding(16.dp),
                        shape = CardDefaults.shape,
                        border = BorderStroke(
                            width = 2.dp,
                            color = Color.Green
                        )
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(meal?.imageUrl)
                                    .transformations(CircleCropTransformation())
                                    .build()
                            ),
                            contentDescription = null,
                            modifier = Modifier
                                .size(size) // 100 ~ 200dp
                                .padding(8.dp)
                        )
                    }
                    Text(meal?.name?: "default name",
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.CenterVertically)
                    )
                }
            }

            val dummyContentList = (0..100).map { it.toString() }
            LazyColumn (
                state = scrollState,
            ) {
                items(dummyContentList.size) { index ->
                    Text(text = dummyContentList[index], modifier = Modifier.padding(24.dp))
                }
            }
        }
    }
}











