package presentation

import core.dataLayer
import core.domainLayer
import core.presentationLayer
import core.utils
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject

class InputHandlerImplTest: KoinTest {

    private val handler by inject<InputHandler>()
    private val account = "{\"account\": {\"active-card\": true, \"available-limit\": 100}}"
    private val transaction = "{\"transaction\": {\"merchant\": \"Burger King\", \"amount\": 20, \"time\": \"2019-02-13T10:00:00.000Z\"}}"

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(utils, dataLayer, domainLayer, presentationLayer)
    }

    @Test
    fun testWithAccountInput() {
        handler.readUserInput(account)
    }

    @Test
    fun testWithTransactionInput() {
        handler.readUserInput(transaction)
    }

    @Test
    fun testInvalidInput() {
        handler.readUserInput("")
    }

}