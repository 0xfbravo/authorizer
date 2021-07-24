package domain.usecases.validator

import core.*
import data.repository.TransactionRepository
import domain.model.Transaction
import domain.model.Violation
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import org.koin.test.mock.declare
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import java.time.LocalDateTime
import kotlin.test.assertFailsWith

class ValidateHighFrequencySmallIntervalTest: KoinTest {

    private val merchant = "Burger King"
    private val amount = 200

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(utils, dataLayer, domainLayer, presentationLayer)
    }

    @Test
    fun validateHighFrequencyNullTransaction() {
        val useCase by inject<ValidateHighFrequencySmallInterval>()
        assertFailsWith(TransactionCantBeNull::class) { useCase.execute() }
    }

    @Test
    fun validateHighFrequencySuccess() {
        val useCase by inject<ValidateHighFrequencySmallInterval>()
        val transaction = Transaction(merchant, amount, LocalDateTime.now())
        val violation = useCase.with(transaction).execute()
        assertNull(violation)
    }

    @Test
    fun validateHighFrequencyViolation() {
        val transactionsMax = 15
        val transactions = arrayListOf<Transaction>()
        for (i in 1..transactionsMax) {
            val transaction = Transaction(merchant, amount, LocalDateTime.now().minusSeconds(20 * i.toLong()))
            transactions.add(transaction)
        }

        declare { mock<TransactionRepository> { on { getTransactions(merchant) } doReturn transactions } }
        val useCase by inject<ValidateHighFrequencySmallInterval>()
        val transaction = Transaction(merchant, amount, LocalDateTime.now())

        val violation = useCase.with(transaction).execute()
        assertNotNull(violation)
        assertEquals(violation, Violation.HighFrequencySmallInterval)
    }

}