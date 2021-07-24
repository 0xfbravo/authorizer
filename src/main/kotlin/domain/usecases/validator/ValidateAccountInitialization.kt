package domain.usecases.validator

import data.repository.AccountRepository
import domain.AccountNotInitialized
import domain.usecases.UseCase

class ValidateAccountInitialization(private val repository: AccountRepository): UseCase<Unit> {

    override fun execute() {
        val isAccountCreated = repository.containsAccount()
        if (!isAccountCreated) {
            throw AccountNotInitialized()
        }
    }

}