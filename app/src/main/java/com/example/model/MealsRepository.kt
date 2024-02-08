package com.example.model

import com.example.model.response.MealsCategoriesResponse

/**
 * Model Layer
 * 데이터를 가져오고 저장하는 역할
 */
class MealsRepository {
    fun getMeals(): MealsCategoriesResponse = MealsCategoriesResponse(arrayListOf())
}