package com.elevii.comidanamedida.ui.home

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.elevii.comidanamedida.R
import com.elevii.comidanamedida.databinding.DialogAlertErrorBinding
import com.elevii.comidanamedida.databinding.FragmentMenuBinding
import com.elevii.comidanamedida.domain.model.CookedFoodMeasurement
import com.elevii.comidanamedida.domain.model.Food
import com.elevii.comidanamedida.ui.home.events.SaveMeasurementEvent
import com.elevii.comidanamedida.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()
    private var foodList: List<Food> = emptyList()
    private var selectedFood: Food? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeFoods()
        initializeListeners()
        observeResultCalculateFood()
        observeSaveMeasurementError()
    }

    private fun observeResultCalculateFood() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.measurement.collect { measurement ->
                    measurement?.let { showResult(it) }
                }
            }
        }
    }

    private fun initializeListeners() {
        binding.slFoodType.setOnItemClickListener { adapterView, view, i, l ->
            clearFoodSelected()
            selectedFood = foodList[i]
        }

        binding.btCalculated.setOnClickListener {
            validateResult()
            closeKeyboard()
        }

        binding.btSaveResult.setOnClickListener {
            saveMeasurement()
        }

        binding.btClearResult.setOnClickListener {
            binding.slFoodType.text.clear()
            clearFoodSelected()
        }
    }

    private fun saveMeasurement() {
        viewModel.saveMeasurement()
    }

    private fun closeKeyboard() {
        binding.tlWeightCookedFood.clearFocus()
        val imm = requireContext().getSystemService(
            Context.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun clearFoodSelected() {
        binding.cvResult.visibility = View.GONE
        binding.tlWeightCookedFood.editText?.text?.clear()
        selectedFood = null
        viewModel.clearMeasurement()
    }

    private fun observeFoods() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.foods.collect { resource ->
                    when (resource) {
                        is Resource.Loading -> showLoading()
                        is Resource.Success -> {
                            hideLoading()
                            foodList = resource.data ?: emptyList()
                            loadDropdownFoods(foodList)
                        }
                        is Resource.Error -> {
                            hideLoading()
                            showError(resource.message.toString())
                        }
                    }
                }
            }
        }
    }

    private fun observeSaveMeasurementError() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.saveMeasurementEvent.collect { event ->
                    when (event) {
                        is SaveMeasurementEvent.Success -> {
                            showToast()
                            clearFoodSelected()
                        }
                        is SaveMeasurementEvent.Error -> showError("Erro: ${event.message}")
                    }
                }
            }
        }
    }

    private fun showToast() {
        Toast.makeText(context, "Salvo com sucesso!", Toast.LENGTH_SHORT).show()
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.dropdownMenu.isEnabled = false
        binding.tlWeightCookedFood.isEnabled = false
        binding.btCalculated.isEnabled = false
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
        binding.dropdownMenu.isEnabled = true
        binding.tlWeightCookedFood.isEnabled = true
        binding.btCalculated.isEnabled = true
    }

    private fun loadDropdownFoods(foodsList: List<Food>) {
        val foodNames = foodsList.map { it.name }
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            foodNames
        )
        binding.slFoodType.setAdapter(adapter)
    }

    private fun validateResult() {
        val cookedText =
            binding.tlWeightCookedFood.editText?.text?.toString() ?: ""
        val cooked = cookedText.toDoubleOrNull()
        val food = selectedFood

        if (food == null) {
            binding.tlWeightCookedFood.error = null
            binding.dropdownMenu.error = getString(R.string.error_select_food)
        } else if (cooked == null) {
            binding.dropdownMenu.error = null
            binding.tlWeightCookedFood.error = getString(R.string.error_invalid_weight)
        } else {
            binding.dropdownMenu.error = null
            binding.tlWeightCookedFood.error = null
            viewModel.calculateMeasurement(cooked, food)
        }
    }

    private fun showResult(measurement: CookedFoodMeasurement) {
        binding.tvResultText.text = getString(
            R.string.result_format,
            measurement.weightRaw,
            selectedFood?.name.orEmpty()
        )
        binding.cvResult.visibility = View.VISIBLE
    }

    private fun showError(message: String) {
        val bindingDialogError = DialogAlertErrorBinding.inflate(LayoutInflater.from(context))

        val dialog = AlertDialog.Builder(context)
            .setView(bindingDialogError.root)
            .setCancelable(false)
            .create()

        dialog.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())

        bindingDialogError.tvErrorDescription.text = message
        bindingDialogError.btErrorConfirm.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
