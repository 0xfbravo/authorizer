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
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import java.time.LocalDateTime

class CreateTransactionTest: KoinTest {

    private val transaction = Transaction("Burger King", 100, LocalDateTime.now())
    private val inactiveCard = Account(false, 100)
    private val inactiveCardLowLimit = Account(false, 0)
    private val activeCardLowLimit = Account(true, 10)

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger()
        modules(utils, dataLayer, domainLayer, presentationLayer)
    }

    @Test
    fun testCreateTransactionWithoutAccountInitialized() {
        loadKoinModules(
            module {
                single {
                    mock<AccountRepository> { on { containsAccount() } doReturn false }
                }
                single {
                    mock<TransactionRepository> { }
                }
            }
        )

        val useCase by inject<CreateTransaction>()
        val response = useCase.with(transaction).execute()
        assertNull(response.account.activeCard)
        assertNull(response.account.availableLimit)
        assert(response.violations.isNotEmpty())
        assertEquals(response.violations.first(), Violation.AccountNotInitialized)
    }

    @Test
    fun testCreateTransactionValidAccountWithCardNotActive() {
        loadKoinModules(
            module {
                single {
                    mock<AccountRepository> {
                        on { containsAccount() } doReturn true
                        on { getCurrentAccount() } doReturn inactiveCard
                        on { updateCurrentAccount(any()) } doReturn true
                    }
                }
                single {
                    mock<TransactionRepository> { }
                }
            }
        )

        val useCase by inject<CreateTransaction>()
        val response = useCase.with(transaction).execute()
        assertNotNull(response.account.activeCard)
        assertNotNull(response.account.availableLimit)

        assert(response.violations.isNotEmpty())
        assertEquals(response.violations.first(), Violation.CardNotActive)
    }

    @Test
    fun testCreateTransactionValidAccountWithInsufficientLimit() {
        loadKoinModules(
            module {
                single {
                    mock<AccountRepository> {
                        on { containsAccount() } doReturn true
                        on { getCurrentAccount() } doReturn activeCardLowLimit
                        on { updateCurrentAccount(any()) } doReturn true
                    }
                }
                single {
                    mock<TransactionRepository> { }
                }
            }
        )

        val useCase by inject<CreateTransaction>()
        val response = useCase.with(transaction).execute()
        assertNotNull(response.account.activeCard)
        assertNotNull(response.account.availableLimit)

        assert(response.violations.isNotEmpty())
        assertEquals(response.violations.first(), Violation.InsufficientLimit)
    }

    @Test
    fun testCreateTransactionValidAccountMultipleViolations() {
        loadKoinModules(
            module {
                single {
                    mock<AccountRepository> {
                        on { containsAccount() } doReturn true
                        on { getCurrentAccount() } doReturn inactiveCardLowLimit
                        on { updateCurrentAccount(any()) } doReturn true
                    }
                }
                single {
                    mock<TransactionRepository> { }
                }
            }
        )

        val useCase by inject<CreateTransaction>()
        val response = useCase.with(transaction).execute()
        assertNotNull(response.account.activeCard)
        assertNotNull(response.account.availableLimit)

        assert(response.violations.isNotEmpty())
        assertEquals(response.violations.size, 2)
        assertEquals(response.violations[0], Violation.CardNotActive)
        assertEquals(response.violations[1], Violation.InsufficientLimit)
    }

}