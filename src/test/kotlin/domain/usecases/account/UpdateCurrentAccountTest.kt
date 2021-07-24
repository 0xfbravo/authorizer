package domain.usecases.account

import data.datasource.AccountDataSource
import data.repository.AccountRepository
import data.repository.AccountRepositoryImpl
import domain.AccountCantBeNull
import domain.AccountCantBeUpdated
import domain.model.Account
import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

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
        assertThrows(AccountCantBeUpdated::class.java) { useCase.with(account).execute() }
    }

    @Test
    fun testUpdateCurrentAccountNull() {
        val repository = mock<AccountRepository> { on { updateCurrentAccount(account) } doReturn true }
        val useCase = UpdateCurrentAccount(repository)
        assertThrows(AccountCantBeNull::class.java) { useCase.execute() }
    }

}