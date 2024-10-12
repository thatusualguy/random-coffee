# MeetApi

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**meetApproveUserIdMeetIdPost**](MeetApi.md#meetApproveUserIdMeetIdPost) | **POST** meet/approve/{userId}/{meetId} |  |
| [**meetFutureUserIdGet**](MeetApi.md#meetFutureUserIdGet) | **GET** meet/future/{userId} |  |
| [**meetHistoryUserIdGet**](MeetApi.md#meetHistoryUserIdGet) | **GET** meet/history/{userId} |  |





### Example
```kotlin
// Import classes:
//import org.openapitools.client.*
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiClient = ApiClient()
apiClient.setBearerToken("TOKEN")
val webService = apiClient.createWebservice(MeetApi::class.java)
val userId : kotlin.Int = 56 // kotlin.Int | 
val meetId : kotlin.Int = 56 // kotlin.Int | 

webService.meetApproveUserIdMeetIdPost(userId, meetId)
```

### Parameters
| **userId** | **kotlin.Int**|  | |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **meetId** | **kotlin.Int**|  | |

### Return type

null (empty response body)

### Authorization


Configure bearerAuth:
    ApiClient().setBearerToken("TOKEN")

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined




### Example
```kotlin
// Import classes:
//import org.openapitools.client.*
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiClient = ApiClient()
apiClient.setBearerToken("TOKEN")
val webService = apiClient.createWebservice(MeetApi::class.java)
val userId : kotlin.Int = 56 // kotlin.Int | 

val result : FutureMeets = webService.meetFutureUserIdGet(userId)
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **userId** | **kotlin.Int**|  | |

### Return type

[**FutureMeets**](FutureMeets.md)

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
val webService = apiClient.createWebservice(MeetApi::class.java)
val userId : kotlin.String = userId_example // kotlin.String | 
val startDate : java.time.LocalDate = 2013-10-20 // java.time.LocalDate | 
val endDate : java.time.LocalDate = 2013-10-20 // java.time.LocalDate | 

val result : kotlin.collections.List<HistoryMeet> = webService.meetHistoryUserIdGet(userId, startDate, endDate)
```

### Parameters
| **userId** | **kotlin.String**|  | |
| **startDate** | **java.time.LocalDate**|  | [optional] |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **endDate** | **java.time.LocalDate**|  | [optional] |

### Return type

[**kotlin.collections.List&lt;HistoryMeet&gt;**](HistoryMeet.md)

### Authorization


Configure bearerAuth:
    ApiClient().setBearerToken("TOKEN")

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

