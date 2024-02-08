package com.example.mvvm_coroutines_retrofit.ui.meals

import androidx.lifecycle.ViewModel
import com.example.model.MealsRepository

/** ViewModel Layer
 * Andriodx.lifecycle.ViewModel을 상속받음
 * Model에 대한 참조 보유
 */
class MealsCategoriesViewModel(private val repository: MealsRepository = MealsRepository()) : ViewModel() {


}