package com.example.tofuapp.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.tofuapp.R
import com.example.tofuapp.data.model.dto.recipe.RecipeResponseDTO

class UserRecipesRecyclerAdapter(
    private val onItemClick: OnItemClickListener,
    val token:String?
) :
    ListAdapter<RecipeResponseDTO, UserRecipesViewHolder>(UserRecipesDiffCallback()) {

    interface OnItemClickListener {
        fun onItemClick(recipe: RecipeResponseDTO) {}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserRecipesViewHolder =
        UserRecipesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_recipe_item, parent, false)
        )

    override fun onBindViewHolder(holder: UserRecipesViewHolder, position: Int) {
        val recipe = getItem(position)
        holder.bind(recipe, onItemClick, token)
    }
}

class UserRecipesDiffCallback : DiffUtil.ItemCallback<RecipeResponseDTO>() {
    override fun areItemsTheSame(oldItem: RecipeResponseDTO, newItem: RecipeResponseDTO): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: RecipeResponseDTO,
        newItem: RecipeResponseDTO
    ): Boolean = oldItem == newItem

}
