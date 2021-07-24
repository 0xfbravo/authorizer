package domain.usecases.validator

import domain.AccountCantBeNull
import domain.CardNotActive
import domain.model.Account
import org.junit.Assert.assertThrows
import org.junit.Test

class ValidateCardActivationTest {

    private val useCase = ValidateCardActivation()

    @Test
    fun testNullAccount() {
        assertThrows(AccountCantBeNull::class.java) { useCase.execute() }
    }

    @Test
    fun testActiveCard() {
        val account = Account(true, 1200)
        useCase.with(account).execute()
    }

    @Test
    fun testInactiveCard() {
        val account = Account(false, 1200)
        assertThrows(CardNotActive::class.java) { useCase.with(account).execute() }
    }

}