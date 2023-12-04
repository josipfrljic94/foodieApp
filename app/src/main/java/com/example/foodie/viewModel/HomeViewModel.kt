package com.example.foodie.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodie.pojo.CategoryList
import com.example.foodie.pojo.CategoryMeals
import com.example.foodie.pojo.Meal
import com.example.foodie.pojo.MealList
import com.example.foodie.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel():ViewModel() {
    private  var randomMealLiveData = MutableLiveData<Meal>()
    private var popularMealList=MutableLiveData<List<CategoryMeals>>()
    fun getRandomMeal(){
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {

                if(!call.isCanceled || response.body() != null){
                    val randomMeal: Meal = response.body()!!.meals[0]
                    Log.d("RANDOM MEAL",randomMeal.strMeal)
                    randomMealLiveData.value=randomMeal
                }else{
                    Log.d("RANDOM MEAL","shit")
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("Home F",t.message.toString())
            }
        })
    }

    fun getCategoryMeal(){
        RetrofitInstance.api.getCategoryMeal("Seafood").enqueue(object : Callback<CategoryList> {
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {

                if(!call.isCanceled || response.body() != null){
                    popularMealList.value=response.body()?.meals
                }else{
                    Log.d("RANDOM MEAL","shit")
                    return
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d("Home F",t.message.toString())
            }
        })
    }

    fun observeCategoryMeal():LiveData<List<CategoryMeals>>{
        return popularMealList
    }

    fun observeRandomMeal():LiveData<Meal>{
        return randomMealLiveData
    }
}