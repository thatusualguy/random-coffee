package org.openapitools.client.apis

import org.openapitools.client.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Call
import okhttp3.RequestBody
import com.squareup.moshi.Json

import org.openapitools.client.models.Interest
import org.openapitools.client.models.InterestGroup

interface InterestsApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: Success
     *
     * @return [Call]<[kotlin.collections.List<InterestGroup>]>
     */
    @GET("interests")
    fun interestsGet(): Call<kotlin.collections.List<InterestGroup>>

    /**
     * 
     * 
     * Responses:
     *  - 200: Success
     *
     * @param userId 
     * @return [Call]<[kotlin.collections.List<Interest>]>
     */
    @GET("interests/{userId}")
    fun interestsUserIdGet(@Path("userId") userId: kotlin.String): Call<kotlin.collections.List<Interest>>

    /**
     * 
     * 
     * Responses:
     *  - 200: Success
     *
     * @param userId 
     * @param interest  (optional)
     * @return [Call]<[kotlin.collections.List<Interest>]>
     */
    @POST("interests/{userId}")
    fun interestsUserIdPost(@Path("userId") userId: kotlin.String, @Body interest: kotlin.collections.List<Interest>? = null): Call<kotlin.collections.List<Interest>>

}
