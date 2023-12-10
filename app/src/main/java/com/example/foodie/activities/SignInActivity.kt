package com.example.foodie.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.foodie.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth

class SignInActivity : AppCompatActivity() {

    companion object{
        private const val RC_SIGN_IN=120
        private val REQ_ONE_TAP = 2  // Can be any integer unique to the Activity
        private var showOneTapUI = true
    }

    private val TAG="SignInActivity"
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient:GoogleSignInClient
    private lateinit var googleBtn:View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        auth = Firebase.auth

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client))
            .requestEmail()
            .build()
        googleSignInClient=GoogleSignIn.getClient(this,gso)

        googleBtn=findViewById<View>(R.id.google_sign_in_button)
        googleBtn.setOnClickListener{
            signIn()
        }

    }



    // [START onactivityresult]
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception=task.exception
//            val idToken=task.result.idToken
            if(task.isSuccessful){
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.result
                    Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.w(TAG, "Google sign in failed", e)
                }
            }else{
                Log.w(TAG,exception.toString())
            }
        }
    }
    // [END onactivityresult]


    // [START signin]
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    // [END signin]

    // [START auth_with_google]
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val intent=Intent(this,MainActivity::class.java)
                    startActivity(intent)

//                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
//                    updateUI(null)
                }
            }
    }
    // [END auth_with_google]

}