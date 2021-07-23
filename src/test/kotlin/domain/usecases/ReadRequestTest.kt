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
    fun testInvalidJson() {
        val request = useCase.with("{}").execute()
        assertNull(request?.account)
        assertNull(request?.transaction)
    }

    @Test
    fun testAccountJson() {
        val request = useCase
            .with("{\"account\": {\"active-card\": true, \"available-limit\": 100}}")
            .execute()
        assertNotNull(request?.account)
        assertNotNull(request?.account?.activeCard)
        assertNotNull(request?.account?.availableLimit)
    }

    @Test
    fun testTransactionJson() {
        val request = useCase
            .with("{\"transaction\": {\"merchant\": \"Burger King\", \"amount\": 20, \"time\": \"2019-02-13T10:00:00.000Z\"}}")
            .execute()
        assertNotNull(request?.transaction?.merchant)
        assertNotNull(request?.transaction?.amount)
        assertNotNull(request?.transaction?.time)
        assertEquals(request!!.transaction!!.merchant, "Burger King")
        assertEquals(request.transaction!!.amount, 20)
        assertEquals(request.transaction!!.time, "2019-02-13T10:00:00.000Z")
    }

}