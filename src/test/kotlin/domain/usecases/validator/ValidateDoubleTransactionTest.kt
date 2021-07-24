package domain.usecases.validator

import data.repository.TransactionRepository
import core.TransactionCantBeNull
import domain.model.Violation
import domain.model.Transaction
import domain.usecases.transaction.GetLastTransactions
import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import java.time.LocalDateTime

class ValidateDoubleTransactionTest {

    private val merchant = "Burger King"
    private val amount = 200

    @Test
    fun validateDoubleTransactionNullTransaction() {
        val repository = mock<TransactionRepository> { on { getTransactions(merchant) } doReturn emptyList() }
        val getLastTransactions = GetLastTransactions(repository)
        val useCase = ValidateDoubleTransaction(getLastTransactions)
        assertThrows(TransactionCantBeNull::class.java) { useCase.execute() }
    }

    @Test
    fun validateDoubleTransactionSuccess() {
        val repository = mock<TransactionRepository> { on { getTransactions(merchant) } doReturn emptyList() }
        val getLastTransactions = GetLastTransactions(repository)
        val useCase = ValidateDoubleTransaction(getLastTransactions)
        val transaction = Transaction(merchant, amount, LocalDateTime.now())
        useCase.with(transaction).execute()
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
        val getLastTransactions = GetLastTransactions(repository)
        val useCase = ValidateDoubleTransaction(getLastTransactions)
        val transaction = Transaction(merchant, amount, LocalDateTime.now())

        val violation = useCase.with(transaction).execute()
        assertNotNull(violation)
        assertEquals(violation, Violation.DoubleTransaction)
    }

}