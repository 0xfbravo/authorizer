package data.datasource

import domain.model.Transaction
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime
import kotlin.system.measureTimeMillis
import kotlin.test.assertEquals

class TransactionDatasourceImplTest {

    private lateinit var datasource: TransactionDatasource

    @Before
    fun init() {
        datasource = TransactionDatasourceImpl()
    }

    @Test
    fun testAddTransaction() {
        val transaction = Transaction("Burger King", 20, LocalDateTime.of(2019,2,13,11,0,0,0))
        assert(datasource.addTransaction(transaction))
    }

    @Test
    fun testAddSameTransactionTwice() {
        val transaction = Transaction("Burger King", 20, LocalDateTime.of(2019,2,13,11,0,0,0))
        val sameTransaction = Transaction("Burger King", 20, LocalDateTime.of(2019,2,13,11,0,0,0))
        assert(datasource.addTransaction(transaction))
        assertFalse(datasource.addTransaction(sameTransaction))
    }

    @Test
    fun testGetAllTransactions() {
        val merchants = listOf("Burger King", "Uber Eats")
        val transactionsMax = 10
        for (i in 1..transactionsMax) {
            val transaction = Transaction(merchants.random(), i, LocalDateTime.now())
            datasource.addTransaction(transaction)
        }
        assertFalse(datasource.getTransactions().isEmpty())
        assertEquals(datasource.getTransactions().size, transactionsMax)
    }

    @Test
    fun testGetAllTransactionsWithEmptyResponse() {
        assert(datasource.getTransactions().isEmpty())
    }

    @Test
    fun testGetAllTransactionsFromMerchant() {
        val merchantToSearch = "Uber Eats"
        val merchants = listOf("Burger King", "Uber Eats")
        val transactionsMax = 10
        for (i in 1..transactionsMax) {
            val transaction = Transaction(merchants.random(), i, LocalDateTime.now())
            datasource.addTransaction(transaction)
        }
        assertFalse(datasource.getTransactions(merchantToSearch).isEmpty())
    }

    @Test
    fun testGetAllTransactionsFromMerchantWithEmptyResponse() {
        assert(datasource.getTransactions("Uber Eats").isEmpty())
    }

    @Test(timeout = 5000)
    fun testGetAllTransactionsFromMerchantWithHugeDataset() {
        val merchantToSearch = "Pizza Da'skina"
        val merchants = listOf("Burger King", "Uber Eats", "Mc Donald's", "Pizza Da'skina", "Outro")
        val transactionsMax = 3000000
        for (i in 1..transactionsMax) {
            val transaction = Transaction(merchants.random(), i, LocalDateTime.now())
            assert(datasource.addTransaction(transaction))
        }
        val totalTime = measureTimeMillis {
            assertFalse(datasource.getTransactions(merchantToSearch).isEmpty())
        }
        println("Huge dataset ($transactionsMax) search time: $totalTime ms")
    }

}