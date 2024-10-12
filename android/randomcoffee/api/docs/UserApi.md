# UserApi

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**userUserIdGet**](UserApi.md#userUserIdGet) | **GET** user/{userId} |  |
| [**userUserIdPatch**](UserApi.md#userUserIdPatch) | **PATCH** user/{userId} |  |





### Example
```kotlin
// Import classes:
//import org.openapitools.client.*
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiClient = ApiClient()
apiClient.setBearerToken("TOKEN")
val webService = apiClient.createWebservice(UserApi::class.java)
val userId : kotlin.String = userId_example // kotlin.String | 

val result : User = webService.userUserIdGet(userId)
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **userId** | **kotlin.String**|  | |

### Return type

[**User**](User.md)

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
val webService = apiClient.createWebservice(UserApi::class.java)
val userId : kotlin.String = userId_example // kotlin.String | 
val userRegister : UserRegister =  // UserRegister | 

val result : User = webService.userUserIdPatch(userId, userRegister)
```

### Parameters
| **userId** | **kotlin.String**|  | |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **userRegister** | [**UserRegister**](UserRegister.md)|  | [optional] |

### Return type

[**User**](User.md)

### Authorization


Configure bearerAuth:
    ApiClient().setBearerToken("TOKEN")

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

