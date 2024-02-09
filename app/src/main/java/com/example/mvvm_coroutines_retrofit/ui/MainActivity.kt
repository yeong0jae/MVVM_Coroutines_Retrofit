package com.example.mvvm_coroutines_retrofit.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mvvm_coroutines_retrofit.ui.meals.MealsCategoriesScreen
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
