package com.flowz.agromailjobtask.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.flowz.agromailjobtask.R
import com.flowz.agromailjobtask.databinding.FragmentLandingStatisticsBinding


class LandingStatisticsFragment : Fragment(R.layout.fragment_landing_statistics) {

    private var _binding: FragmentLandingStatisticsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentLandingStatisticsBinding.bind(view)
        val navController : NavController = Navigation.findNavController(view)


        binding.apply {

            openFarmers.setOnClickListener {
                navController.navigate(R.id.action_landingStatisticsFragment_to_farmersListFragment)
            }

            openFarms.setOnClickListener {
                navController.navigate(R.id.action_landingStatisticsFragment_to_farmFragment)
            }
        }
    }

}