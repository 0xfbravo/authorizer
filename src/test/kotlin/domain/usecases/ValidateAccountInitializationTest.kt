package domain.usecases

import data.repository.AccountRepository
import domain.AccountNotInitialized
import org.junit.Assert.assertThrows
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class ValidateAccountInitializationTest {

    @Test
    fun testAccountNotInitialized() {
        val repository = mock<AccountRepository> { on { containsAccount() } doReturn false }
        val useCase = ValidateAccountInitialization(repository)
        assertThrows(AccountNotInitialized::class.java) { useCase.execute() }
    }

    @Test
    fun testAccountInitialized() {
        val repository = mock<AccountRepository> { on { containsAccount() } doReturn true }
        val useCase = ValidateAccountInitialization(repository)
        useCase.execute()
    }

}