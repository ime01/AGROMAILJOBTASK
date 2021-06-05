package com.flowz.agromailjobtask.ui.fragments.landingdashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.flowz.agromailjobtask.R
import com.flowz.agromailjobtask.databinding.FragmentLandingStatisticsBinding
import com.flowz.agromailjobtask.ui.fragments.farm.FarmsViewModel
import com.flowz.agromailjobtask.ui.fragments.farmers.FarmersViewModel
import com.flowz.byteworksjobtask.util.playAnimation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LandingStatisticsFragment : Fragment(R.layout.fragment_landing_statistics) {

    private var _binding: FragmentLandingStatisticsBinding? = null
    private val binding get() = _binding!!
    private var totalNumberOfFarmsCaptured: Int? = 3
    private var totalNumberOfFarmersCaptured: Int? = 5
    private val farmviewModel: FarmsViewModel by activityViewModels()
    private val farmersviewModel: FarmersViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentLandingStatisticsBinding.bind(view)
        val navController : NavController = Navigation.findNavController(view)

        playAnimation(requireContext(), R.anim.blink, binding.farmers)
        playAnimation(requireContext(), R.anim.blink, binding.farmland)
        playAnimation(requireContext(), R.anim.blink, binding.totalFarmers)
        playAnimation(requireContext(), R.anim.blink, binding.totalFarms)

        farmviewModel.farmsFromDb.observe(viewLifecycleOwner, Observer {
            totalNumberOfFarmsCaptured = it.size
        })

        farmersviewModel.farmersFromDb.observe(viewLifecycleOwner, Observer {
            totalNumberOfFarmersCaptured = it.size
        })


        binding.apply {

            openFarmers.setOnClickListener {
                navController.navigate(R.id.action_landingStatisticsFragment_to_farmersListFragment)
            }

            openFarms.setOnClickListener {
                navController.navigate(R.id.action_landingStatisticsFragment_to_farmFragment)
            }

            totalFarms.text = "Total Farms Captured ${totalNumberOfFarmsCaptured}"
            totalFarmers.text = "Total Farmers Captured ${totalNumberOfFarmersCaptured}"
        }
    }

}