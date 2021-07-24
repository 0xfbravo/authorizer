package domain.usecases

import data.repository.AccountRepository
import domain.AccountNotInitialized

class ValidateAccountInitialization(private val repository: AccountRepository): UseCase<Unit> {

    override fun execute() {
        val isAccountCreated = repository.containsAccount()
        if (!isAccountCreated) {
            throw AccountNotInitialized()
        }
    }

}