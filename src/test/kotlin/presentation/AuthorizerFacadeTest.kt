package presentation

import core.dataLayer
import core.domainLayer
import core.presentationLayer
import core.utils
import org.junit.Rule
import org.junit.Test
import org.koin.core.component.KoinComponent
import org.koin.test.KoinTestRule

class AuthorizerFacadeTest: KoinComponent {

    private val facade = AuthorizerFacade()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(utils, dataLayer, domainLayer, presentationLayer)
    }

    @Test
    fun testProcessMultipleOperations() {
        val operations = sequenceOf(
            "{\"account\": {\"active-card\": true, \"available-limit\": 100}}",
            "{\"transaction\": {\"merchant\": \"Burger King\", \"amount\": 20, \"time\": \"2019-02-13T10:00:00.000Z\"}}",
            "{\"transaction\": {\"merchant\": \"Habbib's\", \"amount\": 90, \"time\": \"2019-02-13T11:00:00.000Z\"}}",
            "{\"transaction\": {\"merchant\": \"McDonald's\", \"amount\": 30, \"time\": \"2019-02-13T12:00:00.000Z\"}}"
        )
        facade.process(operations)
    }

    @Test
    fun testProcessEmptyLines() {
        facade.process(sequenceOf())
    }

}