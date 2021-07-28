package domain.usecases.validator

import core.*
import domain.model.Account
import domain.model.Transaction
import domain.model.Violation
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import org.mockito.kotlin.any
import java.time.LocalDateTime
import kotlin.test.assertFailsWith

class ValidateCardLimitTest: KoinTest {

    private val useCase by inject<ValidateCardLimit>()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(utils, dataLayer, domainLayer, presentationLayer)
    }

    @Test
    fun testNullAccount() {
        assertFailsWith(AccountCantBeNull::class) { useCase.execute() }
    }

    @Test
    fun testValidAccountWithNullLimit() {
        assertFailsWith(AccountLimitCantBeNull::class) { useCase.with(Account(), null).execute() }
    }

    @Test
    fun testValidAccountWithNullTransaction() {
        val account = Account(true, 1200)
        assertFailsWith(TransactionCantBeNull::class) { useCase.with(account, null).execute() }
    }

    @Test
    fun testValidTransaction() {
        val account = Account(true, 1200)
        val transaction = Transaction("Burger King", 200, LocalDateTime.now())
        val violation = useCase.with(account, transaction).execute()
        assertNull(violation)
    }

    @Test
    fun testInsufficientLimit() {
        val account = Account(true, 10)
        val transaction = Transaction("Burger King", 200, LocalDateTime.now())
        val violation = useCase.with(account, transaction).execute()
        assertNotNull(violation)
        assertEquals(Violation.InsufficientLimit, violation)
    }

}