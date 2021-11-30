package com.lohanna.nekopics.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.lohanna.nekopics.R
import com.lohanna.nekopics.application.NekoPicsApp.Companion.getInternetStatus
import com.lohanna.nekopics.databinding.FragmentMainBinding
import com.lohanna.nekopics.ui.adapter.CatAdapter
import com.lohanna.nekopics.utils.DataHandler
import com.lohanna.nekopics.utils.logData
import com.lohanna.nekopics.utils.showCustomActionBar
import com.lohanna.nekopics.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment: Fragment(R.layout.fragment_main) {

    private lateinit var binding: FragmentMainBinding

    val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var catAdapter: CatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)


        when(getInternetStatus()) {
            true -> {
                viewModel.getCats()
            }

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showCustomActionBar((activity as AppCompatActivity))

        binding = FragmentMainBinding.bind(view)

        init()

        viewModel.catsList.observe(viewLifecycleOwner, Observer { dataHandler ->
            when (dataHandler) {
                is DataHandler.SUCCESS -> {
                    logData("onViewCreated: SUCCESS  ${dataHandler.data?.get(0)?.id} ")
                    catAdapter.differ.submitList(dataHandler.data)
                    binding.progressBar.visibility = View.GONE
                }
                is DataHandler.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    logData("onViewCreated: ERROR ${dataHandler.message}")
                }
                is DataHandler.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    //Thread.sleep(2000)
                    logData("onViewCreated: LOADING")
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.refresh -> {
                when(getInternetStatus()) {
                    true -> {
                        viewModel.getCats()
                    }
                    false -> {
                        Toast.makeText(context,"Internet connection failed",Toast.LENGTH_SHORT).show()
                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun init() {
        catAdapter.onCatClicked {
            val bundle = Bundle().apply {
                putParcelable("cat_data", it)

            }
            findNavController().navigate(
                R.id.action_mainFragment_to_catImageFragment,
                bundle
            )
        }

        binding.recyclerView.apply {
            adapter = catAdapter
        }
    }

}