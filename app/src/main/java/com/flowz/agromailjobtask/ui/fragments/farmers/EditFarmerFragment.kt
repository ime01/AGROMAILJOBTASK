package com.flowz.agromailjobtask.ui.fragments.farmers

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.flowz.agromailjobtask.R
import com.flowz.agromailjobtask.databinding.FragmentEditFarmerBinding
import com.flowz.agromailjobtask.models.networkmodels.Farmer
import com.flowz.agromailjobtask.models.roomdbmodels.RdbFarmer
import com.flowz.agromailjobtask.ui.fragments.farmers.EditFarmerFragmentArgs
import com.flowz.agromailjobtask.utils.Constants
import com.flowz.byteworksjobtask.util.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditFarmerFragment : Fragment(R.layout.fragment_edit_farmer) {

    private var farmer: Farmer? = null
    private var _binding: FragmentEditFarmerBinding? = null
    private val binding get() = _binding!!
    private var imageUri : Uri? = null
    private val viewModel: FarmersViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            farmer = EditFarmerFragmentArgs.fromBundle(it).farmer
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentEditFarmerBinding.bind(view)

        binding.apply {
            farmerFullname.setText(farmer?.fullName)
            farmerLga.setText(farmer?.lga)
            farmerMobileNo.setText(farmer?.mobileNo)
            farmerState.setText(farmer?.state)

            val imageLink = Constants.IMG_BASEURL +  farmer?.passportPhoto

            Glide.with(farmerProfilePic)
                .load(imageLink)
                .circleCrop()
                .placeholder(R.drawable.ic_baseline_person_24)
                .error(R.drawable.ic_baseline_error_outline_24)
                .fallback(R.drawable.ic_baseline_person_24)
                .into(farmerProfilePic)

            editFarmerPic.setOnClickListener {

                val layoutInflater = LayoutInflater.from(requireContext())
                val alertView = layoutInflater.inflate(R.layout.camera_or_gallery_alert_dialog, null)

                val alertDialog = MaterialAlertDialogBuilder(requireContext())
                alertDialog.setView(alertView)
                alertDialog.setTitle(getString(R.string.choose_image))
                alertDialog.setCancelable(false)
                val dialog = alertDialog.create()

                val openCameraImage = alertView.findViewById<ImageView>(R.id.rg_open_camera)
                val openGalleryImage = alertView.findViewById<ImageView>(R.id.open_gallery)

                dialog.show()

                openCameraImage.setOnClickListener {
                    checkPermssion()
                    openCamera()
                    dialog.dismiss()
                }

                openGalleryImage.setOnClickListener {
                    checkPermssion()
                    pickImageFromGallery()
                    dialog.dismiss()
                }
            }


            updateFarmer.setOnClickListener {

                if (TextUtils.isEmpty(farmerFullname.text.toString())){
                    farmerFullname.setError(getString(R.string.farm_name_error))
                    return@setOnClickListener
                } else if (TextUtils.isEmpty(farmerLga.text.toString())){
                    farmerLga.setError(getString(R.string.lga_error))
                    return@setOnClickListener
                }else if(TextUtils.isEmpty(farmerMobileNo.text.toString())){
                    farmerMobileNo.setError(getString(R.string.number_error))
                    return@setOnClickListener
                }
                else if(imageUri == null){
                    showSnackbar(farmerLga, "Ensure You have chosen a profile photo")
                    return@setOnClickListener
                }

                val updatedFarmerToBeSaved = RdbFarmer(farmer?.farmerId, farmerFullname.takeWords(), farmerLga.takeWords(), imageUri, farmerState.takeWords() )
                viewModel.insertFarmer(updatedFarmerToBeSaved)
                val arrayOfViewsToClearAfterSavingEmployee = arrayOf(farmerFullname,farmerMobileNo, farmerLga, farmerState)
                clearTexts(arrayOfViewsToClearAfterSavingEmployee)
                farmerProfilePic.setImageResource(R.drawable.ic_baseline_person_24)
                showSnackbar(farmerLga, "Farmer ${farmer?.fullName} UPDATED AND SAVED LOCALLY")
            }
        }


    }

    fun checkPermssion(){
        if(Build.VERSION.SDK_INT>=23){
            if (ActivityCompat.checkSelfPermission(this.requireActivity()
                    ,android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    READIMAGE
                )
                return
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            READIMAGE ->{
                if (grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery()
                }else{
                    showToast("Cannnot access your images",this.requireContext() )
                }
            }else-> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUESTCODE && resultCode == RESULT_OK && data!!.data != null ){

            imageUri = data.data
            binding.farmerProfilePic.setImageURI(imageUri)
            showSnackbar(binding.farmerLga, "Profile pic selected for update....")
        }
        else if (requestCode == IMAGECAPUTRECODE && resultCode == RESULT_OK){

            val rgPhoto = data!!.extras?.get("data") as Bitmap

            binding.farmerProfilePic.setImageBitmap(rgPhoto)

            imageUri = getImageUri(requireContext(), rgPhoto)
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, REQUESTCODE)
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, IMAGECAPUTRECODE)
    }



    companion object {

        val READIMAGE = 10
        val REQUESTCODE = 20
        val IMAGECAPUTRECODE = 30

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditFarmerFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }


}