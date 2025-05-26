package com.elevii.comidanamedida.ui.historic

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.elevii.comidanamedida.databinding.DialogAlertErrorBinding
import com.elevii.comidanamedida.databinding.FragmentHistoricBinding
import com.elevii.comidanamedida.domain.model.CookedFoodMeasurement
import com.elevii.comidanamedida.domain.model.Food
import com.elevii.comidanamedida.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HistoricFragment : Fragment() {

    private var _binding: FragmentHistoricBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HistoricViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoricBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareUi()
        observeMeasurementsAndFoods()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun prepareUi() {
        binding.rvHistoric.layoutManager = LinearLayoutManager(context)
    }

    private fun observeMeasurementsAndFoods() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                combine(
                    viewModel.cookedFoodMeasurement,
                    viewModel.foods
                ) { measurementsResource, foodsResource ->
                    Pair(measurementsResource, foodsResource)
                }.collect { (measurements, foods) ->
                    when {
                        measurements is Resource.Loading || foods is Resource.Loading -> {
                            showLoading()
                        }

                        measurements is Resource.Error -> {
                            hideLoading()
                            showError(measurements.message ?: "Erro nas medições")
                        }

                        foods is Resource.Error -> {
                            hideLoading()
                            showError(foods.message ?: "Erro nos alimentos")
                        }

                        measurements is Resource.Success && foods is Resource.Success -> {
                            hideLoading()
                            val measurements = measurements.data ?: emptyList()
                            val foods = foods.data ?: emptyList()
                            loadHistoric(measurements, foods)
                        }
                    }
                }
            }
        }
    }

    private fun loadHistoric(measurementList: List<CookedFoodMeasurement>?, foods: List<Food>) {
        val adapter = HistoricAdapter(measurementList ?: emptyList(), foods, requireContext())
        binding.rvHistoric.adapter = adapter
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
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
}
