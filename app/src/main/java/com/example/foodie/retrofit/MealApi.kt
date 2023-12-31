package com.example.foodie.retrofit

import com.example.foodie.pojo.CategoryList
import com.example.foodie.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {
    @GET("random.php")
    fun getRandomMeal():Call<MealList>

    @GET("lookup.php?")
    fun getMealDetails(@Query("i")id:String):Call<MealList>

    @GET("filter.php?")
    fun getCategoryMeal(@Query("c")categoryName:String):Call<CategoryList>
}