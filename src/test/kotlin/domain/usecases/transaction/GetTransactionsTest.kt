package domain.usecases.transaction

import core.MerchantCantBeNull
import data.repository.TransactionRepository
import domain.model.Transaction
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import java.time.LocalDateTime
import kotlin.test.assertFailsWith

class GetTransactionsTest {

    private val merchantToSearch = "Burger King"
    private var transactions = arrayListOf<Transaction>()

    @Before
    fun init() {
        val merchants = listOf("Burger King", "Uber Eats")
        val transactionsMax = 100
        transactions.clear()
        for (i in 1..transactionsMax) {
            val transaction = Transaction(merchants.random(), i, LocalDateTime.now())
            transactions.add(transaction)
        }
    }

    @Test
    fun testGetAllTransactionMerchantNull() {
        val repository = mock<TransactionRepository> { on { getTransactions(merchantToSearch) } doReturn transactions }
        val useCase = GetTransactions(repository)
        assertFailsWith(MerchantCantBeNull::class) { useCase.execute() }
    }

    @Test
    fun testGetAllTransactionsWithMerchant() {
        val repository = mock<TransactionRepository> { on { getTransactions(merchantToSearch) } doReturn transactions }
        val useCase = GetTransactions(repository)
        val lastTransactions = useCase.with(merchantToSearch).execute()
        assert(lastTransactions.isNotEmpty())
    }

    @Test
    fun testGetAllTransactionsWithMerchantWithEmptyResponse() {
        val repository = mock<TransactionRepository> { on { getTransactions(merchantToSearch) } doReturn listOf() }
        val useCase = GetTransactions(repository)
        val lastTransactions = useCase.with(merchantToSearch).execute()
        assert(lastTransactions.isEmpty())
    }

}