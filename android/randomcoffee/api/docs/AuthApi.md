# AuthApi

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**loginPost**](AuthApi.md#loginPost) | **POST** login |  |
| [**registerPost**](AuthApi.md#registerPost) | **POST** register |  |





### Example
```kotlin
// Import classes:
//import org.openapitools.client.*
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiClient = ApiClient()
val webService = apiClient.createWebservice(AuthApi::class.java)
val loginDetails : LoginDetails =  // LoginDetails | 

val result : kotlin.String = webService.loginPost(loginDetails)
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **loginDetails** | [**LoginDetails**](LoginDetails.md)|  | [optional] |

### Return type

**kotlin.String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json




### Example
```kotlin
// Import classes:
//import org.openapitools.client.*
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiClient = ApiClient()
val webService = apiClient.createWebservice(AuthApi::class.java)
val userRegister : UserRegister =  // UserRegister | 

val result : kotlin.String = webService.registerPost(userRegister)
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **userRegister** | [**UserRegister**](UserRegister.md)|  | [optional] |

### Return type

**kotlin.String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

