package com.example.word

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI


class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WordDatabase.initInstance(this)
        setContentView(R.layout.activity_main)
        navController = Navigation.findNavController(findViewById(R.id.fragment))
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        navController.navigateUp()
    }

    override fun onSupportNavigateUp(): Boolean {
        val inputMethodManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        // 这里是收起键盘 ,其实也可以放到  WordsFragment 的 onResume 里面 , 但是我感觉放到这里更恰当一些
        inputMethodManager.hideSoftInputFromWindow(findViewById<View>(R.id.fragment).windowToken, 0)
        navController.navigateUp()
        return super.onSupportNavigateUp()
    }
}
