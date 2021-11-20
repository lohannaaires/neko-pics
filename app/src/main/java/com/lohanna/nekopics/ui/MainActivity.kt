package com.lohanna.nekopics.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lohanna.nekopics.BuildConfig
import com.lohanna.nekopics.R
import com.lohanna.nekopics.adapter.GridAdapter
import com.lohanna.nekopics.databinding.ActivityMainBinding
import com.lohanna.nekopics.network.RetrofitService
import com.lohanna.nekopics.repository.MainRepository
import com.lohanna.nekopics.viewmodel.MainViewModel
import com.lohanna.nekopics.viewmodel.ViewModelFactory

const val API_KEY: String = BuildConfig.API_KEY

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: MainViewModel
    private val retrofitService = RetrofitService.getInstance()
    private val adapter = GridAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.action_bar)

        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ViewModelFactory(MainRepository(retrofitService))).get(MainViewModel::class.java)

        binding.recyclerView.adapter = adapter


        viewModel.catsList.observe(this, Observer {
            adapter.setCatsList(it)
        })

        viewModel.errorMessage.observe(this, Observer {

        })

        viewModel.getCats()
    }
}