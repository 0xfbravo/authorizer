package presentation

import domain.model.Account
import domain.model.Response
import domain.model.Transaction
import domain.usecases.account.CreateAccount
import domain.usecases.transaction.CreateTransaction
import domain.usecases.utils.ConvertResponse
import domain.usecases.utils.ReadRequest

interface InputHandler {
    fun readUserInput(userInput: String)
}

class InputHandlerImpl(private val readRequest: ReadRequest,
                       private val convertResponse: ConvertResponse,
                       private val createAccount: CreateAccount,
                       private val createTransaction: CreateTransaction): InputHandler {

    override fun readUserInput(userInput: String) {
        val request = readRequest.with(userInput).execute()
        when {
            request?.account != null -> { startAccountCreation(request.account) }
            request?.transaction != null -> { startTransactionCreation(request.transaction) }
            else -> { }
        }
    }

    private fun startAccountCreation(account: Account) {
        val response = createAccount.with(account).execute()
        showResponseToUser(response)
    }

    private fun startTransactionCreation(transaction: Transaction) {
        val response = createTransaction.with(transaction).execute()
        showResponseToUser(response)
    }

    private fun showResponseToUser(response: Response) {
        val responseString = convertResponse.with(response).execute()
        println(responseString)
    }

}