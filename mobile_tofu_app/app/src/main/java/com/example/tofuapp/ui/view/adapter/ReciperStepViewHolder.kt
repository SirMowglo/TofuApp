package com.example.tofuapp.ui.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.tofuapp.data.model.dto.step.StepResponseDTO
import com.example.tofuapp.databinding.RowStepsCardBinding

class ReciperStepViewHolder(itemView: View): ViewHolder(itemView) {
    private val binding = RowStepsCardBinding.bind(itemView)

    fun bind(step: StepResponseDTO){
        with(binding){
            rowStepDescription.text = step.description
            rowStepNum.text = "Step: ${step.stepNumber}"
        }
    }
}