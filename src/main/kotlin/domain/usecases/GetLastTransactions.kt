package domain.usecases

import domain.model.Transaction

class GetLastTransactions: UseCase<List<Transaction>> {
    override fun execute(): List<Transaction> {
        TODO("Not yet implemented")
    }
}