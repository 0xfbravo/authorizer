package domain.usecases.transaction

import core.dataLayer
import core.domainLayer
import core.presentationLayer
import core.utils
import data.repository.AccountRepository
import data.repository.TransactionRepository
import domain.model.Account
import domain.model.Transaction
import domain.model.Violation
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import org.koin.test.mock.declare
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import java.time.LocalDateTime

class CreateTransactionTest: KoinTest {

    private val useCase by inject<CreateTransaction>()
    private val transaction = Transaction("Burger King", 100, LocalDateTime.now())
    private val inactiveCard = Account(false, 100)
    private val inactiveCardLowLimit = Account(false, 0)
    private val activeCardLowLimit = Account(true, 10)
    private val activeCardHighLimit = Account(true, 1000)

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(utils, dataLayer, domainLayer, presentationLayer)
    }

    @Test
    fun testCreateTransactionWithoutAccountInitialized() {
        declare {
            mock<AccountRepository> { on { containsAccount() } doReturn false }
        }

        val response = useCase.with(transaction).execute()
        assertNull(response.account.activeCard)
        assertNull(response.account.availableLimit)
        assert(response.violations.isNotEmpty())
        assertEquals(response.violations.first(), Violation.AccountNotInitialized)
    }

    @Test
    fun testCreateTransactionValidAccountWithCardNotActive() {
        declare {
            mock<AccountRepository> {
                on { containsAccount() } doReturn true
                on { getCurrentAccount() } doReturn inactiveCard
                on { updateCurrentAccount(any()) } doReturn true
            }
        }

        val response = useCase.with(transaction).execute()
        assertNotNull(response.account.activeCard)
        assertNotNull(response.account.availableLimit)

        assert(response.violations.isNotEmpty())
        assertEquals(response.violations.first(), Violation.CardNotActive)
    }

    @Test
    fun testCreateTransactionValidAccountWithInsufficientLimit() {
        declare {
            mock<AccountRepository> {
                on { containsAccount() } doReturn true
                on { getCurrentAccount() } doReturn activeCardLowLimit
                on { updateCurrentAccount(any()) } doReturn true
            }
        }

        val response = useCase.with(transaction).execute()
        assertNotNull(response.account.activeCard)
        assertNotNull(response.account.availableLimit)

        assert(response.violations.isNotEmpty())
        assertEquals(response.violations.first(), Violation.InsufficientLimit)
    }

    @Test
    fun testCreateTransactionHighFrequency() {
        val transactionsMax = 15
        val transactions = arrayListOf<Transaction>()
        for (i in 1..transactionsMax) {
            val transaction = Transaction("Burger King", 33, LocalDateTime.now().minusSeconds(20 * i.toLong()))
            transactions.add(transaction)
        }

        declare {
            mock<AccountRepository> {
                on { containsAccount() } doReturn true
                on { getCurrentAccount() } doReturn activeCardHighLimit
                on { updateCurrentAccount(any()) } doReturn true
            }
            mock<TransactionRepository> {
                on { getTransactions(any()) } doReturn transactions
            }
        }

        val response = useCase.with(transaction).execute()
        assertNotNull(response.account.activeCard)
        assertNotNull(response.account.availableLimit)

        assert(response.violations.isNotEmpty())
        assertEquals(response.violations.first(), Violation.HighFrequencySmallInterval)
    }

    @Test
    fun testCreateTransactionValidAccountMultipleViolations() {
        declare {
            mock<AccountRepository> {
                on { containsAccount() } doReturn true
                on { getCurrentAccount() } doReturn inactiveCardLowLimit
                on { updateCurrentAccount(any()) } doReturn true
            }
        }

        val response = useCase.with(transaction).execute()
        assertNotNull(response.account.activeCard)
        assertNotNull(response.account.availableLimit)

        assert(response.violations.isNotEmpty())
        assertEquals(response.violations.size, 2)
        assertEquals(response.violations[0], Violation.CardNotActive)
        assertEquals(response.violations[1], Violation.InsufficientLimit)
    }

}