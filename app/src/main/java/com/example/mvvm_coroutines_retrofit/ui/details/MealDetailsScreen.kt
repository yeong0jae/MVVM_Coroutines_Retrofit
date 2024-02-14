package com.codingtroops.mealzapp.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.model.response.MealResponse

@Composable
fun MealDetailsScreen(meal: MealResponse?) {
    Column {
        Row {
            Card {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(meal?.imageUrl)
                            .transformations(CircleCropTransformation())
                            .build()
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(200.dp)
                )
            }
            Text(meal?.name?: "default name")
        }
        Button(onClick = { /*TODO*/ }) {
            Text("Change state of meal profile picture")
        }
    }
}