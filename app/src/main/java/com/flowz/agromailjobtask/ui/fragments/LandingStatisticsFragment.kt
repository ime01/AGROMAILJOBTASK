package com.flowz.agromailjobtask.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.flowz.agromailjobtask.R
import com.flowz.agromailjobtask.databinding.FragmentLandingStatisticsBinding
import com.flowz.agromailjobtask.ui.fragments.farm.FarmsViewModel
import com.flowz.byteworksjobtask.util.playAnimation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LandingStatisticsFragment : Fragment(R.layout.fragment_landing_statistics) {

    private var _binding: FragmentLandingStatisticsBinding? = null
    private val binding get() = _binding!!
    private var totalNumberOfFarmsCaptured: Int? = null
    private val viewModel: FarmsViewModel by activityViewModels()


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

        viewModel.farmsFromDb.observe(viewLifecycleOwner, Observer {
            totalNumberOfFarmsCaptured = it.size
        })


        binding.apply {

            openFarmers.setOnClickListener {
                navController.navigate(R.id.action_landingStatisticsFragment_to_farmersListFragment)
            }

            openFarms.setOnClickListener {
                navController.navigate(R.id.action_landingStatisticsFragment_to_farmFragment)
            }

            totalFarms.text = "Total Farms Captured ${totalNumberOfFarmsCaptured}"
        }
    }

}