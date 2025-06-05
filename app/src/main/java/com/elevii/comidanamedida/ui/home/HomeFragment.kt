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
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.elevii.comidanamedida.R
import com.elevii.comidanamedida.databinding.DialogAlertErrorBinding
import com.elevii.comidanamedida.databinding.DialogResultBinding
import com.elevii.comidanamedida.databinding.FragmentMenuBinding
import com.elevii.comidanamedida.domain.model.CookedFoodMeasurement
import com.elevii.comidanamedida.domain.model.Food
import com.elevii.comidanamedida.ui.home.events.SaveMeasurementEvent
import com.elevii.comidanamedida.util.Resource
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()
    private var foodList: List<Food> = emptyList()
    private var selectedFood: Food? = null

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

        loadQuantityDays()
        observeFoods()
        initializeListeners()
        observeResultCalculateFood()
        observeSaveMeasurementError()
        observeQuantityDays()
    }

    private fun loadQuantityDays() {
        binding.etDayQuantity.editText?.setText(DEFAULT_DAY_QUANTITY.toString())
    }

    private fun initializeListeners() {
        binding.slFoodType.setOnItemClickListener { _, _, i, _ ->
            clearEnteredData()
            selectedFood = foodList[i]
        }

        binding.btCalculate.setOnClickListener {
            validateResult()
            closeKeyboard()
        }

        binding.buttonIncrement.setOnClickListener {
            binding.etDayQuantity.clearFocus()
            viewModel.increase()
        }

        binding.buttonDecrement.setOnClickListener {
            binding.etDayQuantity.clearFocus()
            viewModel.decrement()
        }

        binding.teDayQuantity.doAfterTextChanged { text ->
            val valor = text?.toString()?.takeIf { it.isNotBlank() }?.toIntOrNull()
            if (valor != null) {
                viewModel.setQuantityManual(valor)
            }
        }
    }

    private fun saveMeasurement() {
        viewModel.saveMeasurement()
    }

    private fun observeResultCalculateFood() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.measurement.collectLatest { measurement ->
                    measurement?.let { showResult(it) }
                }
            }
        }
    }

    private fun observeFoods() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.foods.collect { resource ->
                    when (resource) {
                        is Resource.Loading -> setLoading(true)
                        is Resource.Success -> {
                            setLoading(false)
                            foodList = resource.data ?: emptyList()
                            loadDropdownFoods(foodList)
                        }

                        is Resource.Error -> {
                            setLoading(false)
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
                            clearSelectedFood()
                        }

                        is SaveMeasurementEvent.Error -> showError("Erro: ${event.message}")
                    }
                }
            }
        }
    }

    private fun observeQuantityDays() {
        viewModel.quantity.observe(viewLifecycleOwner) { value ->
            val currentText = binding.etDayQuantity.editText?.text.toString()
            if (currentText != value.toString()) {
                binding.etDayQuantity.editText?.setText(value.toString())
            }
        }
    }

    private fun showToast() {
        Toast.makeText(context, "Salvo com sucesso!", Toast.LENGTH_SHORT).show()
    }

    private fun closeKeyboard() {
        binding.tlWeightCookedFood.clearFocus()
        val imm = requireContext().getSystemService(
            Context.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun clearSelectedFood() {
        binding.slFoodType.text.clear()
        clearEnteredData()
    }

    private fun clearEnteredData() {
        binding.tlWeightCookedFood.editText?.text?.clear()
        loadQuantityDays()
        selectedFood = null
        viewModel.clearMeasurement()
    }

    private fun setLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.dropdownMenu.isEnabled = !isLoading
        binding.tlWeightCookedFood.isEnabled = !isLoading
        binding.btCalculate.isEnabled = !isLoading
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

    private fun isValidInput(): Boolean {
        val cookedText =
            binding.tlWeightCookedFood.editText?.text?.toString() ?: ""
        val cooked = cookedText.toDoubleOrNull()
        val food = selectedFood
        val isValid: Boolean

        if (food == null) {
            binding.tlWeightCookedFood.error = null
            binding.dropdownMenu.error = getString(R.string.error_select_food)
            isValid = false
        } else if (cooked == null) {
            binding.dropdownMenu.error = null
            binding.tlWeightCookedFood.error = getString(R.string.error_invalid_weight)
            isValid = false
        } else {
            binding.dropdownMenu.error = null
            binding.tlWeightCookedFood.error = null
            isValid = true
        }

        return isValid
    }

    private fun validateResult() {
        val cookedText =
            binding.tlWeightCookedFood.editText?.text?.toString() ?: ""
        val cooked = cookedText.toDoubleOrNull()
        val food = selectedFood

        var quantity = binding.etDayQuantity.editText?.text?.toString()

        if (quantity.isNullOrEmpty()) {
            quantity = DEFAULT_DAY_QUANTITY.toString()
        }

        if (isValidInput()) {
            viewModel.calculateMeasurement(cooked!!, quantity.toInt(), food!!)
        }
    }

    private fun showResult(measurement: CookedFoodMeasurement) {
        val measuremntDays: Double = if (measurement.quantityDays > 0) {
            measurement.weightRaw * measurement.quantityDays
        } else {
            measurement.weightRaw
        }

        showDialogResult(measurement, measuremntDays)
    }

    private fun showDialogResult(measurement: CookedFoodMeasurement, measuremntDays: Double) {
        val dialogBinding = DialogResultBinding.inflate(LayoutInflater.from(requireContext()))

        if (measurement.quantityDays > 0) {
            dialogBinding.tvDaysResult.text = getString(
                R.string.result_days_format,
                measurement.weightRaw,
                measurement.quantityDays
            )

            dialogBinding.tvDaysResult.visibility = View.VISIBLE
        } else {
            dialogBinding.tvDaysResult.visibility = View.GONE
        }

        dialogBinding.tvResultText.text = getString(
            R.string.result_format,
            measuremntDays,
            selectedFood?.name.orEmpty()
        )

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setView(dialogBinding.root)
            .setCancelable(false)
            .create()

        dialogBinding.btSaveResult.setOnClickListener {
            saveMeasurement()
            dialog.dismiss()
        }

        dialogBinding.btClearResult.setOnClickListener {
            clearSelectedFood()
            dialog.dismiss()
        }
        dialog.show()
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

    companion object {
        private const val DEFAULT_DAY_QUANTITY = 0
    }
}
