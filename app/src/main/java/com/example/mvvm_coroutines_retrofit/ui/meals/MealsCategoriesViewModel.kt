package com.example.mvvm_coroutines_retrofit.ui.meals

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.MealsRepository
import com.example.model.response.MealsCategoriesResponse
import com.example.model.response.MealsResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/** ViewModel Layer
 * Andriodx.lifecycle.ViewModel을 상속받음
 * Model에 대한 참조 보유
 */
class MealsCategoriesViewModel(private val repository: MealsRepository = MealsRepository()) : ViewModel() {

    // State Hoisting with Coroutine
    val mealsState: MutableState<List<MealsResponse>> = mutableStateOf(emptyList<MealsResponse>())

    private val mealsJob = Job() // custom coroutine
    init {
        val scope = CoroutineScope(mealsJob + Dispatchers.IO)
        scope.launch() { // IO 스레드에서 비동기적으로 실행
            val meals = getMeals() // suspend 함수 호출
            mealsState.value = meals // state 업데이트
        }
    }
    override fun onCleared() { // ViewModel이 소멸될 때 호출
        super.onCleared()
        mealsJob.cancel()
    }

    private suspend fun getMeals(): List<MealsResponse> {
        return repository.getMeals().categories
    }
}