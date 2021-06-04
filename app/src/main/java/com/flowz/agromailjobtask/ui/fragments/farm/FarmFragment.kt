package com.flowz.agromailjobtask.ui.fragments.farm

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.flowz.agromailjobtask.R
import com.flowz.agromailjobtask.adapter.FarmAdapter
import com.flowz.agromailjobtask.databinding.FragmentFarmBinding
import com.flowz.agromailjobtask.databinding.FragmentFarmersListBinding
import com.flowz.agromailjobtask.models.roomdbmodels.Farm
import com.flowz.byteworksjobtask.util.playAnimation
import com.flowz.byteworksjobtask.util.showSnackbar
import com.flowz.byteworksjobtask.util.takeWords
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FarmFragment : Fragment(R.layout.fragment_farm), FarmAdapter.RowClickListener {

    private var _binding: FragmentFarmBinding? = null
    private val binding get() = _binding!!
    private lateinit var farmAdapter: FarmAdapter
    private val viewModel: FarmsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentFarmBinding.bind(view)
        val navController: NavController = Navigation.findNavController(view)

        loadRecyclerView()

        binding.fab.setOnClickListener {

            val layoutInflater = LayoutInflater.from(requireContext())
            val alertView = layoutInflater.inflate(R.layout.add_farm_alert_dialog, null)
            val alertDialog = MaterialAlertDialogBuilder(requireContext())
            alertDialog.setView(alertView)
            alertDialog.setCancelable(true)
            val dialog = alertDialog.create()

            val farmName = alertView.findViewById<TextInputEditText>(R.id.new_farm_name)
            val farmOwner = alertView.findViewById<TextInputEditText>(R.id.new_farm_owner)
            val farmLocation = alertView.findViewById<TextInputEditText>(R.id.new_farm_location)
            val farmCoordinateLat = alertView.findViewById<TextInputEditText>(R.id.new_farmcoordinate_lat)
            val farmCoordinateLng = alertView.findViewById<TextInputEditText>(R.id.new_farmcoordinate_lng)
            val saveNewFarm = alertView.findViewById<MaterialButton>(R.id.save_new_farm)

            dialog.show()

            saveNewFarm.setOnClickListener {
                if (TextUtils.isEmpty(farmName.text.toString())) {
                    farmName.setError(getString(R.string.enter_name_error))
                    return@setOnClickListener
                } else if (TextUtils.isEmpty(farmOwner.text.toString())) {
                    farmOwner.setError(getString(R.string.enter_owner_name))
                    return@setOnClickListener
                } else if (TextUtils.isEmpty(farmLocation.text.toString())) {
                    farmLocation.setError(getString(R.string.enter_valid_location))
                    return@setOnClickListener
                } else if (TextUtils.isEmpty(farmCoordinateLat.text.toString())) {
                    farmCoordinateLat.setError(getString(R.string.enter_valid_lat))
                    return@setOnClickListener
                } else if (TextUtils.isEmpty(farmCoordinateLat.text.toString())) {
                    farmCoordinateLng.setError(getString(R.string.enter_valid_lng))
                    return@setOnClickListener
                }else{
                    val newFarm = Farm(farmName.takeWords(), farmOwner.takeWords(), farmLocation.takeWords(),  farmCoordinateLat.takeWords().toDouble(),  farmCoordinateLng.takeWords().toDouble())
                    viewModel.insertFarm(newFarm)
                    showSnackbar(binding.farmRecycler, "${newFarm.farmName} Saved")
                    dialog.dismiss()
                }
            }



        }
    }

    private fun loadRecyclerView() {

       farmAdapter = FarmAdapter(this@FarmFragment)

        viewModel.farmersFromDb.observe(viewLifecycleOwner, Observer {
            farmAdapter.submitList(it)
        })

        binding.apply {

            farmRecycler.apply {
                layoutManager = LinearLayoutManager(this.context)
                adapter = farmAdapter
            }
        }

    }

    override fun onItemClickListener(farm: Farm) {
        val action = FarmFragmentDirections.actionFarmFragmentToFarmDetailFragment()
        action.farm = farm
        Navigation.findNavController(requireView()).navigate(action)
        showSnackbar(binding.root, "Farm ${farm.farmName} Clicked")
    }
}