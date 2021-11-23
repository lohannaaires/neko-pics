package com.lohanna.nekopics.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import com.lohanna.nekopics.R
import com.lohanna.nekopics.databinding.FragmentOnlineBinding
import com.lohanna.nekopics.ui.adapter.CatAdapter
import com.lohanna.nekopics.utils.DataHandler
import com.lohanna.nekopics.utils.logData
import com.lohanna.nekopics.utils.showCustomActionBar
import com.lohanna.nekopics.viewmodel.OnlineViewModel
import javax.inject.Inject
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnlineFragment : Fragment(R.layout.fragment_online) {

    private lateinit var binding: FragmentOnlineBinding

    val viewModel: OnlineViewModel by viewModels()

    @Inject
    lateinit var catAdapter: CatAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showCustomActionBar(activity as AppCompatActivity)

        binding = FragmentOnlineBinding.bind(view)
        init()

        viewModel.catsList.observe(viewLifecycleOwner, Observer { dataHandler ->
            when (dataHandler) {
                is DataHandler.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    catAdapter.differ.submitList(dataHandler.data)
                }
                is DataHandler.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    logData("onViewCreated: ERROR " + dataHandler.message)
                }
                is DataHandler.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    logData("onViewCreated: LOADING")
                }
            }

        })

        viewModel.getCats()

    }

    private fun init() {
        binding.recyclerView.apply {
            adapter = catAdapter
        }

    }

}