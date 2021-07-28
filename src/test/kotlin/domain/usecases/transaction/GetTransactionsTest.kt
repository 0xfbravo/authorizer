package domain.usecases.transaction

import core.dataLayer
import core.domainLayer
import core.presentationLayer
import core.utils
import data.repository.TransactionRepository
import domain.model.Transaction
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import org.koin.test.mock.declare
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import java.time.LocalDateTime

class GetTransactionsTest: KoinTest {

    private var transactions = arrayListOf<Transaction>()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(utils, dataLayer, domainLayer, presentationLayer)
    }

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
    fun testGetAllTransactions() {
        declare { mock<TransactionRepository> { on { getTransactions() } doReturn transactions } }
        val useCase by inject<GetTransactions>()
        val lastTransactions = useCase.execute()
        assert(lastTransactions.isNotEmpty())
    }

    @Test
    fun testGetAllTransactionsWithEmptyResponse() {
        val useCase by inject<GetTransactions>()
        val lastTransactions = useCase.execute()
        assert(lastTransactions.isEmpty())
    }

}