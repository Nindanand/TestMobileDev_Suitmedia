package com.example.userapplication.secondScreen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.userapplication.R
import com.example.userapplication.databinding.ActivitySecondScreenBinding
import com.example.userapplication.firstScreen.SharedViewModel
import com.example.userapplication.thirdScreen.ThirdScreen

class SecondScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySecondScreenBinding
    private val viewModel by viewModels<SharedViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.chooseButton.setOnClickListener {
            startActivity(Intent(this, ThirdScreen::class.java))
        }

        val userData = intent.getBundleExtra("selectedUserData")

        if (userData == null) {
            binding.tvSelectedName.text = (getString(R.string.name))

        } else {
            val firstName = userData.getString("firstName")
            val lastName = userData.getString("lastName")

            binding.tvSelectedName.text = ("$firstName $lastName")
        }

        viewModel.getName()?.let { name ->
            Log.d("SecondScreen", "Name retrieved: $name")
            binding.tvName.text = name
        }

        supportActionBar?.title = (getString(R.string.second_screen))
    }
}