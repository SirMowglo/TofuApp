package com.example.tofuapp.ui.view.adapter

import android.content.res.ColorStateList
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.tofuapp.R
import com.example.tofuapp.data.model.dto.recipe.RecipeResponseDTO
import com.example.tofuapp.databinding.RowRecipeItemBinding
import com.example.tofuapp.ui.viewmodel.TokenViewModel
import com.example.tofuapp.util.API_BASE_URL
import com.example.tofuapp.util.renderUrl

class UserRecipesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = RowRecipeItemBinding.bind(itemView)


    fun bind(
        recipe: RecipeResponseDTO,
        onItemClick: UserRecipesRecyclerAdapter.OnItemClickListener,
        token: String?
    ) {
        with(binding) {
            val imgUrl = "${API_BASE_URL}download/${recipe.img}"
            val glideUrl = GlideUrl(
                imgUrl,
                LazyHeaders.Builder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
            )
            rowRecipeImage.renderUrl(glideUrl)

            rowRecipePrepTime.text ="${recipe.prepTime} m"
            rowRecipeNLikes.text = " ${recipe.nlikes}"
            rowRecipeName.text = recipe.name
            rowRecipeAuthorUsername.text = "@${recipe.author.username}"
            if (recipe.nlikes > 0) {
                changeFavoriteIcon(R.drawable.ic_favorite, R.color.tofu_accent_red_light)
            }else{
                changeFavoriteIcon(R.drawable.ic_favorite_border, R.color.tofu_generic_darker)
            }

            rowRecipeCard.setOnClickListener {
                onItemClick.onItemClick(recipe)
            }
            rowRecipeCard.setOnLongClickListener{
                onItemClick.onLongItemClick(recipe)
            }
        }
    }

    private fun RowRecipeItemBinding.changeFavoriteIcon(icon: Int, color: Int){
        rowRecipeFavoriteIcon.setImageResource(icon)
        rowRecipeFavoriteIcon.imageTintList = ColorStateList.valueOf(
            ContextCompat.getColor(
                rowRecipeFavoriteIcon.context,
                color
            )
        )
    }
}