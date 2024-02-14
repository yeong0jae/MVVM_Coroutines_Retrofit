package com.example.model

import com.example.model.api.MealsWebService
import com.example.model.response.MealsCategoriesResponse
import com.example.model.response.MealResponse

/**
 * Model Layer
 * 데이터를 가져오고 저장하는 역할
 */
class MealsRepository(private val webService: MealsWebService = MealsWebService()) {

    private var cachedMeals = listOf<MealResponse>()
    // list를 불러올 때 저장해두고 detail을 불러올 때 다시 불러오지 않도록 하기 위함
    /**
     * Backend에서는 db와의 조회 최적화가 중요하고
     * Frontend에서는 네트워크 통신 최적화가 중요함. 불필요한 네트워크 통신을 줄이기 위해 캐싱을 사용함
     */

    suspend fun getMeals(): MealsCategoriesResponse {
        val response = webService.getMeals()
        cachedMeals = response.categories // 캐싱
        return response
    }

    /**
     *         webService.getMeals().enqueue(object : Callback<MealsCategoriesResponse>{
     *             override fun onResponse( // HTTP response 성공 시 호출
     *                 call: Call<MealsCategoriesResponse>,
     *                 response: Response<MealsCategoriesResponse>
     *             ) {
     *                 if (response.isSuccessful) // 애플리케이션 수준에서의 성공 여부 확인
     *                     successCallback(response.body()) // view로부터 전달받은 콜백함수 호출
     *             }
     *
     *             override fun onFailure(call: Call<MealsCategoriesResponse>, t: Throwable) {
     *                 // TODO: 에러 처리
     *             }
     *         })
     *         비동기적(no coroutine)
     *
     *         return webService.getMeals().execute().body()
     *         execute()는 동기적으로 실행됨 block the Thread -> main thread에서 실행됨. 응답을 받을 때까지 앱을 block.
     *         => Bad Practice
     *         http request, response는 비동기적으로 처리해야 함. => enqueue() 와 콜백함수 사용
     */


    fun getMeal(id: String): MealResponse? {
        return cachedMeals.firstOrNull { it.id == id } // 캐싱된 데이터에서 id와 일치하는 첫번째 데이터를 반환
    }

    companion object { // 싱글톤을 위한 companion object
        @Volatile
        private var instance: MealsRepository? = null

        fun getInstance() = instance?: synchronized(this) {
            instance?: MealsRepository().also { instance = it }
        }
    }
    /**
     * 싱글톤으로 하지 않으면 viewModel에서 repository를 생성할 때마다 새로운 객체가 생성되기 때문에
     * MealDetailsViewModel과 MealsCategoriesViewModel이 다른 repository를 참조하게 됨
     * cache된 데이터를 잃는 등 문제가 발생함
     */
}