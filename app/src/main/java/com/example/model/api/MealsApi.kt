package com.example.model.api

import com.example.model.response.MealsCategoriesResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class MealsWebService {
    private lateinit var api: MealsApi // lateinit: 나중에 초기화
    init { // 객체 생성 시 호출
        val retrofit = Retrofit.Builder() // Retrofit 객체 생성
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(MealsApi::class.java)
    }

    fun getMeals(): Call<MealsCategoriesResponse> {
        return api.getMeals()
    }

    interface MealsApi {
        @GET("categories.php") // API endpoint
        fun getMeals(): Call<MealsCategoriesResponse>
    }
}