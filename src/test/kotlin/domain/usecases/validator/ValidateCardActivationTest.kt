package domain.usecases.validator

import core.AccountCantBeNull
import domain.model.Violation
import domain.model.Account
import org.junit.Assert
import org.junit.Assert.assertThrows
import org.junit.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class ValidateCardActivationTest {

    private val useCase = ValidateCardActivation()

    @Test
    fun testNullAccount() {
        assertThrows(AccountCantBeNull::class.java) { useCase.execute() }
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