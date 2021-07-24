package domain.usecases.validator

import data.repository.AccountRepository
import domain.model.Violation
import domain.usecases.UseCase

class ValidateAccountInitialization(private val repository: AccountRepository): UseCase<Violation?> {

    override fun execute(): Violation? {
        val isAccountCreated = repository.containsAccount()
        if (!isAccountCreated) {
            return Violation.AccountNotInitialized
        }
        return null
    }

}