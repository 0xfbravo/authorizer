package domain.usecases.utils

import core.*
import domain.model.Account
import domain.model.Violation
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import kotlin.test.assertFailsWith

class SendResponseTest: KoinTest {

    private val useCase by inject<SendResponse>()
    private val account = Account(true, 199)
    private val violations = listOf<Violation>()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(utils, dataLayer, domainLayer, presentationLayer)
    }

    @Test
    fun testNullAccount() {
        assertFailsWith(AccountCantBeNull::class) { useCase.execute() }
    }

    @Test
    fun testSendResponseValidAccountNoViolations() {
        val response = useCase.with(account, violations).execute()
        println(response)
    }

    @Test
    fun testSendResponseValidAccountNullViolations() {
        val response = useCase.with(account, null).execute()
        println(response)
    }

    @Test
    fun testSendResponseValidAccountWithViolations() {
        val response = useCase.with(account, listOf(Violation.HighFrequencySmallInterval, Violation.DoubleTransaction)).execute()
        println(response)
    }

    @Test
    fun testSendResponseInvalidAccount() {
        val response = useCase.with(Account(), listOf(Violation.AccountNotInitialized)).execute()
        println(response)
    }

}