package com.capstone.allergysavvy.ui.main.fragment.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.capstone.allergysavvy.R
import com.capstone.allergysavvy.data.local.pref.SettingPreference
import com.capstone.allergysavvy.data.local.pref.dataStore
import com.capstone.allergysavvy.databinding.FragmentProfileBinding
import com.capstone.allergysavvy.ui.setting.SettingViewModel
import com.capstone.allergysavvy.ui.setting.SettingViewModelFactory
import com.capstone.allergysavvy.ui.welcome.WelcomeActivity

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val settingViewModel: SettingViewModel by viewModels {
        SettingViewModelFactory(SettingPreference.getInstance(requireContext().dataStore))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupActionTheme()
        setupLogout()
    }

    private fun setupActionTheme() {
        binding.btnEditThemeFragmentProfile.setOnClickListener {
            showSettingThemeDialog()
        }
    }

    private fun setupLogout() {
        binding.btnLogoutFragmentProfile.setOnClickListener {
            dialogLogoutConfirmation()
        }
    }

    private fun dialogLogoutConfirmation() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Logout")
            setMessage("Are you sure want to logout?")
            setPositiveButton("Yes") { _, _ ->
                val intent = Intent(requireContext(), WelcomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                activity?.finish()
            }
            setNegativeButton("No", null)
            create()
            show()
        }
    }

    private fun showSettingThemeDialog() {
        val dialogViewTheme = layoutInflater.inflate(R.layout.dialog_theme_layout, null)
        val radioGroupTheme = dialogViewTheme.findViewById<RadioGroup>(R.id.rg_select_theme)

        settingViewModel.getThemeSetting().observe(viewLifecycleOwner) { isDarkModeActive ->
            when {
                isDarkModeActive -> radioGroupTheme.check(R.id.rb_dark_mode)
                else -> radioGroupTheme.check(R.id.rb_light_mode)
            }
        }

        AlertDialog.Builder(requireContext())
            .setView(dialogViewTheme)
            .setPositiveButton("Confirm") { _, _ ->
                when (radioGroupTheme.checkedRadioButtonId) {
                    R.id.rb_light_mode -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        settingViewModel.saveThemeSetting(false)
                    }

                    R.id.rb_dark_mode -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        settingViewModel.saveThemeSetting(true)
                    }
                }
                activity?.recreate()

            }

            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}