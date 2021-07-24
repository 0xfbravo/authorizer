package domain.usecases.validator

import core.TransactionCantBeNull
import data.repository.TransactionRepository
import domain.model.Transaction
import domain.model.Violation
import domain.usecases.transaction.GetTransactions
import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import java.time.LocalDateTime
import kotlin.test.assertFailsWith

class ValidateDoubleTransactionTest {

    private val merchant = "Burger King"
    private val amount = 200

    @Test
    fun validateDoubleTransactionNullTransaction() {
        val repository = mock<TransactionRepository> { on { getTransactions(merchant) } doReturn emptyList() }
        val getLastTransactions = GetTransactions(repository)
        val useCase = ValidateDoubleTransaction(getLastTransactions)
        assertFailsWith(TransactionCantBeNull::class) { useCase.execute() }
    }

    @Test
    fun validateDoubleTransactionSuccess() {
        val repository = mock<TransactionRepository> { on { getTransactions(merchant) } doReturn emptyList() }
        val getLastTransactions = GetTransactions(repository)
        val useCase = ValidateDoubleTransaction(getLastTransactions)
        val transaction = Transaction(merchant, amount, LocalDateTime.now())
        val violation = useCase.with(transaction).execute()
        assertNull(violation)
    }

    @Test
    fun validateDoubleTransactionViolation() {
        val transactionsMax = 15
        val transactions = arrayListOf<Transaction>()
        for (i in 1..transactionsMax) {
            val transaction = Transaction(merchant, amount, LocalDateTime.now().minusMinutes(i.toLong()))
            transactions.add(transaction)
        }

        val repository = mock<TransactionRepository> { on { getTransactions(merchant) } doReturn transactions }
        val getLastTransactions = GetTransactions(repository)
        val useCase = ValidateDoubleTransaction(getLastTransactions)
        val transaction = Transaction(merchant, amount, LocalDateTime.now())

        val violation = useCase.with(transaction).execute()
        assertNotNull(violation)
        assertEquals(violation, Violation.DoubleTransaction)
    }

}