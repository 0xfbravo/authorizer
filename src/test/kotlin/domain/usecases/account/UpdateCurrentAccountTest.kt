package domain.usecases.account

import core.*
import data.repository.AccountRepository
import domain.model.Account
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import org.koin.test.mock.declare
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import kotlin.test.assertFailsWith

class UpdateCurrentAccountTest: KoinTest {

    private val useCase by inject<UpdateCurrentAccount>()
    private val account = Account(true, 100)

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(utils, dataLayer, domainLayer, presentationLayer)
    }

    @Test
    fun testUpdateCurrentAccount() {
        declare { mock<AccountRepository> { on { updateCurrentAccount(account) } doReturn true } }
        useCase.with(account).execute()
    }

    @Test
    fun testUpdateCurrentAccountError() {
        declare { mock<AccountRepository> { on { updateCurrentAccount(account) } doReturn false } }
        assertFailsWith(AccountCantBeUpdated::class) { useCase.with(account).execute() }
    }

    @Test
    fun testUpdateCurrentAccountNull() {
        assertFailsWith(AccountCantBeNull::class) { useCase.execute() }
    }

}