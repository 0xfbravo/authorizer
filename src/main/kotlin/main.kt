import core.dataLayer
import core.domainLayer
import core.presentationLayer
import core.utils
import org.koin.core.context.startKoin
import presentation.AuthorizerFacade

fun main(args: Array<String>) {
    // Setups dependency injection
    startKoin {
        modules(utils, dataLayer, domainLayer, presentationLayer)
    }

    // Does the magic! ~
    val operations = generateSequence(::readLine)
    val authorizer = AuthorizerFacade(operations)
    authorizer.start()
}