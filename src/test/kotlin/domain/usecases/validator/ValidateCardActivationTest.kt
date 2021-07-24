package domain.usecases.validator

import core.AccountCantBeNull
import domain.model.Account
import domain.model.Violation
import org.junit.Assert
import org.junit.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class ValidateCardActivationTest {

    private val useCase = ValidateCardActivation()

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
        Assert.assertEquals(violation, Violation.CardNotActive)
    }

}