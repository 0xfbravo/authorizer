package domain.usecases.validator

import core.dataLayer
import core.domainLayer
import core.presentationLayer
import core.utils
import data.repository.AccountRepository
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
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class ValidateAccountInitializationTest: KoinTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(utils, dataLayer, domainLayer, presentationLayer)
    }

    @Test
    fun testAccountNotInitialized() {
        declare { mock<AccountRepository> { on { containsAccount() } doReturn false } }
        val useCase by inject<ValidateAccountInitialization>()
        val violation = useCase.execute()
        assertNotNull(violation)
        assertEquals(Violation.AccountNotInitialized, violation)
    }

    @Test
    fun testAccountInitialized() {
        declare { mock<AccountRepository> { on { containsAccount() } doReturn true } }
        val useCase by inject<ValidateAccountInitialization>()
        val violation = useCase.execute()
        assertNull(violation)
    }

}