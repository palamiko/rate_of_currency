package com.example.rateofcurrency.ui.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.rateofcurrency.R
import com.example.rateofcurrency.databinding.FragmentAuthorizationBinding
import com.example.rateofcurrency.models.entities.AuthData
import com.example.rateofcurrency.models.viewmodel.AuthViewModel
import com.example.rateofcurrency.utilits.Const.Companion.KEY_TOKEN_PARTNER
import com.example.rateofcurrency.utilits.Const.Companion.KEY_TOKEN_PEANUT
import com.example.rateofcurrency.utilits.Const.Companion.KEY_USER_LOGIN
import com.example.rateofcurrency.utilits.Const.Companion.KEY_USER_PASSWD
import com.example.rateofcurrency.utilits.Const.Companion.SHARED_PREF_NAME
import kotlinx.serialization.ExperimentalSerializationApi


class AuthorizationFragment : BaseFragment(R.layout.fragment_authorization) {

    private var _binding: FragmentAuthorizationBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by viewModels()

    private lateinit var sharedPref: SharedPreferences
    private lateinit var editorSharedPref: SharedPreferences.Editor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthorizationBinding.inflate(inflater, container, false)
        return binding.root
    }

    @ExperimentalSerializationApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()

        sharedPref = requireActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        editorSharedPref = sharedPref.edit()

        binding.signIn.setOnClickListener {
            signIn()
        }
    }

    @ExperimentalSerializationApi
    private fun signIn() {
        val login = binding.teUserLogin.text.toString().toIntOrNull()
        val password = binding.tePassword.text.toString()

        if (login == null) {
            showToast("Введите логин")

        } else if (password.isEmpty()) {
            showToast("Введите пароль")
        } else {
            binding.loadInput.isVisible = true
            val authData = AuthData(login = login, password = password)

            authViewModel.authInPeanut(authData)
            authViewModel.authInPartner(authData)
        }
    }

    @ExperimentalSerializationApi
    private fun navigateIfSuccess(login: Int, passwd: String, token: String) {
        // Сохраниение логина/пароля для повторного получения токена
        editorSharedPref.putInt(KEY_USER_LOGIN, 20234561).apply()
        editorSharedPref.putString(KEY_USER_PASSWD, passwd).apply()
        editorSharedPref.putString(KEY_TOKEN_PARTNER, token).apply()
    }

    @ExperimentalSerializationApi
    private fun initObservers() {

        authViewModel.authResponsePeanut.observe(viewLifecycleOwner) { authResponse ->
            if (authResponse.isSuccess()) {
                editorSharedPref.putString(KEY_TOKEN_PEANUT, authResponse.token).apply()
                Log.d ("Auth: ", "Успешно")
            } else {
                Log.d ("Auth: ", "Не верный пароль")
            }
        }

        authViewModel.authResponsePartner.observe(viewLifecycleOwner) { token ->
            if (token.isNullOrEmpty()) {
                showToast("Не верный логин/пароль")
            } else {
                binding.loadInput.isGone = true
                navigateIfSuccess(
                    login = authViewModel.login.value!!, passwd = authViewModel.pass.value!!, token = token)

                findNavController().navigate(
                    AuthorizationFragmentDirections.actionAuthorizationFragmentToCurrencyInformationFragment()
                )
            }
        }

        binding.teUserLogin.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.isNotEmpty()) authViewModel.setLogin(s.toString().toInt())

            }
        })

        binding.tePassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.isNotEmpty()) authViewModel.setPasswd(s.toString())
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}