package com.elevii.comidanamedida.ui.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.elevii.comidanamedida.databinding.FragmentSettingsBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import androidx.core.content.edit


class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val savedMode = loadThemeMode()
        loadTextModeTheme(getTextModeTheme(savedMode))

        binding.llChoiseThemeColor.setOnClickListener {
            showDialogChoiseTheme()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showDialogChoiseTheme() {
        val themes = arrayOf(MODE_NIGHT_NO_LABEL, MODE_NIGHT_YES_LABEL, MODE_SYSTEM_LABEL)
        val currentMode = AppCompatDelegate.getDefaultNightMode()

        val checkedItem = when (currentMode) {
            AppCompatDelegate.MODE_NIGHT_NO -> INDEX_CLARO
            AppCompatDelegate.MODE_NIGHT_YES -> INDEX_ESCURO
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> INDEX_SISTEMA
            else -> INDEX_SISTEMA
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Escolha o tema")
            .setSingleChoiceItems(themes, checkedItem) { dialog, which ->
                val selectedMode = when (which) {
                    INDEX_CLARO -> AppCompatDelegate.MODE_NIGHT_NO
                    INDEX_ESCURO -> AppCompatDelegate.MODE_NIGHT_YES
                    INDEX_SISTEMA -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                    else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                }
                saveThemeMode(selectedMode)
                AppCompatDelegate.setDefaultNightMode(selectedMode)
                loadTextModeTheme(getTextModeTheme(selectedMode))

                dialog.dismiss()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun saveThemeMode(mode: Int) {
        val prefs = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit() { putInt(KEY_THEME_MODE, mode) }
    }

    private fun loadThemeMode(): Int {
        val prefs = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getInt(KEY_THEME_MODE, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }

    private fun getTextModeTheme(mode: Int): String {
        return when (mode) {
            AppCompatDelegate.MODE_NIGHT_NO -> MODE_NIGHT_NO_LABEL
            AppCompatDelegate.MODE_NIGHT_YES -> MODE_NIGHT_YES_LABEL
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> MODE_SYSTEM_LABEL
            else -> MODE_SYSTEM_LABEL
        }
    }

    private fun loadTextModeTheme(textMode: String) {
        binding.tvTitleTheme.text = textMode
    }

    companion object {
        const val PREFS_NAME = "app_prefs"
        const val KEY_THEME_MODE = "theme_mode"
        const val MODE_NIGHT_NO_LABEL = "Claro"
        const val MODE_NIGHT_YES_LABEL = "Escuro"
        const val MODE_SYSTEM_LABEL = "Cor do sistema"

        const val INDEX_CLARO = 0
        const val INDEX_ESCURO = 1
        const val INDEX_SISTEMA = 2
    }
}
