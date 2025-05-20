package com.elevii.comidanamedida.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.elevii.comidanamedida.R
import com.elevii.comidanamedida.databinding.FragmentMenuBinding
import com.elevii.comidanamedida.domain.model.CookedFoodMeasurement
import com.elevii.comidanamedida.domain.model.Food
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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadFoods()
        observeFoods()
        initializeListeners()
        observeResultCalculateFood()
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
            selectedFood = foodList[i]
        }

        binding.btCalculated.setOnClickListener {
            validateResult()
        }
    }

    private fun observeFoods() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.foods.collect { foodsList ->
                    foodList = foodsList
                    loadDropdownFoods(foodsList)
                }
            }
        }
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

    private fun loadFoods() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.refreshFoods()
        }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}