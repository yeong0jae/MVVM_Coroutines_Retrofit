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
    fun getMeals(successCallback: (response: MealsCategoriesResponse?) -> Unit) {
        return webService.getMeals().enqueue(object : Callback<MealsCategoriesResponse>{
            // 성공했을 때, 실패했을 때 처리
            override fun onResponse(
                call: Call<MealsCategoriesResponse>,
                response: Response<MealsCategoriesResponse>
            ) {
                if (response.isSuccessful)
                    successCallback(response.body())
            }

            override fun onFailure(call: Call<MealsCategoriesResponse>, t: Throwable) {
                // TODO: 에러 처리
            }
        })



//        return webService.getMeals().execute().body()
//        execute()는 동기적으로 실행됨 block the Thread -> main thread에서 실행됨. 응답을 받을 때까지 앱을 block.
//        => Bad Practice
//        http request, response는 비동기적으로 처리해야 함. => enqueue() 와 콜백함수 사용
    }
}