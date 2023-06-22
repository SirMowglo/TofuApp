package com.example.tofuapp.ui.view.adapter

import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.tofuapp.R
import com.example.tofuapp.data.model.dto.step.StepResponseDTO

class RecipeStepRecyclerAdapter :
    ListAdapter<StepResponseDTO, ReciperStepViewHolder>(RecipeStepDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReciperStepViewHolder =
        ReciperStepViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_steps_card, parent, false)
        )

    override fun onBindViewHolder(holder: ReciperStepViewHolder, position: Int) {
        val step = getItem(position)
        holder.bind(step)
    }
}

class RecipeStepDiffCallback : DiffUtil.ItemCallback<StepResponseDTO>() {
    override fun areItemsTheSame(oldItem: StepResponseDTO, newItem: StepResponseDTO): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: StepResponseDTO, newItem: StepResponseDTO): Boolean =
        oldItem == newItem

}
