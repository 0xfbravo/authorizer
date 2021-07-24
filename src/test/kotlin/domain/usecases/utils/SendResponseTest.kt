package domain.usecases.utils

import com.google.gson.GsonBuilder
import core.AccountCantBeNull
import core.LocalDateTimeHandler
import domain.model.Account
import domain.model.Violation
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime
import kotlin.test.assertFailsWith

class SendResponseTest {

    private val gson = GsonBuilder().registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeHandler()).create()
    private lateinit var useCase: SendResponse
    private val account = Account(true, 199)
    private val violations = listOf<Violation>()

    @Before
    fun init() {
        useCase = SendResponse(gson)
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