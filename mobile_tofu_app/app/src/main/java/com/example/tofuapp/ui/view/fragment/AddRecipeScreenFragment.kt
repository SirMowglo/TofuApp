package com.example.tofuapp.ui.view.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tofuapp.R
import com.example.tofuapp.data.model.dto.recipe.RecipeRequestDTO
import com.example.tofuapp.databinding.FragmentAddRecipeScreenBinding
import com.example.tofuapp.ui.viewmodel.AddRecipeViewModel
import com.example.tofuapp.ui.viewmodel.CoroutineErrorHandler
import com.example.tofuapp.util.ApiResponse
import com.example.tofuapp.util.toggleVisibility
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddRecipeScreenFragment : Fragment() {
    private val binding by lazy { FragmentAddRecipeScreenBinding.inflate(layoutInflater) }
    private val viewModel: AddRecipeViewModel by viewModels()
    private val inputValidator = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            with(binding) {
                val prepTime: Int =
                    if (addRecipeInputPrepTime.text.toString().isNotEmpty())
                        Integer.parseInt(addRecipeInputPrepTime.text.toString())
                    else 0

                if (prepTime !in 1..999)
                    addRecipeInputLayoutPrepTime.error =
                        "Preparation time must be between 0 and 1000 minutes"
                else addRecipeInputLayoutPrepTime.error = ""

                recipeButtonLogin.isEnabled = (prepTime in 1..999)
            }
        }

        override fun afterTextChanged(s: Editable?) {}

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            validateButton(inputValidator, viewModel)

            addRecipeToolbar.setNavigationOnClickListener {
                findNavController().navigate(R.id.fromAddRecipeScreenFragmentToUserScreenFragment)
            }
        }

        viewModel.recipeResponse.observe(viewLifecycleOwner) { recipeResponse ->
            binding.recipeButtonLogin.toggleVisibility(true)
            binding.recipeCircularLoading.toggleVisibility(false)
            when (recipeResponse) {
                is ApiResponse.Failure -> {
                    Toast.makeText(
                        context,
                        "Error! ${recipeResponse.errorMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                ApiResponse.Loading -> {
                    binding.recipeButtonLogin.toggleVisibility(true)
                    binding.recipeCircularLoading.toggleVisibility(false)
                }

                is ApiResponse.Success -> {
                    Toast.makeText(context, "Added succesfully!", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.fromAddRecipeScreenFragmentToUserScreenFragment)
                }
            }
        }

    }

    private fun FragmentAddRecipeScreenBinding.validateButton(
        inputValidator: TextWatcher,
        viewModel: AddRecipeViewModel
    ) {
        addRecipeInputPrepTime.addTextChangedListener(inputValidator)
        var chipType = "meal"
        addRecipeChipGroupType.setOnCheckedStateChangeListener { cg, _ ->
            if (cg.checkedChipIds.isNotEmpty()) {
                chipType = cg.findViewById<Chip>(cg.checkedChipId).text.toString().lowercase()
            }
        }

        recipeButtonLogin.setOnClickListener {
            viewModel.addRecipe(
                RecipeRequestDTO(
                    addRecipeInputDescription.text.toString(),
                    addRecipeInputName.text.toString(),
                    if (addRecipeInputPrepTime.text.toString().isNotEmpty())
                        Integer.parseInt(addRecipeInputPrepTime.text.toString())
                    else 0,
                    chipType
                ),
                object : CoroutineErrorHandler {
                    override fun onError(message: String) {
                        Toast.makeText(
                            recipeButtonLogin.context,
                            "Error! $message",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            )
        }
    }
}