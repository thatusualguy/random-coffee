# ScheduleApi

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**scheduleUserIdGet**](ScheduleApi.md#scheduleUserIdGet) | **GET** schedule/{userId} |  |
| [**scheduleUserIdPost**](ScheduleApi.md#scheduleUserIdPost) | **POST** schedule/{userId} |  |





### Example
```kotlin
// Import classes:
//import org.openapitools.client.*
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiClient = ApiClient()
apiClient.setBearerToken("TOKEN")
val webService = apiClient.createWebservice(ScheduleApi::class.java)
val userId : kotlin.String = userId_example // kotlin.String | 
val startDate : java.time.LocalDate = 2013-10-20 // java.time.LocalDate | 
val endDate : java.time.LocalDate = 2013-10-20 // java.time.LocalDate | 

val result : kotlin.collections.List<ScheduleSlot> = webService.scheduleUserIdGet(userId, startDate, endDate)
```

### Parameters
| **userId** | **kotlin.String**|  | |
| **startDate** | **java.time.LocalDate**|  | [optional] |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **endDate** | **java.time.LocalDate**|  | [optional] |

### Return type

[**kotlin.collections.List&lt;ScheduleSlot&gt;**](ScheduleSlot.md)

### Authorization


Configure bearerAuth:
    ApiClient().setBearerToken("TOKEN")

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json




### Example
```kotlin
// Import classes:
//import org.openapitools.client.*
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiClient = ApiClient()
apiClient.setBearerToken("TOKEN")
val webService = apiClient.createWebservice(ScheduleApi::class.java)
val userId : kotlin.String = userId_example // kotlin.String | 
val startDate : java.time.LocalDate = 2013-10-20 // java.time.LocalDate | 
val endDate : java.time.LocalDate = 2013-10-20 // java.time.LocalDate | 
val userScheduleChange : UserScheduleChange =  // UserScheduleChange | 

val result : kotlin.collections.List<ScheduleSlot> = webService.scheduleUserIdPost(userId, startDate, endDate, userScheduleChange)
```

### Parameters
| **userId** | **kotlin.String**|  | |
| **startDate** | **java.time.LocalDate**|  | [optional] |
| **endDate** | **java.time.LocalDate**|  | [optional] |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **userScheduleChange** | [**UserScheduleChange**](UserScheduleChange.md)|  | [optional] |

### Return type

[**kotlin.collections.List&lt;ScheduleSlot&gt;**](ScheduleSlot.md)

### Authorization


Configure bearerAuth:
    ApiClient().setBearerToken("TOKEN")

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

