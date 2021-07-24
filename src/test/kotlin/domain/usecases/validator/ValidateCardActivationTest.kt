package domain.usecases.validator

import core.*
import domain.model.Account
import domain.model.Violation
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class ValidateCardActivationTest: KoinTest {

    private val useCase by inject<ValidateCardActivation>()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(utils, dataLayer, domainLayer, presentationLayer)
    }

    @Test
    fun testNullAccount() {
        assertFailsWith(AccountCantBeNull::class) { useCase.execute() }
    }

    @Test
    fun testActiveCard() {
        val account = Account(true, 1200)
        val violation = useCase.with(account).execute()
        assertNull(violation)
    }

    @Test
    fun testInactiveCard() {
        val account = Account(false, 1200)
        val violation = useCase.with(account).execute()
        assertNotNull(violation)
        assertEquals(Violation.CardNotActive, violation)
    }

}