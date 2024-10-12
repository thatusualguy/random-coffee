# InterestsApi

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**interestsGet**](InterestsApi.md#interestsGet) | **GET** interests |  |
| [**interestsUserIdGet**](InterestsApi.md#interestsUserIdGet) | **GET** interests/{userId} |  |
| [**interestsUserIdPost**](InterestsApi.md#interestsUserIdPost) | **POST** interests/{userId} |  |





### Example
```kotlin
// Import classes:
//import org.openapitools.client.*
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiClient = ApiClient()
val webService = apiClient.createWebservice(InterestsApi::class.java)

val result : kotlin.collections.List<InterestGroup> = webService.interestsGet()
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**kotlin.collections.List&lt;InterestGroup&gt;**](InterestGroup.md)

### Authorization

No authorization required

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
val webService = apiClient.createWebservice(InterestsApi::class.java)
val userId : kotlin.String = userId_example // kotlin.String | 

val result : kotlin.collections.List<Interest> = webService.interestsUserIdGet(userId)
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **userId** | **kotlin.String**|  | |

### Return type

[**kotlin.collections.List&lt;Interest&gt;**](Interest.md)

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
val webService = apiClient.createWebservice(InterestsApi::class.java)
val userId : kotlin.String = userId_example // kotlin.String | 
val interest : kotlin.collections.List<Interest> =  // kotlin.collections.List<Interest> | 

val result : kotlin.collections.List<Interest> = webService.interestsUserIdPost(userId, interest)
```

### Parameters
| **userId** | **kotlin.String**|  | |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **interest** | [**kotlin.collections.List&lt;Interest&gt;**](Interest.md)|  | [optional] |

### Return type

[**kotlin.collections.List&lt;Interest&gt;**](Interest.md)

### Authorization


Configure bearerAuth:
    ApiClient().setBearerToken("TOKEN")

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

