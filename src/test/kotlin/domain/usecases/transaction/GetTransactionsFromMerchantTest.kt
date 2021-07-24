package domain.usecases.transaction

import core.*
import data.repository.TransactionRepository
import domain.model.Transaction
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import org.koin.test.mock.declare
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import java.time.LocalDateTime
import kotlin.test.assertFailsWith

class GetTransactionsFromMerchantTest: KoinTest {
    private val merchantToSearch = "Burger King"
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
    fun testGetAllTransactionsFromMerchantWithMerchantNull() {
        declare { mock<TransactionRepository> { on { getTransactions() } doReturn transactions } }
        val useCase by inject<GetTransactionsFromMerchant>()
        assertFailsWith(MerchantCantBeNull::class) { useCase.execute() }
    }

    @Test
    fun testGetAllTransactionsWithMerchant() {
        declare { mock<TransactionRepository> { on { getTransactions(any()) } doReturn transactions } }
        val useCase by inject<GetTransactionsFromMerchant>()
        val merchantTransactions = useCase.with(merchantToSearch).execute()
        assert(merchantTransactions.isNotEmpty())
    }

    @Test
    fun testGetAllTransactionsWithMerchantWithEmptyResponse() {
        val useCase by inject<GetTransactionsFromMerchant>()
        val merchantTransactions = useCase.with(merchantToSearch).execute()
        assert(merchantTransactions.isEmpty())
    }
}