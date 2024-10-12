package org.openapitools.client.apis

import org.openapitools.client.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Call
import okhttp3.RequestBody
import com.squareup.moshi.Json

import org.openapitools.client.models.LoginDetails
import org.openapitools.client.models.UserRegister

interface AuthApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: Successful login
     *
     * @param loginDetails  (optional)
     * @return [Call]<[kotlin.String]>
     */
    @POST("login")
    fun loginPost(@Body loginDetails: LoginDetails? = null): Call<kotlin.String>

    /**
     * 
     * 
     * Responses:
     *  - 200: Successful registration
     *
     * @param userRegister  (optional)
     * @return [Call]<[kotlin.String]>
     */
    @POST("register")
    fun registerPost(@Body userRegister: UserRegister? = null): Call<kotlin.String>

}
