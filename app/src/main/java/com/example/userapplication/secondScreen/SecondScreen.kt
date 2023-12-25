package com.example.userapplication.secondScreen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.userapplication.R
import com.example.userapplication.databinding.ActivitySecondScreenBinding
import com.example.userapplication.firstScreen.SharedViewModel
import com.example.userapplication.thirdScreen.ThirdScreen

class SecondScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySecondScreenBinding
    private lateinit var viewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        viewModel.name.observe(this) {
            Log.d("SecondScreen", "Observed new name: $it")
            binding.tvName.text = it
        }
        supportActionBar?.title = (getString(R.string.second_screen))
        chooseButtonAction()
    }

    private fun chooseButtonAction() {
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
    }
}