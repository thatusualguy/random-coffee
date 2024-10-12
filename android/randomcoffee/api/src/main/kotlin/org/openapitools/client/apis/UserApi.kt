package org.openapitools.client.apis

import org.openapitools.client.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Call
import okhttp3.RequestBody
import com.squareup.moshi.Json

import org.openapitools.client.models.User
import org.openapitools.client.models.UserRegister

interface UserApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: Success
     *
     * @param userId 
     * @return [Call]<[User]>
     */
    @GET("user/{userId}")
    fun userUserIdGet(@Path("userId") userId: kotlin.String): Call<User>

    /**
     * 
     * 
     * Responses:
     *  - 200: Successful change
     *
     * @param userId 
     * @param userRegister  (optional)
     * @return [Call]<[User]>
     */
    @PATCH("user/{userId}")
    fun userUserIdPatch(@Path("userId") userId: kotlin.String, @Body userRegister: UserRegister? = null): Call<User>

}
