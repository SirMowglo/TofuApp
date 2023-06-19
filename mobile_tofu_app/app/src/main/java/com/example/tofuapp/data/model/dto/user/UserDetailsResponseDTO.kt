package com.example.tofuapp.data.model.dto.user

import com.google.gson.annotations.SerializedName

data class UserDetailsResponseDTO(
    @SerializedName("id")
    val id: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("fullname")
    val fullname: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("avatar")
    val avatar: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("birthday")
    val birthday: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("nFollowers")
    val nFollowers: Int,
    @SerializedName("nFollowing")
    val nFollowing: Int,
)