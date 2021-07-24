package data.repository

import data.datasource.AccountDataSource
import domain.model.Account
import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class AccountRepositoryImplTest {

    private val account = Account(true, 199)

    @Test
    fun testAddAccount() {
        val dataSource = mock<AccountDataSource> { on { addAccount(account) } doReturn true }
        val repository: AccountRepository = AccountRepositoryImpl(dataSource)
        assert(repository.addAccount(account))
    }

    @Test
    fun testAddAccountError() {
        val dataSource = mock<AccountDataSource> { on { addAccount(account) } doReturn false }
        val repository: AccountRepository = AccountRepositoryImpl(dataSource)
        assertFalse(repository.addAccount(account))
    }

    @Test
    fun testGetCurrentAccount() {
        val dataSource = mock<AccountDataSource> { on { getCurrentAccount() } doReturn account }
        val repository: AccountRepository = AccountRepositoryImpl(dataSource)
        assertNotNull(repository.getCurrentAccount())
    }

    @Test
    fun testGetCurrentAccountError() {
        val dataSource = mock<AccountDataSource> { on { getCurrentAccount() } doReturn null }
        val repository: AccountRepository = AccountRepositoryImpl(dataSource)
        assertNull(repository.getCurrentAccount())
    }

    @Test
    fun testContainsAccount() {
        val dataSource = mock<AccountDataSource> { on { containsAccount() } doReturn true }
        val repository: AccountRepository = AccountRepositoryImpl(dataSource)
        assert(repository.containsAccount())
    }

    @Test
    fun testContainsAccountError() {
        val dataSource = mock<AccountDataSource> { on { containsAccount() } doReturn false }
        val repository: AccountRepository = AccountRepositoryImpl(dataSource)
        assertFalse(repository.containsAccount())
    }

}