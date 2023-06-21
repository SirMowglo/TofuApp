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
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.tofuapp.R
import com.example.tofuapp.data.model.dto.recipe.RecipeResponseDTO
import com.example.tofuapp.databinding.FragmentUserScreenBinding
import com.example.tofuapp.ui.view.adapter.UserRecipesRecyclerAdapter
import com.example.tofuapp.ui.viewmodel.CoroutineErrorHandler
import com.example.tofuapp.ui.viewmodel.CurrentUserViewModel
import com.example.tofuapp.ui.viewmodel.TokenViewModel
import com.example.tofuapp.util.API_BASE_URL
import com.example.tofuapp.util.ApiResponse
import com.example.tofuapp.util.FIRST_POSITION
import com.example.tofuapp.util.renderUrl
import com.example.tofuapp.util.toggleVisibility
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class UserScreenFragment : Fragment() {
    private val binding by lazy { FragmentUserScreenBinding.inflate(layoutInflater) }
    private val viewModel: CurrentUserViewModel by viewModels()
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

        binding.apply {
            setCollapsingToolbar()

            userFABAddRecipe.setOnClickListener {
                findNavController().navigate(R.id.fromUserScreenFragmentToAddRecipeScreenFragment)
            }
        }
    }

    private fun CurrentUserViewModel.observeViewModel() {

        userResponse.observe(viewLifecycleOwner) { userResponse ->
            binding.profileAvatarCircularLoading.toggleVisibility(false)
            when (userResponse) {
                is ApiResponse.Failure -> {
                    Toast.makeText(
                        context,
                        "Error! ${userResponse.errorMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                ApiResponse.Loading -> {
                    binding.profileAvatarCircularLoading.toggleVisibility(true)
                }
                is ApiResponse.Success -> {
                    saveUser(userResponse.data)
                }
            }
        }

        recipeResponse.observe(viewLifecycleOwner) { recipeResponse ->
            binding.userRecipesCircularLoading.toggleVisibility(false)
            when (recipeResponse) {
                is ApiResponse.Failure -> {
                    Toast.makeText(
                        context,
                        "Error! ${recipeResponse.errorMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                ApiResponse.Loading -> {
                    binding.userRecipesCircularLoading.toggleVisibility(true)
                }
                is ApiResponse.Success -> {
                    saveRecipes(recipeResponse.data.content)
                }
            }
        }

        user.observe(viewLifecycleOwner) { user ->
            with(binding) {
                val avatarUrl = "${API_BASE_URL}download/${user.avatar}"
                val glideUrl = GlideUrl(
                    avatarUrl,
                    LazyHeaders.Builder()
                        .addHeader("Authorization", "Bearer $token")
                        .build()
                )

                profileLabelUsername.text = "@${user.username}"
                profileLabelFullname.text = user.fullname
                profileLabelDescription.text = user.description
                profileImageAvatar.renderUrl(glideUrl)
            }

            getRecipesUser(object : CoroutineErrorHandler {
                override fun onError(message: String) {
                    Toast.makeText(context, "Error! $message", Toast.LENGTH_SHORT).show()
                }
            })
        }

        recipeList.observe(viewLifecycleOwner) { recipeList ->
            binding.userScreenRecipeRecyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = recipesRecylerAdapter
            }
            recipesRecylerAdapter.submitList(recipeList) {
                binding.userScreenRecipeRecyclerView.scrollToPosition(FIRST_POSITION)
            }
        }

        getCurrentUser(object : CoroutineErrorHandler {
            override fun onError(message: String) {
                binding.profileLabelDescription.text = "Error! $message"
            }
        })
    }

    private fun FragmentUserScreenBinding.setCollapsingToolbar() {
        var isShowing = true
        profileAppBarLayout.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            val scrollRange = appBarLayout.totalScrollRange
            if (scrollRange + verticalOffset == 0) {
                profileCollapsingToolbar.title = viewModel.user.value?.fullname
                isShowing = true
            } else if (isShowing) {
                profileCollapsingToolbar.title = ""
                isShowing = false
            }
        }
    }

}