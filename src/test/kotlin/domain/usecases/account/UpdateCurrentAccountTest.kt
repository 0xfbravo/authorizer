package domain.usecases.account

import core.AccountCantBeNull
import core.AccountCantBeUpdated
import data.repository.AccountRepository
import domain.model.Account
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import kotlin.test.assertFailsWith

class UpdateCurrentAccountTest {

    private val account = Account(true, 100)

    @Test
    fun testUpdateCurrentAccount() {
        val repository = mock<AccountRepository> { on { updateCurrentAccount(account) } doReturn true }
        val useCase = UpdateCurrentAccount(repository)
        useCase.with(account).execute()
    }

    @Test
    fun testUpdateCurrentAccountError() {
        val repository = mock<AccountRepository> { on { updateCurrentAccount(account) } doReturn false }
        val useCase = UpdateCurrentAccount(repository)
        assertFailsWith(AccountCantBeUpdated::class) { useCase.with(account).execute() }
    }

    @Test
    fun testUpdateCurrentAccountNull() {
        val repository = mock<AccountRepository> { on { updateCurrentAccount(account) } doReturn true }
        val useCase = UpdateCurrentAccount(repository)
        assertFailsWith(AccountCantBeNull::class) { useCase.execute() }
    }

}