package data.datasource

import domain.model.Account
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals

class AccountDatasourceImplTest {
    private lateinit var datasource: AccountDatasource

    @Before
    fun init() {
        datasource = AccountDatasourceImpl()
    }

    @Test
    fun testAddAccount() {
        val account = Account(true, 199)
        assert(datasource.addAccount(account))
    }

    @Test
    fun testDatasourceContainsAccount() {
        val account = Account(false, 10)
        datasource.addAccount(account)
        assert(datasource.containsAccount())
    }

    @Test
    fun testGetCurrentAccount() {
        val account = Account(false, 10)
        datasource.addAccount(account)
        val currentAccount = datasource.getCurrentAccount()
        assertEquals(currentAccount.activeCard, account.activeCard)
        assertEquals(currentAccount.availableLimit, account.availableLimit)
    }

    @Test
    fun testAddTwoAccount() {
        val fistAccount = Account(false, 10)
        val secondAccount = Account(true, 300)
        assert(datasource.addAccount(fistAccount))
        assertFalse(datasource.addAccount(secondAccount))
        val currentAccount = datasource.getCurrentAccount()
        assertEquals(currentAccount.activeCard, fistAccount.activeCard)
        assertEquals(currentAccount.availableLimit, fistAccount.availableLimit)
        assertNotEquals(currentAccount.activeCard, secondAccount.activeCard)
        assertNotEquals(currentAccount.availableLimit, secondAccount.availableLimit)
    }

}