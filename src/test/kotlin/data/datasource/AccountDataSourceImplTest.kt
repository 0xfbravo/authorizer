package data.datasource

import domain.model.Account
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull

class AccountDataSourceImplTest {
    private lateinit var dataSource: AccountDataSource

    @Before
    fun init() {
        dataSource = AccountDataSourceImpl()
    }

    @Test
    fun testAddAccount() {
        val account = Account(true, 199)
        assert(dataSource.addAccount(account))
    }

    @Test
    fun testDatasourceContainsAccount() {
        val account = Account(false, 10)
        dataSource.addAccount(account)
        assert(dataSource.containsAccount())
    }

    @Test
    fun testDatasourceUpdateAccount() {
        val account = Account(false, 10)
        dataSource.addAccount(account)
        assert(dataSource.containsAccount())

        val updatedAccount = Account(true, 1300)
        dataSource.updateCurrentAccount(updatedAccount)

        val currentAccount = dataSource.getCurrentAccount()
        assertNotNull(currentAccount)
        assertEquals(currentAccount.activeCard, updatedAccount.activeCard)
        assertEquals(currentAccount.availableLimit, updatedAccount.availableLimit)
    }

    @Test
    fun testGetCurrentAccount() {
        val account = Account(false, 10)
        dataSource.addAccount(account)

        val currentAccount = dataSource.getCurrentAccount()
        assertNotNull(currentAccount)
        assertEquals(currentAccount.activeCard, account.activeCard)
        assertEquals(currentAccount.availableLimit, account.availableLimit)
    }

    @Test
    fun testAddTwoAccounts() {
        val fistAccount = Account(false, 10)
        val secondAccount = Account(true, 300)
        assert(dataSource.addAccount(fistAccount))
        assertFalse(dataSource.addAccount(secondAccount))

        val currentAccount = dataSource.getCurrentAccount()
        assertNotNull(currentAccount)
        assertEquals(currentAccount.activeCard, fistAccount.activeCard)
        assertEquals(currentAccount.availableLimit, fistAccount.availableLimit)
        assertNotEquals(currentAccount.activeCard, secondAccount.activeCard)
        assertNotEquals(currentAccount.availableLimit, secondAccount.availableLimit)
    }

}