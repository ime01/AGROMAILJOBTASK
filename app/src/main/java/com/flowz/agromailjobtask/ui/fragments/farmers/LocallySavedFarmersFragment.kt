package com.flowz.agromailjobtask.ui.fragments.farmers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.flowz.agromailjobtask.R
import com.flowz.agromailjobtask.adapter.LocalAdapter
import com.flowz.agromailjobtask.databinding.FragmentLocallySavedFarmersBinding
import com.flowz.agromailjobtask.models.roomdbmodels.RdbFarmer
import com.flowz.agromailjobtask.ui.fragments.farmers.FarmersViewModel
import com.flowz.byteworksjobtask.util.showSnackbar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LocallySavedFarmersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LocallySavedFarmersFragment : Fragment(R.layout.fragment_locally_saved_farmers), LocalAdapter.RowClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentLocallySavedFarmersBinding? = null
    private val binding get() = _binding!!
    private lateinit var localAdapter: LocalAdapter
    private val viewModel: FarmersViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentLocallySavedFarmersBinding.bind(view)

        loadRecyclerView()
    }

    private fun loadRecyclerView() {

        localAdapter = LocalAdapter(this@LocallySavedFarmersFragment)

        viewModel.farmersFromDb.observe(viewLifecycleOwner, Observer {
            localAdapter.submitList(it)
        })

        binding.apply {

            rvUpdatedNSavedLocally.apply {
                layoutManager = LinearLayoutManager(this.context)
                adapter = localAdapter
            }
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LocallySavedFarmersFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onEditClickListener(farmer: RdbFarmer) {
        showSnackbar(binding.rvUpdatedNSavedLocally, getString(R.string.getting_job))
    }

    override fun onDeleteClickListener(farmer: RdbFarmer) {
        showSnackbar(binding.rvUpdatedNSavedLocally, getString(R.string.getting_job))
    }
}