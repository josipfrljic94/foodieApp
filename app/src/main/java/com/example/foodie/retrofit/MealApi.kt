package com.example.foodie.retrofit

import com.example.foodie.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET

interface MealApi {
    @GET("random.php")
    fun getRandomMeal():Call<MealList>
}