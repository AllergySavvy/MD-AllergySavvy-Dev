package com.capstone.allergysavvy.ui.main.fragment.home

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.capstone.allergysavvy.R
import com.capstone.allergysavvy.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkDarkMode()
    }

    private fun checkDarkMode() {
        val isDarkModeActive =
            (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
                    == Configuration.UI_MODE_NIGHT_YES)

        if (isDarkModeActive) {
            binding.ivAppIcon.setImageResource(R.drawable.logo_allergysavvy_dark_mode_svg)
        } else {
            binding.ivAppIcon.setImageResource(R.drawable.logo_allergysavvy_svg)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}