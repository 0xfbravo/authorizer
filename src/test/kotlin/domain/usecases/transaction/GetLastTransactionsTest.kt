package domain.usecases.transaction

import data.repository.TransactionRepository
import domain.model.Transaction
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import java.time.LocalDateTime

class GetLastTransactionsTest {

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
    fun testGetAllTransactionsWithoutMerchant() {
        val repository = mock<TransactionRepository> { on { getTransactions() } doReturn transactions }
        val useCase = GetLastTransactions(repository)
        val lastTransactions = useCase.execute()
        assert(lastTransactions.isNotEmpty())
    }

    @Test
    fun testGetAllTransactionsWithoutMerchantWithEmptyResponse() {
        val repository = mock<TransactionRepository> { on { getTransactions() } doReturn listOf() }
        val useCase = GetLastTransactions(repository)
        val lastTransactions = useCase.execute()
        assert(lastTransactions.isEmpty())
    }

    @Test
    fun testGetAllTransactionsWithMerchant() {
        val repository = mock<TransactionRepository> { on { getTransactions(merchantToSearch) } doReturn transactions }
        val useCase = GetLastTransactions(repository)
        val lastTransactions = useCase.with(merchantToSearch).execute()
        assert(lastTransactions.isNotEmpty())
    }

    @Test
    fun testGetAllTransactionsWithMerchantWithEmptyResponse() {
        val repository = mock<TransactionRepository> { on { getTransactions(merchantToSearch) } doReturn listOf() }
        val useCase = GetLastTransactions(repository)
        val lastTransactions = useCase.with(merchantToSearch).execute()
        assert(lastTransactions.isEmpty())
    }

}