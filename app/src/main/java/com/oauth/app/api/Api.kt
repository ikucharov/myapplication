package com.oauth.app.api

import com.oauth.app.model.Auth
import com.oauth.app.model.Credentials
import com.oauth.app.util.C
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {

    @FormUrlEncoded
    @POST("oauth/p/token")
    fun login(
            @Field("login") email: String,
            @Field("password") password: String,
            @Field("client_id") client_id: String = C.CLIENT_ID,
            @Field("client_secret") client_secret: String = C.CLIENT_SECRET,
            @Field("grant_type") grant_type: String = C.GRANT_TYPE
    ): Observable<Auth>

    @FormUrlEncoded
    @POST("oauth/r/token")
    fun refreshToken(
            @Field("refresh_token") refreshToken: String,
            @Field("grant_type") grant_type: String = C.GRANT_TYPE_REFRESH
    ): Observable<Auth>

    @GET("credentials")
    fun getCredentials(): Observable<Credentials>
}
