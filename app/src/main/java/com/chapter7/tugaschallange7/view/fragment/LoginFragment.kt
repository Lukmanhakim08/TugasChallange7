package com.chapter7.tugaschallange7.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.chapter7.tugaschallange7.R
import com.chapter7.tugaschallange7.databinding.FragmentLoginBinding
import com.chapter7.tugaschallange7.mater.datastore.LoginUserManager
import com.chapter7.tugaschallange7.model.DataUserResponseItem
import com.chapter7.tugaschallange7.viewmodel.ViewModelUser
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {
    private var loginFragmentBinding: FragmentLoginBinding? = null
    private lateinit var loginUserManager: LoginUserManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentLoginBinding.bind(view)
        loginFragmentBinding = binding

        loginFragmentBinding!!.textRegister.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_loginFragment_to_registerFragment)
        }

        loginFragmentBinding!!.btnLogin.setOnClickListener {
            initApiUserViewModel()
        }
    }

    private fun initApiUserViewModel(){
        val viewModelUser = ViewModelProvider(this).get(ViewModelUser::class.java)
        viewModelUser.user.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()){
                AuthUser(it)
            }
        }
    }

    private fun AuthUser(listUser: List<DataUserResponseItem>) {
        val inputEmail = loginFragmentBinding!!.etEmail.text.toString()
        val inputPassw = loginFragmentBinding!!.etPassword.text.toString()
        loginUserManager = LoginUserManager(requireContext())

        if (inputEmail.isNotEmpty() && inputPassw.isNotEmpty()){
            for (i in listUser.indices){
                if (inputEmail == listUser[i].email && inputPassw == listUser[i].password ) {
                    //menyimpan ke dalam datastore
                    GlobalScope.launch {
                        loginUserManager.setBoolean(true)
                        loginUserManager.saveDataLogin(
                            listUser[i].alamat,
                            listUser[i].email,
                            listUser[i].id,
                            listUser[i].image,
                            listUser[i].name,
                            listUser[i].password,
                            listUser[i].tanggal_lahir,
                            listUser[i].username
                        )
                    }
                    Navigation.findNavController(requireView())
                        .navigate(R.id.action_loginFragment_to_homeFragment)
                    Toast.makeText(requireContext(), "Login berhasil", Toast.LENGTH_SHORT).show()
                    break

                } else if (i == listUser.lastIndex && inputEmail != listUser[i].email && inputPassw != listUser[i].password ){
                    Toast.makeText(requireContext(), "Email atau password salah", Toast.LENGTH_SHORT).show()
                }
            }
        } else{
            Toast.makeText(requireContext(), "Email dan password harus diisi", Toast.LENGTH_SHORT).show()
        }
    }
}