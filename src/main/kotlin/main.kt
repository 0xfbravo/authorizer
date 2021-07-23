import core.dataLayer
import core.domainLayer
import core.presentationLayer
import core.utils
import org.koin.core.context.startKoin

fun main(args: Array<String>) {
    // Setups dependency injection
    startKoin {
        // Koin Logger
        printLogger()
        // Modules
        modules(utils, dataLayer, domainLayer, presentationLayer)
    }

    // Does the magic! ~
    println("Hello World!")
}