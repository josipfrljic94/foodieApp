package com.example.foodie.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.foodie.R
import com.example.foodie.databinding.ActivityMainBinding
import com.example.foodie.databinding.ActivityMealBinding
import com.example.foodie.fragments.HomeFragment
import com.example.foodie.pojo.Meal
import com.example.foodie.viewModel.HomeViewModel
import com.example.foodie.viewModel.MealViewModel
import java.net.URI

class MealActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMealBinding
    private lateinit var mealId:String
    private  lateinit var mealName:String
    private lateinit var mealThumb:String
    private lateinit var mvvm:MealViewModel
    private lateinit var ytLink:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMealBinding.inflate(layoutInflater)

        mvvm=ViewModelProviders.of(this).get(MealViewModel::class.java)
        setContentView(binding.root)
        getRandomMealInfo()
        setInfoInView()
        mvvm.getMealDetail(mealId)
        observerRandomMeal()
        onYTHandler()
    }

    private fun setInfoInView() {
        if(mealName== null){
            binding.mealDetailProgress.visibility= View.VISIBLE
           return

        }
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        binding.collapsingToolbar.title=mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
        binding.mealDetailProgress.visibility= View.INVISIBLE

    }

    private fun getRandomMealInfo() {
     val intent=intent
        mealId=intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName=intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb=intent.getStringExtra(HomeFragment.MEAL_THUMB)!!

    }

    private fun observerRandomMeal() {
        mvvm.observeMealDetailsLiveData().observe(this,object : Observer<Meal> {
            override fun onChanged(t: Meal?) {
                if(t?.strArea !==null){
                    setMealDetail(t)
                    ytLink=t.strYoutube

                }

            }


        })
    }

    private fun onYTHandler(){
        binding.imageYoutube.setOnClickListener {
            val intent=Intent(Intent.ACTION_VIEW,Uri.parse(ytLink))
            startActivity(intent)
        }
    }

    private fun setMealDetail(meal:Meal){
        binding.mealLocation.text="Location: ${meal.strArea}"
        binding.mealCategory.text="Category: ~${meal.strCategory}"
        binding.mealInstruction.text=meal.strInstructions
    }
}