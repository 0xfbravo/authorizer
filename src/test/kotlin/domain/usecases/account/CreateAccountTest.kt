package domain.usecases.account

import core.*
import data.repository.AccountRepository
import domain.model.Account
import domain.model.Violation
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import org.koin.test.mock.declare
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CreateAccountTest: KoinTest {

    private val useCase by inject<CreateAccount>()
    private val account = Account(false, 1200)

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(utils, dataLayer, domainLayer, presentationLayer)
    }

    @Test
    fun testNullAccount() {
        declare { mock<AccountRepository> { on { addAccount(account) } doReturn false } }
        assertFailsWith(AccountCantBeNull::class) { useCase.execute() }
    }

    @Test
    fun testAlreadyInitializedAccount() {
        declare {
            mock<AccountRepository> {
                on { addAccount(account) } doReturn false
                on { getCurrentAccount() } doReturn Account(true, 1000)
            }
        }

        val response = useCase.with(account).execute()
        assert(response.violations.isNotEmpty())
        assertEquals(Violation.AccountAlreadyInitialized, response.violations.first())
    }

    @Test
    fun testSuccessCreateAccount() {
        val response = useCase.with(account).execute()
        assert(response.violations.isEmpty())
        assertEquals(account, response.account)
    }

}