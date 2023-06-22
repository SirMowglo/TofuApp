package com.example.tofuapp.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.tofuapp.R
import com.example.tofuapp.data.model.dto.ingredient.IngredientResponseDTO

class RecipeIngredientRecyclerAdapter(
    val token: String?
): ListAdapter<IngredientResponseDTO, RecipeIngredientViewHolder>(IngredientDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeIngredientViewHolder =
        RecipeIngredientViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_ingredient_card, parent, false)
        )

    override fun onBindViewHolder(holder: RecipeIngredientViewHolder, position: Int) {
        val ingredient = getItem(position)
        holder.bind(ingredient,token)
    }
}

class IngredientDiffCallback : DiffUtil.ItemCallback<IngredientResponseDTO>(){
    override fun areItemsTheSame(
        oldItem: IngredientResponseDTO,
        newItem: IngredientResponseDTO
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: IngredientResponseDTO,
        newItem: IngredientResponseDTO
    ): Boolean = oldItem == newItem

}
