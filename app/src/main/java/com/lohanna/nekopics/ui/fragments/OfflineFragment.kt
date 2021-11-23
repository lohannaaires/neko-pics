package com.lohanna.nekopics.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import com.lohanna.nekopics.R
import com.lohanna.nekopics.databinding.FragmentOfflineBinding
import com.lohanna.nekopics.ui.adapter.CatAdapter
import com.lohanna.nekopics.utils.DataHandler
import com.lohanna.nekopics.utils.logData
import com.lohanna.nekopics.utils.showCustomActionBar
import com.lohanna.nekopics.viewmodel.OfflineViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OfflineFragment : Fragment(R.layout.fragment_offline) {

    private lateinit var binding: FragmentOfflineBinding

    val viewModel: OfflineViewModel by viewModels()

    @Inject
    lateinit var catAdapter: CatAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showCustomActionBar(activity as AppCompatActivity)

        binding = FragmentOfflineBinding.bind(view)
        init()

        viewModel.catsList.observe(viewLifecycleOwner, Observer { dataHandler ->
            when (dataHandler) {
                is DataHandler.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    logData("onViewCreated: SUCCESS  ${dataHandler.data?.get(0)?.id} ")
                    catAdapter.differ.submitList(dataHandler.data)
                }
                is DataHandler.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    logData("onViewCreated: ERROR ${dataHandler.message}")
                }
                is DataHandler.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    logData("onViewCreated: LOADING")
                }
            }
        })

    }

    private fun init() {
        binding.recyclerView.apply {
            adapter = catAdapter
        }

    }

}