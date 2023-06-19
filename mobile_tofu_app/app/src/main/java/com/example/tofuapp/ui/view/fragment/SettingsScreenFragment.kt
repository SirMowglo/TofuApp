package com.example.tofuapp.ui.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.tofuapp.R
import com.example.tofuapp.databinding.FragmentSettingsScreenBinding
import com.example.tofuapp.ui.viewmodel.TokenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsScreenFragment : Fragment() {
    private val binding by lazy { FragmentSettingsScreenBinding.inflate(layoutInflater)}
    private val tokenViewModel: TokenViewModel by activityViewModels()

    private var token: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tokenViewModel.token.observe(viewLifecycleOwner){token ->
            this.token = token
            if(token == null){
                findNavController().navigate(R.id.fromSettingsScreenFragmentToLoginScreenFragment)
            }
        }

        binding.settingsButtonLogout.setOnClickListener {
            tokenViewModel.deleteToken()
        }

    }
}