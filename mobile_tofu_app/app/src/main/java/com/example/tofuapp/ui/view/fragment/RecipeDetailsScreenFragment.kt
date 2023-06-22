package com.example.tofuapp.ui.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.tofuapp.R
import com.example.tofuapp.databinding.FragmentRecipeDetailsScreenBinding
import com.example.tofuapp.databinding.FragmentUserScreenBinding
import com.example.tofuapp.ui.view.adapter.RecipeIngredientRecyclerAdapter
import com.example.tofuapp.ui.view.adapter.RecipeStepRecyclerAdapter
import com.example.tofuapp.ui.viewmodel.CoroutineErrorHandler
import com.example.tofuapp.ui.viewmodel.RecipeDetailsViewModel
import com.example.tofuapp.ui.viewmodel.TokenViewModel
import com.example.tofuapp.util.API_BASE_URL
import com.example.tofuapp.util.ApiResponse
import com.example.tofuapp.util.FIRST_POSITION
import com.example.tofuapp.util.renderUrl
import com.example.tofuapp.util.toggleVisibility
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeDetailsScreenFragment : Fragment() {
    companion object {
        const val RECIPE_ID_KEY = "recipeId"
    }

    private val binding by lazy { FragmentRecipeDetailsScreenBinding.inflate(layoutInflater) }
    private val tokenViewModel: TokenViewModel by activityViewModels()
    private val viewModel: RecipeDetailsViewModel by viewModels()
    private val ingredientsRecyclerAdapter by lazy { RecipeIngredientRecyclerAdapter(token) }
    private val stepsRecyclerAdapter by lazy { RecipeStepRecyclerAdapter() }
    private val recipeId by lazy { arguments?.getString(RECIPE_ID_KEY) }

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

        viewModel.observerViewModel()

        binding.apply {
            setCollapsingToolbar()
            recipeDetailsToolbar.setNavigationOnClickListener {
                findNavController().navigate(R.id.fromRecipeDetailsScreenFragmentToUserScreenFragment)
            }
        }
    }

    private fun FragmentRecipeDetailsScreenBinding.setCollapsingToolbar() {
        var isShowing = true
        recipeDetailsAppBarLayout.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            val scrollRange = appBarLayout.totalScrollRange
            if (scrollRange + verticalOffset == 0) {
                profileCollapsingToolbar.title = viewModel.recipe.value?.name
                isShowing = true
            } else if (isShowing) {
                profileCollapsingToolbar.title = ""
                isShowing = false
            }
        }
    }

    private fun RecipeDetailsViewModel.observerViewModel() {

        recipeResponse.observe(viewLifecycleOwner) { recipeResponse ->
            binding.recipeDetailsImageCircularLoading.toggleVisibility(false)
            when (recipeResponse) {
                is ApiResponse.Failure -> {
                    Toast.makeText(
                        context,
                        "Error! ${recipeResponse.errorMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                ApiResponse.Loading -> {
                    binding.recipeDetailsImageCircularLoading.toggleVisibility(true)
                }

                is ApiResponse.Success -> {
                    saveRecipe(recipeResponse.data)
                }
            }
        }

        stepResponse.observe(viewLifecycleOwner) { stepResponse ->
            when (stepResponse) {
                is ApiResponse.Failure -> {
                    Toast.makeText(
                        context,
                        "Error! ${stepResponse.errorMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                ApiResponse.Loading -> {}
                is ApiResponse.Success -> {
                    saveSteps(stepResponse.data)
                }
            }
        }

        ingredientResponse.observe(viewLifecycleOwner){ ingredientResponse ->
            when(ingredientResponse){
                is ApiResponse.Failure -> {
                    Toast.makeText(
                        context,
                        "Error! ${ingredientResponse.errorMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                ApiResponse.Loading -> {}
                is ApiResponse.Success -> {
                    saveIngredients(ingredientResponse.data.content)
                }
            }
        }
        recipe.observe(viewLifecycleOwner) { recipe ->
            with(binding) {
                val imageUrl = "${API_BASE_URL}download/${recipe.img}"
                val glideUrl = GlideUrl(
                    imageUrl,
                    LazyHeaders.Builder()
                        .addHeader("Authorization", "Bearer $token")
                        .build()
                )

                recipeDetailsImage.renderUrl(glideUrl)
                recipeDetailsAuthorUsername.text = "@${recipe.author.username}"
                recipeDetailsName.text = recipe.name
                recipeLabelType.text = recipe.type.replaceFirstChar(Char::uppercase)
                recipeLabelPrepTime.text = "${recipe.prepTime} m"
                recipeDetailsDescription.text = recipe.description

                getStepsByRecipe(recipeId ?: "", object : CoroutineErrorHandler {
                    override fun onError(message: String) {
                        Toast.makeText(context, "Error! $message", Toast.LENGTH_SHORT).show()
                    }
                })

                getIngredientsByRecipe(recipeId ?: "", object : CoroutineErrorHandler {
                    override fun onError(message: String) {
                        Toast.makeText(context, "Error! $message", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }

        stepList.observe(viewLifecycleOwner) { stepList ->
            binding.recipeDetailsStepsRecyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = stepsRecyclerAdapter
            }
            stepsRecyclerAdapter.submitList(stepList) {
                binding.recipeDetailsStepsRecyclerView.scrollToPosition(FIRST_POSITION)
            }
        }

        ingredientList.observe(viewLifecycleOwner) { ingredientList ->
            binding.recipeDetailsIngredientsRecyclerView.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = ingredientsRecyclerAdapter
            }
            ingredientsRecyclerAdapter.submitList(ingredientList) {
                binding.recipeDetailsIngredientsRecyclerView.scrollToPosition(FIRST_POSITION)
            }
        }

        getRecipeDetails(recipeId ?: "", object : CoroutineErrorHandler {
            override fun onError(message: String) {
                Toast.makeText(context, "Error! $message", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
