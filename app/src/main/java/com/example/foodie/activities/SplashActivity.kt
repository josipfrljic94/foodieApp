package com.example.foodie.activities
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.foodie.R
import com.google.firebase.auth.FirebaseAuth



class SplashActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT: Long = 2000 // 2 seconds
    private lateinit var authInstance:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        authInstance= FirebaseAuth.getInstance()
        val user=authInstance.currentUser

        Handler().postDelayed({
            if(user !=null){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }else{
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
            }

            finish()
        }, SPLASH_TIME_OUT)
    }
}
