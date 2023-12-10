package com.example.foodie.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodie.databinding.PopularItemsBinding
import com.example.foodie.pojo.CategoryMeals
import com.example.foodie.pojo.Meal
import com.example.foodie.pojo.MealList
import com.example.foodie.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MostPopularAdapter(): RecyclerView.Adapter<MostPopularAdapter.PopularMealViewHolder>(){
    private var mealList= ArrayList<CategoryMeals>()
    lateinit var onItemClick:((CategoryMeals)->Unit)

    fun setMeals(mealsList: ArrayList<CategoryMeals>){
        mealList=mealsList

        notifyDataSetChanged()
    }


    class PopularMealViewHolder(val binding:PopularItemsBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
        return PopularMealViewHolder(PopularItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
        holder.binding.apply {
            Glide.with(holder.itemView)
                .load(mealList[position].strMealThumb)
                .into(imgPopularMealItem)
        }
        holder.itemView.setOnClickListener{

            onItemClick.invoke(mealList[position])
        }

    }
}