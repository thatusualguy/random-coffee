package org.openapitools.client.apis

import org.openapitools.client.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Call
import okhttp3.RequestBody
import com.squareup.moshi.Json

import org.openapitools.client.models.FutureMeets
import org.openapitools.client.models.HistoryMeet

interface MeetApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: Success
     *
     * @param userId 
     * @param meetId 
     * @return [Call]<[Unit]>
     */
    @POST("meet/approve/{userId}/{meetId}")
    fun meetApproveUserIdMeetIdPost(@Path("userId") userId: kotlin.Int, @Path("meetId") meetId: kotlin.Int): Call<Unit>

    /**
     * 
     * 
     * Responses:
     *  - 200: Success
     *
     * @param userId 
     * @return [Call]<[FutureMeets]>
     */
    @GET("meet/future/{userId}")
    fun meetFutureUserIdGet(@Path("userId") userId: kotlin.Int): Call<FutureMeets>

    /**
     * 
     * 
     * Responses:
     *  - 200: Success
     *
     * @param userId 
     * @param startDate  (optional)
     * @param endDate  (optional)
     * @return [Call]<[kotlin.collections.List<HistoryMeet>]>
     */
    @GET("meet/history/{userId}")
    fun meetHistoryUserIdGet(@Path("userId") userId: kotlin.String, @Query("start_date") startDate: java.time.LocalDate? = null, @Query("end_date") endDate: java.time.LocalDate? = null): Call<kotlin.collections.List<HistoryMeet>>

}
