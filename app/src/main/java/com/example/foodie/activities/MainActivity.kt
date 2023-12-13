 package com.example.foodie.activities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.foodie.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database


 class MainActivity : AppCompatActivity() {
     private val TAG = "MainActivity"
     private lateinit var database: DatabaseReference
// ...

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation= findViewById<BottomNavigationView>(R.id.btn_nav)
        val navController= Navigation.findNavController(this, R.id.host_fragment)

        NavigationUI.setupWithNavController(bottomNavigation,navController)

        val database = FirebaseDatabase.getInstance().reference
        val message = Message("John", "Hello, world!")
        database.child("messages").push().setValue(message)
    }

}

 data class Message(val author: String, val content: String)