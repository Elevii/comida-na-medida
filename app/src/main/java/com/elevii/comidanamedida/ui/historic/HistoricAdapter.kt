package com.elevii.comidanamedida.ui.historic

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elevii.comidanamedida.R
import com.elevii.comidanamedida.databinding.ItemHistoricLayoutBinding
import com.elevii.comidanamedida.domain.model.CookedFoodMeasurement
import com.elevii.comidanamedida.domain.model.Food

class HistoricAdapter(
    private val itemsHistoric: List<CookedFoodMeasurement>,
    private val foods: List<Food>,
    private val context: Context,
    private val onItemClick: (CookedFoodMeasurement) -> Unit
) : RecyclerView.Adapter<HistoricAdapter.HistoricViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoricViewHolder {
        val binding = ItemHistoricLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HistoricViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemsHistoric.size
    }

    override fun onBindViewHolder(holder: HistoricViewHolder, position: Int) {
        holder.bind(itemsHistoric[position])
    }

    inner class HistoricViewHolder(private val binding: ItemHistoricLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CookedFoodMeasurement) {
            binding.tvFood.text = getFoodName(item.uuidFood)
            binding.tvValues.text = context.getString(
                R.string.item_historic,
                item.weightRaw,
                item.weightCooked
            )

            binding.ivDelete.setOnClickListener {
                onItemClick(item)
            }
        }

        private fun getFoodName(foodUuid: String): String {
            val food = foods.find { it.uuid == foodUuid }
            return food?.name ?: ""
        }
    }
}
