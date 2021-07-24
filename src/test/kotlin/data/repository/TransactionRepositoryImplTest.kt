package data.repository

import data.datasource.TransactionDataSource
import domain.model.Transaction
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import java.time.LocalDateTime
import kotlin.test.assertFalse

class TransactionRepositoryImplTest {

    private val merchant = "Burger King"
    private val transaction = Transaction(merchant, 123, LocalDateTime.now())

    @Test
    fun testAddTransaction() {
        val dataSource = mock<TransactionDataSource> { on { addTransaction(transaction) } doReturn true }
        val repository: TransactionRepository = TransactionRepositoryImpl(dataSource)
        assert(repository.addTransaction(transaction))
    }

    @Test
    fun testAddTransactionError() {
        val dataSource = mock<TransactionDataSource> { on { addTransaction(transaction) } doReturn false }
        val repository: TransactionRepository = TransactionRepositoryImpl(dataSource)
        assertFalse(repository.addTransaction(transaction))
    }

    @Test
    fun testGetTransactionsWithData() {
        val dataSource = mock<TransactionDataSource> { on { getTransactions() } doReturn listOf(transaction) }
        val repository: TransactionRepository = TransactionRepositoryImpl(dataSource)
        assert(repository.getTransactions().isNotEmpty())
    }

    @Test
    fun testGetTransactionsEmpty() {
        val dataSource = mock<TransactionDataSource> { on { getTransactions() } doReturn listOf() }
        val repository: TransactionRepository = TransactionRepositoryImpl(dataSource)
        assert(repository.getTransactions().isEmpty())
    }

    @Test
    fun testGetTransactionsWithMerchantWithData() {
        val dataSource = mock<TransactionDataSource> { on { getTransactions(merchant) } doReturn listOf(transaction) }
        val repository: TransactionRepository = TransactionRepositoryImpl(dataSource)
        assert(repository.getTransactions(merchant).isNotEmpty())
    }

    @Test
    fun testGetTransactionsWithMerchantEmpty() {
        val dataSource = mock<TransactionDataSource> { on { getTransactions(merchant) } doReturn listOf() }
        val repository: TransactionRepository = TransactionRepositoryImpl(dataSource)
        assert(repository.getTransactions(merchant).isEmpty())
    }

}