package com.example.userapplication.thirdScreen

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.data.adapter.UserAdapter
import com.example.data.response.DataItem
import com.example.userapplication.R
import com.example.userapplication.databinding.ActivityThirdScreenBinding
import com.example.userapplication.secondScreen.SecondScreen

class ThirdScreen : AppCompatActivity() {
    private lateinit var binding: ActivityThirdScreenBinding
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: ThirdViewModel
    private var userName: String? = null
    private var isLoading = false
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        swipeRefreshLayout = binding.swipeRefreshLayout

        adapter = UserAdapter()
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[ThirdViewModel::class.java]

        userName = intent.getStringExtra("USER_NAME")

        supportActionBar?.title = (getString(R.string.third_screen))


        binding.apply {
            rvListUser.layoutManager = LinearLayoutManager(this@ThirdScreen)
            rvListUser.setHasFixedSize(true)
            rvListUser.adapter = adapter

            swipeRefreshLayout.setOnRefreshListener {
                viewModel.refreshData()
            }

            rvListUser.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val totalItemCount = layoutManager.itemCount
                    val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

                    if (!isLoading && (lastVisibleItemPosition + 5) >= totalItemCount) {
                        viewModel.loadNextPage()
                    }
                }
            })
        }

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(user: DataItem) {

                val intent = Intent(this@ThirdScreen, SecondScreen::class.java)

                intent.putExtra("USER_NAME", userName)

                val userData = Bundle().apply {
                    putString("firstName", user.firstName)
                    putString("lastName", user.lastName)
                }

                intent.putExtra("selectedUserData", userData)
                startActivity(intent)
            }
        })

        viewModel.getAllUsers()
        vmObserver()
    }


    private fun vmObserver() {
        viewModel.listUsers.observe(this) { listUser ->
            listUser?.let {
                if (it.isNotEmpty()) {
                    adapter.setListUser(it)
                } else {
                    Toast.makeText(this@ThirdScreen, "No data available", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}