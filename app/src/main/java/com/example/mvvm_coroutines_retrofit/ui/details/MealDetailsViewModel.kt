package com.codingtroops.mealzapp.ui.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.model.MealsRepository
import com.example.model.response.MealResponse

class MealDetailsViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    // savedStateHandle : navigation에서 전달하는 인자가 저장돼있음

    // private val repository: MealsRepository = MealsRepository()
    // 위처럼 쓰면 안되는 이유가 repository를 생성할 때마다 새로운 객체가 생성되기 때문에. repo는 싱글톤으로 관리해야함
    private val repository: MealsRepository = MealsRepository.getInstance()

    var mealState = mutableStateOf<MealResponse?>(null)
    // meal detail 이 state로서 관리됨

    init { // navigate가 실행되면 viewModel이 생성되고 init 블록이 실행됨 -> mealState(mealsResponse)를 가져와야함
        val mealId = savedStateHandle.get<String>("meal_category_id") ?: ""
        // savedStateHandle을 통해 meal_category_id값을 가져옴
        mealState.value = repository.getMeal(mealId)
    }
}