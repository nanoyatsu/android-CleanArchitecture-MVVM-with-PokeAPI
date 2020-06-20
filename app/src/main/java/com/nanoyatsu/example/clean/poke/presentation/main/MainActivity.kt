package com.nanoyatsu.example.clean.poke.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.nanoyatsu.example.clean.poke.R
import com.nanoyatsu.example.clean.poke.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
//        setContentView(R.layout.activity_main)
//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.container, IndexFragment.newInstance())
//                .commitNow()
//        }
    }

    // todo navigation
    // todo poke_detail
    // todo room
    // todo cache
}
