package com.example.foodie.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.get
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.foodie.R
import com.example.foodie.activities.MealActivity
import com.example.foodie.databinding.FragmentHomeBinding
import com.example.foodie.pojo.Meal
import com.example.foodie.pojo.MealList
import com.example.foodie.retrofit.RetrofitInstance
import com.example.foodie.viewModel.HomeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding:FragmentHomeBinding
    private lateinit var  homeMvvm:HomeViewModel
    private lateinit var randomMeal:Meal

    companion object{
        const val MEAL_ID="com.example.easyfood.fragments.";
        const val MEAL_NAME="com.example.easyfood.fragments.nameMeal"
        const val MEAL_THUMB="com.example.easyfood.fragments.thumbMeal"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm= ViewModelProviders.of(this)[HomeViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentHomeBinding.inflate(inflater,container,false)
//        return inflater.inflate(R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeMvvm.getRandomMeal()
        observerRandomMeal()
        onRandomMealClick()
    }




    private fun observerRandomMeal() {
        homeMvvm.observeRandomMeal().observe(viewLifecycleOwner,{t->
                Glide.with(this@HomeFragment)
                    .load(t!!.strMealThumb)
                    .into(binding.imgMainMeal)
                this.randomMeal=t

        })
    }

    private fun onRandomMealClick(){
        binding.mainMealCard.setOnClickListener {
            val intent= Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
            startActivity(intent)
        }
    }



}