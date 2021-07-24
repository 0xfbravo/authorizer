package domain.usecases.account

import data.repository.AccountRepository
import core.AccountCantBeNull
import domain.model.Violation
import domain.model.Account
import org.junit.Assert.assertThrows
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

class CreateAccountTest {

    private val account = Account(false, 1200)

    @Test
    fun testNullAccount() {
        val repository = mock<AccountRepository> { on { addAccount(account) } doReturn false }
        val useCase = CreateAccount(repository)
        assertFailsWith(AccountCantBeNull::class) { useCase.execute() }
    }

    @Test
    fun testAlreadyInitializedAccount() {
        val repository = mock<AccountRepository> {
            on { addAccount(account) } doReturn false
            on { getCurrentAccount() } doReturn Account(true, 1000)
        }
        val useCase = CreateAccount(repository)
        val violation = useCase.with(account).execute()
        assertNotNull(violation)
        assertEquals(violation, Violation.AccountAlreadyInitialized)
    }

    @Test
    fun testSuccessCreateAccount() {
        val repository = mock<AccountRepository> { on { addAccount(account) } doReturn true }
        val useCase = CreateAccount(repository)
        useCase.with(account).execute()
    }

}