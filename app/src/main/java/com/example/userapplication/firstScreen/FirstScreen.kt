package com.example.userapplication.firstScreen

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.userapplication.R
import com.example.userapplication.databinding.ActivityFirstScreenBinding
import com.example.userapplication.secondScreen.SecondScreen

class FirstScreen : AppCompatActivity() {
    private lateinit var binding: ActivityFirstScreenBinding
    private var isPalindrome:Boolean = false
    private var currentImageUri: Uri? = null
    private val viewModel by viewModels<SharedViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupView()
        nextAction()
        checkAction()
        setupnameEditText()
        setuppalindromeEditText()
        binding.ivUser.setOnClickListener { startGallery() }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun nextAction() {
        binding.nextButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val palindrome = binding.palindromeEditText.text.toString()

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(palindrome)) {
                Toast.makeText(
                    this@FirstScreen,
                    "All sections are required to be filled in",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                if (checkPalindrome(palindrome)) {
                    startActivity(Intent(this, SecondScreen::class.java))
                    viewModel.setName(name)
                    Log.d("FirstScreen", "Name before setting in ViewModel: $name")
                }
                else {
                    Toast.makeText(
                        this@FirstScreen,
                        "Change text to palindrome!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.ivUser.setImageURI(it)
        }
    }

    private fun checkAction() {
        binding.checkButton.setOnClickListener {
            val inputText = binding.palindromeEditText.text.toString().toLowerCase()
            if (TextUtils.isEmpty(inputText)) {
                Toast.makeText(
                    this@FirstScreen,
                    "Palindrome sections are required to be filled in",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                if (checkPalindrome(inputText)) {
                    isPalindrome = true
                    Toast.makeText(this, "Is Palindrome", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Not Palindrome", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupnameEditText() {
        binding.nameEditText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.nameEditTextLayout.hint = ""
            } else {
                if (binding.nameEditText.text.isNullOrBlank()) {
                    binding.nameEditTextLayout.hint = getString(R.string.name)
                }
            }
        }
    }

    private fun setuppalindromeEditText() {
        binding.palindromeEditText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.palindromeEditTextLayout.hint = ""
            } else {
                if (binding.palindromeEditText.text.isNullOrBlank()) {
                    binding.palindromeEditTextLayout.hint = getString(R.string.palindrome)
                }
            }
        }
    }
    fun checkPalindrome(text: String): Boolean {
        val cleanedText = text.replace("[^a-zA-Z0-9]".toRegex(), "")
        return cleanedText == cleanedText.reversed()
    }
}