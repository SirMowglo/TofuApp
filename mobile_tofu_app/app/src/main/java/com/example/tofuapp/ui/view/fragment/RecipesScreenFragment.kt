package com.example.tofuapp.ui.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tofuapp.R
import com.example.tofuapp.data.model.dto.recipe.RecipeResponseDTO
import com.example.tofuapp.databinding.FragmentRecipesScreenBinding
import com.example.tofuapp.ui.view.adapter.UserRecipesRecyclerAdapter
import com.example.tofuapp.ui.viewmodel.CoroutineErrorHandler
import com.example.tofuapp.ui.viewmodel.CurrentUserViewModel
import com.example.tofuapp.ui.viewmodel.RecipesViewModel
import com.example.tofuapp.ui.viewmodel.TokenViewModel
import com.example.tofuapp.util.ApiResponse
import com.example.tofuapp.util.FIRST_POSITION
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class RecipesScreenFragment : Fragment() {
    private val binding by lazy { FragmentRecipesScreenBinding.inflate(layoutInflater) }
    private val viewModel: RecipesViewModel by viewModels()
    private val tokenViewModel: TokenViewModel by activityViewModels()
    private val recipesRecylerAdapter by lazy { UserRecipesRecyclerAdapter(recipeClick, token) }

    private val recipeClick by lazy {
        object : UserRecipesRecyclerAdapter.OnItemClickListener {
            override fun onItemClick(recipe: RecipeResponseDTO) {
                super.onItemClick(recipe)
                //TODO Implementar detalles al pulsar
            }
        }
    }
    private var token: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tokenViewModel.token.observe(viewLifecycleOwner) { token ->
            this.token = token
            if (token == null) {
                findNavController().navigate(R.id.fromUserScreenFragmentToLoginScreenFragment)
            }
        }

        viewModel.observeViewModel()
    }

    private fun RecipesViewModel.observeViewModel(){

        recipeResponse.observe(viewLifecycleOwner) { recipeResponse ->
            when (recipeResponse) {
                is ApiResponse.Failure -> {}
                ApiResponse.Loading -> {}
                is ApiResponse.Success -> {
                    saveRecipes(recipeResponse.data.content)
                }
            }
        }

        recipeList.observe(viewLifecycleOwner) { recipeList ->
            binding.recipesRecyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = recipesRecylerAdapter
            }
            recipesRecylerAdapter.submitList(recipeList) {
                binding.recipesRecyclerView.scrollToPosition(FIRST_POSITION)
            }
        }

        getRecipes(object : CoroutineErrorHandler {
            override fun onError(message: String) {
                Toast.makeText(context, "Error! $message", Toast.LENGTH_SHORT).show()
            }
        })
    }
}