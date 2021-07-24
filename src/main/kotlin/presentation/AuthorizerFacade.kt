package presentation

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AuthorizerFacade(private val userInputSequence: Sequence<String>): KoinComponent {
    private val inputHandler by inject<InputHandler>()

    fun start() {
        userInputSequence.forEach {
            inputHandler.readUserInput(it)
        }
    }

}