package com.sirmow.tofuapp.ui.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sirmow.tofuapp.R
import com.sirmow.tofuapp.databinding.FragmentLauncherBinding

class LauncherFragment : Fragment() {
    private val binding by lazy { FragmentLauncherBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

}
