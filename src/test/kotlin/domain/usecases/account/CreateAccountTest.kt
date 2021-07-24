package domain.usecases.account

import data.repository.AccountRepository
import domain.AccountAlreadyInitialized
import domain.AccountCantBeNull
import domain.model.Account
import org.junit.Assert.assertThrows
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class CreateAccountTest {

    private val account = Account(false, 1200)

    @Test
    fun testNullAccount() {
        val repository = mock<AccountRepository> { on { addAccount(account) } doReturn false }
        val useCase = CreateAccount(repository)
        assertThrows(AccountCantBeNull::class.java) { useCase.execute() }
    }

    @Test
    fun testAlreadyInitializedAccount() {
        val repository = mock<AccountRepository> {
            on { addAccount(account) } doReturn false
            on { getCurrentAccount() } doReturn Account(true, 1000)
        }
        val useCase = CreateAccount(repository)
        assertThrows(AccountAlreadyInitialized::class.java) { useCase.with(account).execute() }
    }

    @Test
    fun testSuccessCreateAccount() {
        val repository = mock<AccountRepository> { on { addAccount(account) } doReturn true }
        val useCase = CreateAccount(repository)
        useCase.with(account).execute()
    }

}