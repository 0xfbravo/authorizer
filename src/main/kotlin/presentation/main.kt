package presentation

import core.dataLayer
import core.domainLayer
import core.presentationLayer
import core.utils
import org.koin.core.context.startKoin

fun main(args: Array<String>) {
    // Setups dependency injection
    startKoin {
        modules(utils, dataLayer, domainLayer, presentationLayer)
    }

    // Does the magic! ~
    val authorizer = AuthorizerFacade()
    val operations = generateSequence(::readLine)
    authorizer.process(operations)
}