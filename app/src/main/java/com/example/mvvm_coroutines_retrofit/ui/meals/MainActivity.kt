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
    // ViewModel: recomposition 발생하더라도 동일한 인스턴스가 유지
    val viewModel: MealsCategoriesViewModel = viewModel()
    // remember: 값이 변경되면 recomposition 발생
    val rememberedMeals: MutableState<List<MealsResponse>> = remember { mutableStateOf(emptyList<MealsResponse>()) }
    // 앱 실행 직후에는 빈 값임. API 호출 후 값이 변경되면 recomposition 발생
    viewModel.getMeals() { response ->
        /**
         * 이 람다, 콜백 함수는 지금 실행되는 게 아님. getMeals의 매개변수로 전달된 후 함수 실행부에서 호출되었을 때 실행됨
         * => categories -> List<MealsResponse> -> remember 역할
         * retrofit은 개발자가 retrofit2.Callback의 onResponse()에서 이 작업을 호출하도록 디자인되어 있음
         * 결국 HTTP 응답이 성공일 때 이 작업을 수행하도록 하는 것이고, 그것을 실현시키는 것이 콜백함수.
         * 콜백함수를 쓰지 않고 동기적으로 처리하면 앱이 block되어 버림. 메인 스레드에서 네트워크 요청을 보내면 안됨.
         */
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
}

@Preview(showBackground = true)
@Composable
fun MealsCategoriesScreenPreview() {
    MVVM_Coroutines_RetrofitTheme {
        MealsCategoriesScreen()
    }
}