package com.example.userapplication.secondScreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.userapplication.R
import com.example.userapplication.databinding.ActivitySecondScreenBinding
import com.example.userapplication.thirdScreen.ThirdScreen

class SecondScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySecondScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val nameUser = sharedPreferences.getString("name", "")
        if (nameUser.isNullOrBlank()) {
            binding.tvName.text = (getString(R.string.name))
        } else {
            binding.tvName.text = ("$nameUser")
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