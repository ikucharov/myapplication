package com.oauth.app.model

import com.google.gson.annotations.SerializedName

data class Auth(
        @SerializedName("access_token")
        val access_token: String?,

        @SerializedName("refresh_token")
        val refresh_token: String?,

        @SerializedName("token_type")
        val token_type: String?,

        @SerializedName("expires_in")
        val expires_in: Long?,

        @SerializedName("scope")
        val scope: String?)
