package com.example.tofuapp.ui.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.tofuapp.data.model.dto.ingredient.IngredientResponseDTO
import com.example.tofuapp.databinding.RowIngredientCardBinding
import com.example.tofuapp.util.API_BASE_URL
import com.example.tofuapp.util.renderUrl

class RecipeIngredientViewHolder(itemView: View): ViewHolder(itemView) {
    private val binding = RowIngredientCardBinding.bind(itemView)

    fun bind(ingredient: IngredientResponseDTO, token: String?){
        with(binding){
            rowIngredientName.text = ingredient.name

            val imgUrl = "${API_BASE_URL}download/${ingredient.img}"
            val glideUrl = GlideUrl(
                imgUrl,
                LazyHeaders.Builder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
            )
            rowIngredientImage.renderUrl(glideUrl)
        }
    }
}