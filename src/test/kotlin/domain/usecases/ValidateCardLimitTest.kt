package domain.usecases

import domain.AccountCantBeNull
import domain.InsufficientLimit
import domain.model.Account
import domain.model.Transaction
import domain.usecases.validator.ValidateCardLimit
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
        assertThrows(InsufficientLimit::class.java) { useCase.with(account, transaction).execute() }
    }

}