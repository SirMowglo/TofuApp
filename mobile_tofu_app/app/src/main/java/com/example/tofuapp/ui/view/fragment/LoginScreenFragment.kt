package com.example.tofuapp.ui.view.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tofuapp.R
import com.example.tofuapp.data.model.dto.auth.LoginRequestDTO
import com.example.tofuapp.databinding.FragmentLoginScreenBinding
import com.example.tofuapp.ui.viewmodel.AuthViewModel
import com.example.tofuapp.ui.viewmodel.CoroutineErrorHandler
import com.example.tofuapp.ui.viewmodel.TokenViewModel
import com.example.tofuapp.util.ApiResponse
import com.example.tofuapp.util.hasEnoughDigits
import com.example.tofuapp.util.isLongEnough
import com.example.tofuapp.util.isMixedCase
import com.example.tofuapp.util.toggleVisibility
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Pattern

@AndroidEntryPoint
class LoginScreenFragment : Fragment() {
    private val binding by lazy { FragmentLoginScreenBinding.inflate(layoutInflater) }
    private val viewModel: AuthViewModel by viewModels()
    private val tokenViewModel: TokenViewModel by activityViewModels()

    private val inputValidator = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            with(binding) {
                val usernameText: String = loginInputUsername.text.toString().trim()
                val passwordText: String = loginInputPassword.text.toString().trim()

                if(!passwordText.isLongEnough())
                    loginInputLayoutPassword.error = "Password needs at least 8 characters"
                else loginInputLayoutPassword.error = ""

                if(!passwordText.isMixedCase())
                    loginInputLayoutPassword.error = "Password needs at least one uppercase and one lowercase"
                if(!passwordText.hasEnoughDigits())
                    loginInputLayoutPassword.error = "Password needs at least one digit"

                if(usernameText.isEmpty())
                    loginInputLayoutUsername.error = "Username can't be empty"
                else loginInputLayoutUsername.error = ""

                loginButtonLogin.isEnabled = isValidPassword(passwordText) && usernameText.isNotEmpty()
            }
        }

        override fun afterTextChanged(s: Editable?) {}

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        tokenViewModel.token.observe(viewLifecycleOwner) { token ->
            if (token != null) {
                findNavController().navigate(R.id.fromLoginScreenFragmentToUserScreenFragment)
            }
        }

        viewModel.loginResponse.observe(viewLifecycleOwner) { login ->
            when (login) {
                is ApiResponse.Failure -> {
                    binding.loginButtonLogin.toggleVisibility(true)
                    binding.loginCircularLoading.toggleVisibility(false)
                    binding.loginLabelSlogan.text = login.errorMessage
                }
                ApiResponse.Loading -> {
                    binding.loginButtonLogin.toggleVisibility(false)
                    binding.loginCircularLoading.toggleVisibility(true)
                }

                is ApiResponse.Success -> {
                    binding.loginButtonLogin.toggleVisibility(true)
                    binding.loginCircularLoading.toggleVisibility(false)
                    tokenViewModel.saveToken(login.data.token)
                }
            }
        }

        with(binding) {
            loginInputUsername.addTextChangedListener(inputValidator)
            loginInputPassword.addTextChangedListener(inputValidator)

            loginButtonLogin.setOnClickListener {
                viewModel.login(
                    LoginRequestDTO(
                        loginInputUsername.text.toString().trim(),
                        loginInputPassword.text.toString().trim(),
                    ),
                    object : CoroutineErrorHandler {
                        override fun onError(message: String) {
                            binding.loginLabelSlogan.text = "Error: $message"
                        }
                    }
                )
            }
        }

    }

    fun isValidPassword(password: String): Boolean {
        val requirements = listOf(String::isLongEnough, String::hasEnoughDigits, String::isMixedCase)
        return requirements.all { check -> check(password) }
    }
}