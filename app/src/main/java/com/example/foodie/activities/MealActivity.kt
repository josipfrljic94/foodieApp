package com.example.foodie.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.foodie.R
import com.example.foodie.databinding.ActivityMainBinding
import com.example.foodie.databinding.ActivityMealBinding
import com.example.foodie.fragments.HomeFragment
import com.example.foodie.viewModel.HomeViewModel

class MealActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMealBinding
    private lateinit var mealId:String
    private  lateinit var mealName:String
    private lateinit var mealThumb:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getRandomMealInfo()
        setInfoInView()
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
}