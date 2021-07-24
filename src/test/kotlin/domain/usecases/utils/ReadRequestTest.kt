package domain.usecases.utils

import core.dataLayer
import core.domainLayer
import core.presentationLayer
import core.utils
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import java.time.LocalDateTime

class ReadRequestTest: KoinTest {

    private val useCase by inject<ReadRequest>()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(utils, dataLayer, domainLayer, presentationLayer)
    }

    @Test
    fun testEmptyString() {
        assertNull(useCase.with("").execute())
    }

    @Test
    fun testNullString() {
        assertNull(useCase.execute())
    }

    @Test
    fun testValidJsonEmptyParameters() {
        val request = useCase.with("{}").execute()
        assertNotNull(request)
        assertNull(request?.account)
        assertNull(request?.transaction)
    }

    @Test
    fun testInvalidJson() {
        val request = useCase.with("{").execute()
        assertNull(request)
        assertNull(request?.account)
        assertNull(request?.transaction)
    }

    @Test
    fun testAccountJson() {
        val request = useCase
            .with("{\"account\": {\"active-card\": true, \"available-limit\": 100}}")
            .execute()

        val account = request!!.account
        assertNotNull(account)
        assertNotNull(account?.activeCard)
        assertNotNull(account?.availableLimit)
    }

    @Test
    fun testTransactionJson() {
        val request = useCase
            .with("{\"transaction\": {\"merchant\": \"Burger King\", \"amount\": 20, \"time\": \"2019-02-13T10:00:00.000Z\"}}")
            .execute()

        val transaction = request!!.transaction
        assertNotNull(transaction)
        assertNotNull(transaction?.merchant)
        assertNotNull(transaction?.merchant)
        assertNotNull(transaction?.amount)
        assertNotNull(transaction?.time)
        assertEquals(transaction?.merchant, "Burger King")
        assertEquals(transaction?.amount, 20)
        assertEquals(transaction?.time, LocalDateTime.of(2019, 2, 13,10, 0, 0, 0))
    }

}