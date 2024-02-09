package com.example.mvvm_coroutines_retrofit.ui.meals

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.model.response.MealsResponse
import com.example.mvvm_coroutines_retrofit.ui.theme.MVVM_Coroutines_RetrofitTheme

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
    viewModel.getMeals() {response ->
        val mealsFromTheApi = response?.categories
        rememberedMeals.value = mealsFromTheApi.orEmpty()
    }
    LazyColumn() {
        items(rememberedMeals.value.size) { index ->
            Text(
                text = rememberedMeals.value[index].name
            )
        }
    }

    // composable이 살아있는 동안에는 다시 인스턴스화 되지 않음. 구성 변경(recomposition)이 발생하더라도 동일한 인스턴스가 유지

}

@Preview(showBackground = true)
@Composable
fun MealsCategoriesScreenPreview() {
    MVVM_Coroutines_RetrofitTheme {
        MealsCategoriesScreen()
    }
}