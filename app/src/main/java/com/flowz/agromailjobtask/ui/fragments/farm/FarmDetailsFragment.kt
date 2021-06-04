package com.flowz.agromailjobtask.ui.fragments.farm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.flowz.agromailjobtask.R
import com.flowz.agromailjobtask.models.roomdbmodels.Farm
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import dagger.hilt.android.AndroidEntryPoint

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


@AndroidEntryPoint
class FarmDetailFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var param1: String? = null
    private var param2: String? = null
    private var farm: Farm? = null
    lateinit var farmLocation: LatLng
    private var polygon: Polygon? = null
    val LatLngList = ArrayList<LatLng>()
    val MarkerList = ArrayList<Marker>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            farm = FarmDetailFragmentArgs.fromBundle(it).farm
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_farmer_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        farmLocation = LatLng(farm?.farmCoordinatesLat!!, farm?.farmCoordinatesLng!!)

       val fName =  view.findViewById<TextView>(R.id.de_farm_name)
       val fOwner =  view.findViewById<TextView>(R.id.de_farm_owner)
       val fLocation =  view.findViewById<TextView>(R.id.de_farm_location)
       val fLat =  view.findViewById<TextView>(R.id.de_farm_lat)
       val fLng =  view.findViewById<TextView>(R.id.de_farm_lng)

        fName.setText("Name of Farm: ${farm?.farmName}")
        fOwner.setText("Farm Owner: ${farm?.farmOwner}")
        fLocation.setText("Farm Location: ${farm?.farmLocation}")
        fLat.setText("Farm Latitude: ${farm?.farmCoordinatesLat}")
        fLng.setText("Farm Longitude: ${farm?.farmCoordinatesLng}")





    }

    override fun onMapReady(gmap: GoogleMap?) {
        if (gmap != null) {
            mMap = gmap
        }

        val sourceLocation = LatLng(4.815554, 7.049844)

        mMap.addMarker(MarkerOptions().position(farmLocation).title("USER FARM"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(farmLocation))

        mMap.addCircle(
            CircleOptions()
            .center(farmLocation)
            .radius(50000.0)
            .strokeWidth(3f)
            .strokeColor(getResources().getColor(R.color.design_default_color_error))
            .fillColor(getResources().getColor(R.color.design_default_color_error)))



    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FarmDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}