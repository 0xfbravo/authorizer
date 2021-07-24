package domain.usecases.utils

import com.google.gson.GsonBuilder
import core.LocalDateTimeHandler
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

class ReadRequestTest {

    private val gson = GsonBuilder().registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeHandler()).create()
    private lateinit var useCase: ReadRequest

    @Before
    fun init() {
       useCase = ReadRequest(gson)
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