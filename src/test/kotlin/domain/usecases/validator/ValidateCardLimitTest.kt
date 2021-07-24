package domain.usecases.validator

import core.AccountCantBeNull
import domain.model.Violation
import domain.model.Account
import domain.model.Transaction
import org.junit.Assert.*
import org.junit.Test
import java.time.LocalDateTime

class ValidateCardLimitTest {

    private val useCase = ValidateCardLimit()

    @Test
    fun testNullAccount() {
        assertThrows(AccountCantBeNull::class.java) { useCase.execute() }
    }

    @Test
    fun testValidTransaction() {
        val account = Account(true, 1200)
        val transaction = Transaction("Burger King", 200, LocalDateTime.now())
        useCase.with(account, transaction).execute()
    }

    @Test
    fun testInsufficientLimit() {
        val account = Account(true, 10)
        val transaction = Transaction("Burger King", 200, LocalDateTime.now())
        val violation = useCase.with(account, transaction).execute()
        assertNotNull(violation)
        assertEquals(violation, Violation.InsufficientLimit)
    }

}