package com.example.tofuapp.ui.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tofuapp.R
import com.example.tofuapp.databinding.FragmentLauncherBinding
import com.example.tofuapp.ui.viewmodel.LauncherViewModel

class LauncherFragment : Fragment() {
    private val binding by lazy { FragmentLauncherBinding.inflate(layoutInflater) }
    private val launcherViewModel: LauncherViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launcherViewModel.getNavigateState().observe(viewLifecycleOwner){isNavigate ->
            if(isNavigate)  findNavController().navigate(R.id.fromLauncherFragmentToLoginScreenFragment)
        }
        launcherViewModel.startTimer()

    }
}