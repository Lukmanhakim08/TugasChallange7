package com.chapter7.tugaschallange7.view.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.bumptech.glide.Glide
import com.chapter7.tugaschallange7.R
import com.chapter7.tugaschallange7.databinding.FragmentProfileBinding
import com.chapter7.tugaschallange7.mater.datastore.LoginUserManager
import com.chapter7.tugaschallange7.model.RequesUser
import com.chapter7.tugaschallange7.viewmodel.ViewModelUser
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private var profileFragmentBinding: FragmentProfileBinding? = null
    private lateinit var loginUserManager: LoginUserManager
    private lateinit var takeImage: ActivityResultLauncher<Intent>
    private lateinit var viewModelUser: ViewModelUser

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        takeImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ data ->
            if (data.resultCode == Activity.RESULT_OK){
                handleImageTakenFromCamera(data.data)
            }
        }
        val binding = FragmentProfileBinding.bind(view)
        profileFragmentBinding = binding

        initFiel()

        profileFragmentBinding!!.profileButtonLogout.setOnClickListener {
            logout()
        }

        profileFragmentBinding!!.profileButtonUpdate.setOnClickListener {
            updateData()
        }

        profileFragmentBinding!!.buttonEditProfileImage.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Ubah Foto Profile")
                .setMessage("Pilih metode untuk mengambil foto profile yang baru")
                .setNeutralButton("Batal"){ dialogInterface: DialogInterface, i: Int ->
                    dialogInterface.dismiss()
                }
                .setPositiveButton("Penyimpanan"){ dialogInterface: DialogInterface, i: Int ->
                    openImageGallery()
                }
                .setNeutralButton("Kamera"){ dialogInterface: DialogInterface, i: Int ->
                    openCamera()
                }.show()
        }
    }

    private fun updateData(){
        loginUserManager = LoginUserManager(requireContext())
        var id = ""
        val alamat = profileFragmentBinding!!.profileAlamat.text.toString()
        val email = profileFragmentBinding!!.profileEmail.text.toString()
        val image = "http://placeimg.com/640/480/city"
        val username = profileFragmentBinding!!.profileUsername.text.toString()
        val tanggalLahir = profileFragmentBinding!!.profileTanggalLahir.text.toString()
        val password = profileFragmentBinding!!.profilePassword.text.toString()
        val namaLengkap = profileFragmentBinding!!.profileNamaLengkap.text.toString()

        loginUserManager.IDuser.asLiveData().observe(viewLifecycleOwner){
            id = it.toString()
        }

        AlertDialog.Builder(requireContext())
            .setTitle("Update data")
            .setMessage("Yakin ingin mengupdate data?")
            .setNegativeButton("TIDAK"){ dialogInterface: DialogInterface, i: Int ->
                dialogInterface.dismiss()
            }
            .setPositiveButton("YA"){ dialogInterface: DialogInterface, i: Int ->
                viewModelUser = ViewModelProvider(this).get(ViewModelUser::class.java)
                viewModelUser.updateDataUser(
                    id, RequesUser(
                        alamat,
                        email,
                        image,
                        namaLengkap,
                        password,
                        tanggalLahir,
                        username
                    )
                )
                Toast.makeText(requireContext(), "Update data berhasil", Toast.LENGTH_SHORT).show()
                GlobalScope.launch {
                    loginUserManager.saveDataLogin(
                        alamat,
                        email,
                        id,
                        image,
                        namaLengkap,
                        password,
                        tanggalLahir,
                        username
                    )
                }
                //mengulang activity
                val mIntent = activity?.intent
                activity?.finish()
                startActivity(mIntent)
            }.show()
    }

    private fun initFiel(){
        loginUserManager = LoginUserManager(requireContext())
        loginUserManager.imageProfile.asLiveData().observe(viewLifecycleOwner){
            if (it.isNullOrEmpty()){
                loginUserManager.image.asLiveData().observe(viewLifecycleOwner){ result ->
                    Glide.with(profileFragmentBinding!!.profileImage.context)
                        .load(result)
                        .error(R.drawable.profile_photo)
                        .override(100, 100)
                        .into(profileFragmentBinding!!.profileImage)
                }

            } else{
                profileFragmentBinding!!.profileImage.setImageBitmap(convertStringToBitmap(it))
            }
        }

        loginUserManager.name.asLiveData().observe(viewLifecycleOwner){
            profileFragmentBinding!!.profileNamaLengkap.setText(it.toString())
        }

        loginUserManager.dateOfBirth.asLiveData().observe(viewLifecycleOwner){
            profileFragmentBinding!!.profileTanggalLahir.setText(it.toString())
        }
        loginUserManager.address.asLiveData().observe(viewLifecycleOwner){
            profileFragmentBinding!!.profileAlamat.setText(it.toString())
        }
        loginUserManager.email.asLiveData().observe(viewLifecycleOwner){
            profileFragmentBinding!!.profileEmail.setText(it.toString())
        }
        loginUserManager.username.asLiveData().observe(viewLifecycleOwner){
            profileFragmentBinding!!.profileUsername.setText(it.toString())
        }
        loginUserManager.password.asLiveData().observe(viewLifecycleOwner){
            profileFragmentBinding!!.profilePassword.setText(it.toString())
            profileFragmentBinding!!.profileKonfirmasiPassword.setText(it.toString())
        }
    }

    private val bitmapResult = registerForActivityResult(ActivityResultContracts.GetContent()){ result ->
        val originBitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, result)
        loginUserManager = LoginUserManager(requireContext())
        val editedBitmap: Bitmap

        if (originBitmap.width >= originBitmap.height){
            editedBitmap = Bitmap.createBitmap(
                originBitmap,
                originBitmap.width / 2 - originBitmap.height / 2,
                0,
                originBitmap.height,
                originBitmap.height

            )
        } else {
            editedBitmap = Bitmap.createBitmap(
                originBitmap,
                0,
                originBitmap.height / 2 - originBitmap.width / 2,
                originBitmap.width,
                originBitmap.width
            )
        }
        val bitmap = Bitmap.createScaledBitmap(editedBitmap, 720, 720, true)
        val stringResult = convertBitMapToString(bitmap)!!

        GlobalScope.launch {
            loginUserManager.setImageProfile(stringResult)
        }
        profileFragmentBinding!!.profileImage.setImageBitmap(convertStringToBitmap(stringResult))
        Toast.makeText(requireContext(), "Foto profile berhasil diupdate", Toast.LENGTH_SHORT).show()
    }

    private fun openImageGallery(){
        bitmapResult.launch("image/*")
    }

    private fun handleImageTakenFromCamera(a: Intent?){
        val originBitmap = a?.extras?.get("data") as Bitmap
        val editedBitmap: Bitmap

        if (originBitmap.width >= originBitmap.height){
            editedBitmap = Bitmap.createBitmap(
                originBitmap,
                originBitmap.width / 2 - originBitmap.height / 2,
                0,
                originBitmap.height,
                originBitmap.height
            )
        } else {
            editedBitmap = Bitmap.createBitmap(
                originBitmap,
                0,
                originBitmap.height / 2 - originBitmap.width / 2,
                originBitmap.width,
                originBitmap.width
            )
        }

        val stringResult = convertBitMapToString(editedBitmap)
        GlobalScope.launch {
            loginUserManager.setImageProfile(stringResult!!)
        }
        profileFragmentBinding!!.profileImage.setImageBitmap(convertStringToBitmap(stringResult))
        Toast.makeText(requireContext(), "Foto profile berhasil diupdate", Toast.LENGTH_SHORT).show()
    }

    //convert a bitmap to string
    private fun convertBitMapToString(input: Bitmap): String? {
        val byteArray = ByteArrayOutputStream()
        input.compress(Bitmap.CompressFormat.PNG, 100, byteArray)
        val b: ByteArray = byteArray.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    //convert a string to bitmap
    private fun convertStringToBitmap(input: String?): Bitmap? {
        val byteArray1: ByteArray = Base64.decode(input, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(
            byteArray1, 0,
            byteArray1.size
        )
    }

    private fun openCamera(){
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE)
        takeImage.launch(cameraIntent)
    }

    private fun logout(){
        loginUserManager = LoginUserManager(requireContext())
        AlertDialog.Builder(requireContext())
            .setTitle("Logout")
            .setMessage("Apakah anda yakin ingin logout")
            .setNegativeButton("TIDAK"){ dialogInterface: DialogInterface, i: Int ->
                dialogInterface.dismiss()
            }
            .setPositiveButton("YA"){ dialogInterface: DialogInterface, i: Int ->
                GlobalScope.launch {
                    loginUserManager.clearDataLogin()
                }
                val mIntent = activity?.intent
                activity?.finish()
                startActivity(mIntent)
            }.show()
    }

}