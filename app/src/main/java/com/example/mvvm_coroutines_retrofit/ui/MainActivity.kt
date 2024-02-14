package com.example.mvvm_coroutines_retrofit.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.codingtroops.mealzapp.ui.details.MealDetailsScreen
import com.codingtroops.mealzapp.ui.details.MealDetailsViewModel
import com.example.mvvm_coroutines_retrofit.ui.meals.MealsCategoriesScreen
import com.example.mvvm_coroutines_retrofit.ui.theme.MVVM_Coroutines_RetrofitTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MVVM_Coroutines_RetrofitTheme {
                FoodiezApp()
            }
        }
    }
}

@Composable
private fun FoodiezApp() {
    /**
     * navHostController는 앱 내에서 화면 간의 전환을 관리하는 데 사용되는 컨트롤러(navigate 보유)이고,
     * navHost는 이러한 화면 전환을 표시하는 공간을 제공
     */
    val navController = rememberNavController()

    NavHost(navController, startDestination = "destination_meals_list") {
        // 시작 화면 (list page)
        composable(route = "destination_meals_list") {
            MealsCategoriesScreen {navigationMealId ->
                navController.navigate("destination_meal_details/$navigationMealId")
                // navController를 전달하지 않고 navigate를 콜백함수로서 전달함
                // mealCategory에서 콜백함수를 실행하면서 mealId를 전달 -> destination_meal_details로 이동
            }
        }
        // 상세 화면 (detail page)
        composable(
            route = "destination_meal_details/{meal_category_id}",
            arguments = listOf(navArgument("meal_category_id") {
                type = NavType.StringType
            })
        ) {
            var viewModel : MealDetailsViewModel = viewModel()
            // 여기서 meal_category_id를 MealDetailsViewModel에 전달하는데
            // savedStateHandle을 통해 meal_category_id를 가져옴. 이는 viewModel() 함수도 알 수 있음
            // -> 바로 state화 하면서 viewModel에서 값 관리 가능
            MealDetailsScreen(viewModel.mealState.value)
        }
    }
}