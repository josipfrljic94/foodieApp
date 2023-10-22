package com.example.foodie.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodie.pojo.Meal
import com.example.foodie.pojo.MealList
import com.example.foodie.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel():ViewModel() {
    private var mealDetailLiveData= MutableLiveData<Meal>()

    fun getMealDetail(id:String){
        RetrofitInstance.api.getMealDetails(id).enqueue(object : Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
               if(response.body()!=null){
                   mealDetailLiveData.value=response.body()!!.meals[0]
               }else{
                   Log.d("MEAIL DETAIL","fail")
                   return
               }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                TODO("Not yet implemented")
                Log.d("MEAL ACTIVITY",t.message.toString())
            }


        })
    }
    fun observeMealDetailsLiveData():LiveData<Meal>{
        return mealDetailLiveData
    }
}