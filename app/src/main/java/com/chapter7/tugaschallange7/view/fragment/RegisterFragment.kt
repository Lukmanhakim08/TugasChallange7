package com.chapter7.tugaschallange7.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.chapter7.tugaschallange7.R
import com.chapter7.tugaschallange7.databinding.FragmentRegisterBinding
import com.chapter7.tugaschallange7.model.RequesUser
import com.chapter7.tugaschallange7.viewmodel.ViewModelUser
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {
    private var registerFragmentBinding : FragmentRegisterBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentRegisterBinding.bind(view)
        registerFragmentBinding = binding

        registerFragmentBinding!!.btnRegister.setOnClickListener {
            register()
        }
    }

    private fun register(){
        val viewModelUser = ViewModelProvider(this).get(ViewModelUser::class.java)
        val namalengkap = registerFragmentBinding!!.edtNamalengkap.text.toString()
        val username = registerFragmentBinding!!.edtUsername.text.toString()
        val alamat = registerFragmentBinding!!.edtAlamat.text.toString()
        val tanggalLahir = registerFragmentBinding!!.edtTgllahir.text.toString()
        val image = "http://placeimg.com/640/480/city"
        val password = registerFragmentBinding!!.edtPassword.text.toString()
        val email = registerFragmentBinding!!.edtEmail.text.toString()
        val konfirpasswor = registerFragmentBinding!!.edtKonfirmasi.text.toString()

        if (namalengkap.isNotEmpty() &&
                username.isNotEmpty()&&
                alamat.isNotEmpty()&&
                tanggalLahir.isNotEmpty()&&
                password.isNotEmpty()&&
                email.isNotEmpty()&&
                konfirpasswor.isNotEmpty()
        ){
          if (password == konfirpasswor){
              CoroutineScope(Dispatchers.Main).launch {
                  viewModelUser.insertNewUSer(
                      RequesUser(
                          alamat,
                          email,
                          image,
                          namalengkap,
                          password,
                          tanggalLahir,
                          username
                      )
                  ).also {
                      Toast.makeText(requireContext(), "Berhasil register", Toast.LENGTH_SHORT).show()
                  }
              }
              Navigation.findNavController(requireView())
                  .navigate(R.id.action_registerFragment_to_loginFragment)
          }else{
              Toast.makeText(requireContext(), "Password dan konfirmasi password harus sama", Toast.LENGTH_SHORT).show()
          }
        } else{
            Toast.makeText(requireContext(), "Semua inputan harus diisi", Toast.LENGTH_SHORT).show()
        }
    }
}