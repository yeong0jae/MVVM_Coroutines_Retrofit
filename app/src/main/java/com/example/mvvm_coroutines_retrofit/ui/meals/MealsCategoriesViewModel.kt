package com.example.mvvm_coroutines_retrofit.ui.meals

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.model.MealsRepository
import com.example.model.response.MealResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/** ViewModel Layer
 * Andriodx.lifecycle.ViewModel을 상속받음
 * Model에 대한 참조 보유
 */
class MealsCategoriesViewModel(private val repository: MealsRepository = MealsRepository.getInstance()) : ViewModel() {

    // State Hoisting with Coroutine
    val mealsState: MutableState<List<MealResponse>> = mutableStateOf(emptyList<MealResponse>())

    private val mealsJob = Job() // custom coroutine
    init {
        val scope = CoroutineScope(mealsJob + Dispatchers.IO)
        scope.launch() { // IO 스레드에서 비동기적으로 실행
            val meals = getMeals() // suspend 함수 호출 -> api request -> response -> return (비동기 작업)
            mealsState.value = meals // state 업데이트
        }
    }
    override fun onCleared() { // ViewModel이 소멸될 때 호출
        super.onCleared()
        mealsJob.cancel()
    }

    private suspend fun getMeals(): List<MealResponse> {
        return repository.getMeals().categories
    }
}