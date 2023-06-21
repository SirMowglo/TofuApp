package com.example.tofuapp.ui.view.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tofuapp.R
import com.example.tofuapp.data.model.dto.auth.RegisterUserRequestDTO
import com.example.tofuapp.databinding.FragmentRegisterScreenBinding
import com.example.tofuapp.ui.viewmodel.AuthViewModel
import com.example.tofuapp.ui.viewmodel.CoroutineErrorHandler
import com.example.tofuapp.util.ApiResponse
import com.example.tofuapp.util.hasEnoughDigits
import com.example.tofuapp.util.isLongEnough
import com.example.tofuapp.util.isMixedCase
import com.example.tofuapp.util.isValidEmail
import com.example.tofuapp.util.toggleVisibility
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterScreenFragment : Fragment() {
    private val binding by lazy { FragmentRegisterScreenBinding.inflate(layoutInflater) }
    private val viewModel: AuthViewModel by viewModels()
    private val inputValidator = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            with(binding) {
                val usernameText: String = registerInputUsername.text.toString().trim()
                val fullnameText: String = registerInputFullname.text.toString().trim()
                val passwordText: String = registerInputPassword.text.toString().trim()
                val verifyPasswordText: String = registerInputVerifyPassword.text.toString().trim()
                val emailText: String = registerInputEmail.text.toString().trim()
                val verifyEmailText: String = registerInputVerifyEmail.text.toString().trim()

                if (usernameText.isEmpty())
                    registerInputLayoutUsername.error = "Username can't be empty"
                else registerInputLayoutUsername.error = ""

                if (fullnameText.isEmpty())
                    registerInputLayoutFullname.error = "Fullname can't be empty"
                else registerInputLayoutFullname.error = ""

                if(!passwordText.isLongEnough())
                    registerInputLayoutPassword.error = "Password needs at least 8 characters"
                else registerInputLayoutPassword.error = ""

                if(!passwordText.isMixedCase())
                    registerInputLayoutPassword.error = "Password needs at least one uppercase and one lowercase"
                if(!passwordText.hasEnoughDigits())
                    registerInputLayoutPassword.error = "Password needs at least one digit"

                if(passwordText != verifyPasswordText)
                    registerInputLayoutVerifyPassword.error = "Verified password needs to be equal to password"
                else registerInputLayoutVerifyPassword.error = ""

                if(emailText != verifyEmailText)
                    registerInputLayoutVerifyEmail.error = "Verified email needs to be equal to email"
                else registerInputLayoutVerifyEmail.error = ""

                if(!emailText.isValidEmail())
                    registerInputLayoutEmail.error = "Email needs to be a valid email format"
                else registerInputLayoutEmail.error = ""

                registerButtonLogin.isEnabled = isValidPassword(passwordText) &&
                        usernameText.isNotEmpty() &&
                        fullnameText.isNotEmpty() &&
                        (passwordText == verifyPasswordText) &&
                        (emailText == verifyEmailText) &&
                        emailText.isValidEmail()
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

        with(binding) {

            validateButton(inputValidator, viewModel)

            registerToolbar.setNavigationOnClickListener {
                findNavController().navigate(R.id.fromRegisterScreenFragmentToLoginScreenFragment)
            }
        }

        viewModel.registerResponse.observe(viewLifecycleOwner) { registerResponse ->
            binding.registerButtonLogin.toggleVisibility(true)
            binding.registerCircularLoading.toggleVisibility(false)
            when (registerResponse) {
                is ApiResponse.Failure -> {
                    Toast.makeText(
                        context,
                        "Error! ${registerResponse.errorMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                ApiResponse.Loading -> {
                    binding.registerButtonLogin.toggleVisibility(false)
                    binding.registerCircularLoading.toggleVisibility(true)
                }

                is ApiResponse.Success -> {
                    Toast.makeText(context, "Registered succesfully!", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.fromRegisterScreenFragmentToLoginScreenFragment)
                }
            }
        }
    }
    fun isValidPassword(password: String): Boolean {
        val requirements = listOf(String::isLongEnough, String::hasEnoughDigits, String::isMixedCase)
        return requirements.all { check -> check(password) }
    }
}

private fun FragmentRegisterScreenBinding.validateButton(inputValidator: TextWatcher, viewModel: AuthViewModel) {
    registerInputEmail.addTextChangedListener(inputValidator)
    registerInputUsername.addTextChangedListener(inputValidator)
    registerInputFullname.addTextChangedListener(inputValidator)
    registerInputPassword.addTextChangedListener(inputValidator)
    registerInputVerifyPassword.addTextChangedListener(inputValidator)
    registerInputVerifyEmail.addTextChangedListener(inputValidator)

    registerButtonLogin.setOnClickListener {
        viewModel.register(
            RegisterUserRequestDTO(
                registerInputEmail.text.toString().trim(),
                registerInputFullname.text.toString().trim(),
                registerInputPassword.text.toString().trim(),
                registerInputUsername.text.toString().trim(),
                registerInputVerifyEmail.text.toString().trim(),
                registerInputVerifyPassword.text.toString().trim(),
            ),
            object : CoroutineErrorHandler {
                override fun onError(message: String) {
                    Toast.makeText(
                        registerButtonLogin.context,
                        "Error! $message",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )
    }
}
