package domain.usecases.transaction

import core.*
import data.repository.AccountRepository
import data.repository.TransactionRepository
import domain.model.Account
import domain.model.Transaction
import domain.model.Violation
import org.junit.Assert.*
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
        assertEquals(Violation.AccountNotInitialized, response.violations.first())
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
        assertEquals(Violation.CardNotActive, response.violations.first())
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
        assertEquals(Violation.InsufficientLimit, response.violations.first())
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
        }
        declare {
            mock<TransactionRepository> {
                on { getTransactions() } doReturn transactions
            }
        }

        val response = useCase.with(transaction).execute()
        assertNotNull(response.account.activeCard)
        assertNotNull(response.account.availableLimit)

        assert(response.violations.isNotEmpty())
        assertEquals(Violation.HighFrequencySmallInterval, response.violations.first())
    }

    @Test
    fun testCreateTransactionDoubleTransactionViolation() {
        val transactionsMax = 2
        val transactions = arrayListOf<Transaction>()
        for (i in 1..transactionsMax) {
            val transaction = Transaction("Burger King", 33, LocalDateTime.now())
            transactions.add(transaction)
        }

        declare {
            mock<AccountRepository> {
                on { containsAccount() } doReturn true
                on { getCurrentAccount() } doReturn activeCardHighLimit
                on { updateCurrentAccount(any()) } doReturn true
            }
        }
        declare {
            mock<TransactionRepository> {
                on { getTransactions(any()) } doReturn transactions
            }
        }

        val transaction = Transaction("Burger King", 33, LocalDateTime.now())
        val response = useCase.with(transaction).execute()
        assertNotNull(response.account.activeCard)
        assertNotNull(response.account.availableLimit)

        assert(response.violations.isNotEmpty())
        assertEquals(Violation.DoubleTransaction, response.violations.first())
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
        assertEquals(2, response.violations.size)
        assertEquals(Violation.CardNotActive, response.violations[0])
        assertEquals(Violation.InsufficientLimit, response.violations[1])
    }

    @Test
    fun testCreateTransactionSuccess() {
        val transactionsMax = 15
        val merchants = listOf("Burger King", "Uber Eats")
        val transactions = arrayListOf<Transaction>()
        for (i in 1..transactionsMax) {
            val transaction = Transaction(merchants.random(), i * 10, LocalDateTime.now().minusHours(i.toLong()))
            transactions.add(transaction)
        }

        declare {
            mock<AccountRepository> {
                on { containsAccount() } doReturn true
                on { getCurrentAccount() } doReturn activeCardHighLimit
                on { updateCurrentAccount(any()) } doReturn true
            }
        }
        declare {
            mock<TransactionRepository> {
                on { getTransactions(any()) } doReturn transactions
            }
        }

        val transaction = Transaction("Vivara", 450, LocalDateTime.now())
        val response = useCase.with(transaction).execute()
        assertNotNull(response.account.activeCard)
        assertNotNull(response.account.availableLimit)
        assertEquals(activeCardHighLimit.availableLimit?.minus(450), response.account.availableLimit)
        assert(response.violations.isEmpty())
    }

    @Test
    fun testCreateTransactionWithTransactionNull() {
        assertFailsWith(TransactionCantBeNull::class) { useCase.execute() }
    }

}