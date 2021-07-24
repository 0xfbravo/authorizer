package domain.usecases.utils

import core.dataLayer
import core.domainLayer
import core.presentationLayer
import core.utils
import domain.model.Account
import domain.model.Response
import domain.model.Violation
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject

class ConvertResponseTest: KoinTest {

    private val useCase by inject<ConvertResponse>()
    private val account = Account(true, 199)
    private val violations = listOf<Violation>()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(utils, dataLayer, domainLayer, presentationLayer)
    }

    @Test
    fun testSendResponseValidAccountNoViolations() {
        val response = Response(account, violations)
        val responseJson = useCase.with(response).execute()
        println(responseJson)
    }

    @Test
    fun testSendResponseValidAccountNullViolations() {
        val response = Response(account, listOf())
        val responseJson = useCase.with(response).execute()
        println(responseJson)
    }

    @Test
    fun testSendResponseValidAccountWithViolations() {
        val response = Response(account, listOf(Violation.HighFrequencySmallInterval, Violation.DoubleTransaction))
        val responseJson = useCase.with(response).execute()
        println(responseJson)
    }

    @Test
    fun testSendResponseInvalidAccount() {
        val response = Response(Account(), listOf(Violation.AccountNotInitialized))
        val responseJson = useCase.with(response).execute()
        println(responseJson)
    }

}