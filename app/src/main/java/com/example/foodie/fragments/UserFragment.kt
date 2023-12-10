package com.example.foodie.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import com.bumptech.glide.Glide
import com.example.foodie.R
import com.example.foodie.activities.SignInActivity
import com.example.foodie.adapters.MostPopularAdapter
import com.google.firebase.auth.FirebaseAuth
import com.example.foodie.databinding.ActivityMealBinding
import com.example.foodie.databinding.FragmentUserBinding
import com.example.foodie.viewModel.HomeViewModel
import com.example.foodie.viewModel.UserViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


class UserFragment : Fragment() {

    private lateinit var authInstance:FirebaseAuth
    private lateinit var binding:FragmentUserBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var gso:GoogleSignInOptions

    private val TAG="UserFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userViewModel= ViewModelProviders.of(this)[UserViewModel::class.java]

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client))
            .requestEmail()
            .build()
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentUserBinding.inflate(layoutInflater,container,false)
        authInstance= FirebaseAuth.getInstance()
        val currentUser=authInstance.currentUser
        if(currentUser?.displayName != null){
            binding.userName.text=currentUser.displayName
            Log.d(TAG,currentUser.photoUrl.toString())
            val photoUrl=currentUser.photoUrl?.toString()
            if(photoUrl !=null){
                loadImg(binding.userImg,photoUrl)
            }
        }
        binding.logOutBtn.setOnClickListener{
            userViewModel.signOut()
            GoogleSignIn.getClient(requireContext(),gso).signOut().addOnCompleteListener{
                val intent = Intent(context, SignInActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                this?.startActivity(intent)
            }

        }
        return binding.root
    }


    private fun loadImg(view:ImageView, imageUrl: String){
        if(!imageUrl.isNullOrEmpty()){
            Glide.with(view.context)
                .load(imageUrl)
                .into(view)
        }
    }



}