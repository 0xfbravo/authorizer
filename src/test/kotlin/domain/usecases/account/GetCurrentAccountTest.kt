package domain.usecases.account

import core.dataLayer
import core.domainLayer
import core.presentationLayer
import core.utils
import data.repository.AccountRepository
import domain.model.Account
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import org.koin.test.mock.declare
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import kotlin.test.assertNull

class GetCurrentAccountTest: KoinTest {

    private val useCase by inject<GetCurrentAccount>()
    private val account = Account(true, 1200)

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(utils, dataLayer, domainLayer, presentationLayer)
    }

    @Test
    fun testAccountInitialized() {
        declare { mock<AccountRepository> { on { getCurrentAccount() } doReturn account } }
        val currentAccount = useCase.execute()!!
        assertEquals(account.activeCard, currentAccount.activeCard)
        assertEquals(account.availableLimit, currentAccount.availableLimit)
    }

    @Test
    fun testAccountNotInitialized() {
        declare { mock<AccountRepository> { on { getCurrentAccount() } doReturn null } }
        val currentAccount = useCase.execute()
        assertNull(currentAccount)
    }

}