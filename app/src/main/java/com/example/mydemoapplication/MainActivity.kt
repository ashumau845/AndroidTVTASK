package com.example.mydemoapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button

class MainActivity : Activity() {
    lateinit var vBrowseSupportFragment: Button
    lateinit var vTopTabNavigation: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        vBrowseSupportFragment = findViewById(R.id.vBrowseSupportFragment)
        vTopTabNavigation = findViewById(R.id.vTopTabNavigation)
        processListener()

    }

    fun processListener() {
        vBrowseSupportFragment.setOnClickListener {
            val intent: Intent = Intent()
            intent.setClass(this@MainActivity, BrowseSupportFragmentActivity::class.java)
            startActivity(intent)
        }
        vTopTabNavigation.setOnClickListener {
            val intent: Intent = Intent()
            intent.setClass(this@MainActivity, TopTabNavigationActivity::class.java)
            startActivity(intent)
        }
    }

}