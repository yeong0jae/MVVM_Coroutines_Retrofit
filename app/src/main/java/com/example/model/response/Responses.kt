package com.example.model.response

import com.google.gson.annotations.SerializedName

/**
 * deserialization
 * Json to data Class
 */
data class MealsCategoriesResponse(
    val categories: List<MealsResponse> // key와 변수명이 같으면 매핑 생략 가능
)

data class MealsResponse(
    @SerializedName("idCategory") val id: String,
    @SerializedName("strCategory") val name: String,
    @SerializedName("strCategoryThumb") val description: String,
    @SerializedName("strCategoryDescription") val imageUrl: String
)