package com.example.mvvm_coroutines_retrofit.ui.meals

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.model.response.MealsResponse
import com.example.mvvm_coroutines_retrofit.ui.theme.MVVM_Coroutines_RetrofitTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MVVM_Coroutines_RetrofitTheme {
                MealsCategoriesScreen()
            }
        }
    }
}

/**
 * View Layer
 * ViewModel에 대한 참조 보유
 * 인스턴스화를 위해 lifecycle-viewmodel-compose 라이브러리의 viewModel() 함수 사용
 */
@Composable
fun MealsCategoriesScreen() {
    val viewModel: MealsCategoriesViewModel = viewModel()
    val rememberedMeals: MutableState<List<MealsResponse>> = remember { mutableStateOf(emptyList<MealsResponse>()) }

    // LaunchedEffect: composable이 처음으로 그려질 때 한 번 실행 -> 코루틴이 한번만 실행됨
    LaunchedEffect(key1 = "GET_MEALS"){
        rememberCoroutineScope().launch(Dispatchers.IO) { // IO 스레드에서 비동기적으로 실행
            val meals = viewModel.getMeals()
            rememberedMeals.value = meals
        }
    }

    LazyColumn() {
        items(rememberedMeals.value.size) { index ->
            Text(
                text = rememberedMeals.value[index].name
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MealsCategoriesScreenPreview() {
    MVVM_Coroutines_RetrofitTheme {
        MealsCategoriesScreen()
    }
}