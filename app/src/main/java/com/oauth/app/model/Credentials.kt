package com.oauth.app.model

data class Credentials(
        val name: String?,
        val id: Long?,
        val email: String?,
        val roles: List<String>?
)