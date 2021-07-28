package presentation

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AuthorizerFacade: KoinComponent {
    private val inputHandler by inject<InputHandler>()

    fun process(operations: Sequence<String>) {
        operations.forEach {
            inputHandler.readUserInput(it)
        }
    }

}