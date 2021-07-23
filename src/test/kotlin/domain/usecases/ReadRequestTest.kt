package domain.usecases

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ReadRequestTest {

    private lateinit var useCase: ReadRequest

    @Before
    fun init() {
       useCase = ReadRequest()
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
        assertEquals(transaction?.time, "2019-02-13T10:00:00.000Z")
    }

}