package com.flowz.agromailjobtask.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.paging.LoadState
import androidx.paging.filter
import androidx.recyclerview.widget.LinearLayoutManager
import com.flowz.agromailjobtask.R
import com.flowz.agromailjobtask.adapter.FarmersPagingAdapter
import com.flowz.agromailjobtask.databinding.FragmentFarmersListBinding
import com.flowz.agromailjobtask.models.networkmodels.Farmer
import com.flowz.agromailjobtask.models.roomdbmodels.Farm
import com.flowz.agromailjobtask.models.roomdbmodels.RdbFarmer
import com.flowz.agromailjobtask.ui.FarmersViewModel
import com.flowz.byteworksjobtask.util.showSnackbar
import com.flowz.paging3withflow.ui.gridview.FarmerLoadStateAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class FarmersListFragment : Fragment(R.layout.fragment_farmers_list), FarmersPagingAdapter.RowClickListener {

    private var _binding: FragmentFarmersListBinding? = null
    private val binding get() = _binding!!
    private lateinit var farmerAdapter: FarmersPagingAdapter
    private val viewModel: FarmersViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_farmers_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentFarmersListBinding.bind(view)

        loadRecyclerView()
        loadData()

        farmerAdapter.addLoadStateListener { loadState->

            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                farmerRecycler.isVisible = loadState.source.refresh is LoadState.NotLoading
                buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
                errorText.isVisible = loadState.source.refresh is LoadState.Error

                if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached&& farmerAdapter.itemCount<1){
                    farmerRecycler.isVisible = false
                    errorText.isVisible = true
                }else{
                    errorText.isVisible = false
                }
            }

        }

        binding.buttonRetry.setOnClickListener {
            farmerAdapter.retry()
        }


    }

    private fun loadRecyclerView(){

        farmerAdapter = FarmersPagingAdapter(this@FarmersListFragment)

        binding.apply {

            farmerRecycler.apply {
                layoutManager = LinearLayoutManager(this.context)
                adapter = farmerAdapter.withLoadStateHeaderAndFooter(
                    header = FarmerLoadStateAdapter{farmerAdapter.retry()},
                    footer = FarmerLoadStateAdapter{farmerAdapter.retry()}
                )
                setHasFixedSize(true)
            }

            fab.setOnClickListener {
                Navigation.findNavController(requireView()).navigate(R.id.action_farmersListFragment_to_editFarmerFragment)
            }

        }
    }


    private fun loadData(){
        lifecycleScope.launch {
            viewModel.farmersDataFromNetwork.collect {
                Log.e("Farmers","$it")
                farmerAdapter.submitData(it)
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onEditClickListener(farmer: Farmer) {
        val action = FarmersListFragmentDirections.actionFarmersListFragmentToEditFarmerFragment()
        action.farmer = farmer
        Navigation.findNavController(requireView()).navigate(action)
    }

    override fun onDeleteClickListener(farmer: Farmer) {

        val farmerTobeDeleted = RdbFarmer(farmer.farmerId, farmer.fullName, farmer.lga, null, farmer.state)

        AlertDialog.Builder(this.requireContext()).setTitle(getString(R.string.delete_farmer))
            .setMessage(getString(R.string.sure_to_delete))
            .setPositiveButton(getString(R.string.ok)) { _, _ ->
                viewModel.deleteFarmer(farmerTobeDeleted)

                Snackbar.make(binding.farmerRecycler, " ${farmer.fullName} Deleted", Snackbar.LENGTH_LONG)
                    .setAction("UNDO"){ viewModel.insertFarmer(farmerTobeDeleted)}.show()

            }
            .setNegativeButton(getString(R.string.cancel_delete)){ _, _ -> }
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()

    }

}