package domain.usecases

import data.repository.AccountRepository
import domain.AccountNotInitialized
import domain.model.Account
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class GetCurrentAccountTest {

    private val account = Account(true, 1200)

    @Test
    fun testAccountInitialized() {
        val repository = mock<AccountRepository> { on { getCurrentAccount() } doReturn account }
        val useCase = GetCurrentAccount(repository)
        val currentAccount = useCase.execute()
        assertEquals(account.activeCard, currentAccount.activeCard)
        assertEquals(account.availableLimit, currentAccount.availableLimit)
    }

    @Test
    fun testAccountNotInitialized() {
        val repository = mock<AccountRepository> { on { getCurrentAccount() } doReturn null }
        val useCase = GetCurrentAccount(repository)
        assertThrows(AccountNotInitialized::class.java) { useCase.execute() }
    }

}