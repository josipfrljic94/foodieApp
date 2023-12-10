package com.example.foodie.viewModel

import android.app.Application
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodie.R
import com.example.foodie.activities.SignInActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import java.time.Duration


class UserViewModel(private val application: Application) : AndroidViewModel(application){

    private val authInstance = FirebaseAuth.getInstance()
    private lateinit var mGoogleSignInClient:GoogleSignInClient
    private val context=application.applicationContext
    private var  gso:GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
       .requestIdToken(context.getString(R.string.web_client))
       .requestEmail()
       .build()


    fun signOut() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                GoogleSignIn.getClient(context,gso).signOut().addOnCompleteListener{
                    val intent = Intent(context, SignInActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                }
            }catch (e:Exception){
                Toast.makeText(context,"Something went wrong",Toast.LENGTH_SHORT)
            }
        }
    }
}