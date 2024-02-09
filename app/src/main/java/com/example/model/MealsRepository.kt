package com.example.model

import com.example.model.api.MealsWebService
import com.example.model.response.MealsCategoriesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Model Layer
 * 데이터를 가져오고 저장하는 역할
 */
class MealsRepository(private val webService: MealsWebService = MealsWebService()) {
    suspend fun getMeals(): MealsCategoriesResponse {
        return webService.getMeals()
    }

//        webService.getMeals().enqueue(object : Callback<MealsCategoriesResponse>{
//            override fun onResponse( // HTTP response 성공 시 호출
//                call: Call<MealsCategoriesResponse>,
//                response: Response<MealsCategoriesResponse>
//            ) {
//                if (response.isSuccessful) // 애플리케이션 수준에서의 성공 여부 확인
//                    successCallback(response.body()) // view로부터 전달받은 콜백함수 호출
//            }
//
//            override fun onFailure(call: Call<MealsCategoriesResponse>, t: Throwable) {
//                // TODO: 에러 처리
//            }
//        })
//        비동기적(no coroutine)

//        return webService.getMeals().execute().body()
//        execute()는 동기적으로 실행됨 block the Thread -> main thread에서 실행됨. 응답을 받을 때까지 앱을 block.
//        => Bad Practice
//        http request, response는 비동기적으로 처리해야 함. => enqueue() 와 콜백함수 사용

}