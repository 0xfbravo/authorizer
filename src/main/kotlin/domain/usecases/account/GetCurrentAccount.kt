package domain.usecases.account

import data.repository.AccountRepository
import domain.model.Account
import domain.usecases.UseCase

class GetCurrentAccount(private val repository: AccountRepository): UseCase<Account?> {

    override fun execute(): Account? {
        return repository.getCurrentAccount()
    }

}