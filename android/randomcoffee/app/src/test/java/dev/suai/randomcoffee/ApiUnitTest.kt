package dev.suai.randomcoffee

import dev.suai.randomcoffee.data.AuthApiRepository
import dev.suai.randomcoffee.domain.entity.AuthResult
import dev.suai.randomcoffee.schema.api.AuthApi
import dev.suai.randomcoffee.schema.model.LoginDetails
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Test
import retrofit2.Response

class AuthRepositoryImplTest {
    // Authentication with incorrect credentials returns AuthResult.Error
    @Test
    fun authentication_with_incorrect_credentials_returns_authresult_error() {
        runBlocking {
            // Given
            val mockApi = mockk<AuthApi>()
            val loginDetails = LoginDetails("invalidUser", "invalidPassword")

            coEvery { mockApi.loginPost(any()) } returns Response.error(
                401,
                ResponseBody.create(null, "")
            )


            val repository = AuthApiRepository(mockApi)

            // When
            val result = repository.authenticate("invalidUser", "invalidPassword")

            // Then
            assertTrue(result is AuthResult.Error)
            assertEquals("Auth failed", (result as AuthResult.Error).msg)
        }
    }
}

