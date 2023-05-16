package com.example.courseManager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.courseManager.myViewModel.MyViewModel

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

         setContentView(R.layout.activity_main)

        // we need 1 model in activity, and 1 viewModel in each fragment

        // Create a ViewModel
        val myVM = ViewModelProvider(this)[MyViewModel::class.java]


    }

}