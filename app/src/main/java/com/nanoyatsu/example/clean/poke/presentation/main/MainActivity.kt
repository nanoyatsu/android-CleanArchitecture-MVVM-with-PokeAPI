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
    }

    // todo layout for DetailFragment
    // todo error handle for DetailFragment
    // todo onError image (define by bindingAdapter)
}
