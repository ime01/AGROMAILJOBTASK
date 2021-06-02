package com.flowz.agromailjobtask.ui

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.createDataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.flowz.agromailjobtask.R
import com.flowz.agromailjobtask.databinding.FragmentLoginBinding
import com.flowz.byteworksjobtask.util.playAnimation
import com.flowz.byteworksjobtask.util.showSnackbar
import com.flowz.byteworksjobtask.util.takeWords
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var readFirstName: String
    private lateinit var readPasswordName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataStore = requireContext().createDataStore(name = "LOGIN")
        val email = "test@tellerium.io"
        val password = "password"

        lifecycleScope.launch {
            saveLoginInfo(FIRSTNAME, email, PASSWORD, password)
        }

        _binding = FragmentLoginBinding.bind(view)


        playAnimation(this.requireContext(), R.anim.bounce, binding.person )
        val navController : NavController = Navigation.findNavController(view)

        lifecycleScope.launch {
            readFirstName = ReadLoginInfo(FIRSTNAME).toString()
            readPasswordName = ReadLoginInfo(PASSWORD).toString()
        }

        binding.apply {

            adAdminLogin.setOnClickListener {
                if (TextUtils.isEmpty(lgFirstName.text.toString())){
                    lgFirstName.setError(getString(R.string.firstname_error))
                    return@setOnClickListener
                }

                else if (TextUtils.isEmpty(lgPassword.text.toString())){
                    lgPassword.setError(getString(R.string.lastname_error))
                    return@setOnClickListener
                }else{

                    if (lgFirstName.takeWords() == readFirstName &&  lgPassword.takeWords() == readPasswordName){
                        navController.navigate(R.id.action_loginFragment_to_farmersListFragment)
                        showSnackbar(lgFirstName, getString(R.string.login_success))
                    }else{
                        showSnackbar(lgFirstName, getString(R.string.correct_details))
                    }

                }
            }

            noAccount.setOnClickListener {
                showSnackbar(noAccount, getString(R.string.to_be_implemented))
            }
            forgottenPassword.setOnClickListener {
                showSnackbar(noAccount, getString(R.string.to_be_implemented))
            }



        }

    }


    private suspend fun ReadLoginInfo(key:String): String?{
        val dataStorekey = preferencesKey<String>(key)
        val preferences = dataStore.data.first()
        return preferences[dataStorekey]
    }

    private suspend fun saveLoginInfo(firstNamekey:String, firstNameValue:String, passwordkey:String, passWordValue:String){
        val firstNameDataStorekey = preferencesKey<String>(firstNamekey)
        val passwordDataStorekey = preferencesKey<String>(passwordkey)

        dataStore.edit {login->
            login[firstNameDataStorekey] = firstNameValue
            login[passwordDataStorekey] = passWordValue
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

        val FIRSTNAME = "FIRSTNAME"
        val PASSWORD = "PASSWORD"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}