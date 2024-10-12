package org.openapitools.client.apis

import org.openapitools.client.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Call
import okhttp3.RequestBody
import com.squareup.moshi.Json

import org.openapitools.client.models.ScheduleSlot
import org.openapitools.client.models.UserScheduleChange

interface ScheduleApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: Success
     *
     * @param userId 
     * @param startDate  (optional)
     * @param endDate  (optional)
     * @return [Call]<[kotlin.collections.List<ScheduleSlot>]>
     */
    @GET("schedule/{userId}")
    fun scheduleUserIdGet(@Path("userId") userId: kotlin.String, @Query("start_date") startDate: java.time.LocalDate? = null, @Query("end_date") endDate: java.time.LocalDate? = null): Call<kotlin.collections.List<ScheduleSlot>>

    /**
     * 
     * 
     * Responses:
     *  - 200: Success
     *
     * @param userId 
     * @param startDate  (optional)
     * @param endDate  (optional)
     * @param userScheduleChange  (optional)
     * @return [Call]<[kotlin.collections.List<ScheduleSlot>]>
     */
    @POST("schedule/{userId}")
    fun scheduleUserIdPost(@Path("userId") userId: kotlin.String, @Query("start_date") startDate: java.time.LocalDate? = null, @Query("end_date") endDate: java.time.LocalDate? = null, @Body userScheduleChange: UserScheduleChange? = null): Call<kotlin.collections.List<ScheduleSlot>>

}
