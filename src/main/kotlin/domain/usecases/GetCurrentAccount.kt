package domain.usecases

import data.repository.AccountRepository
import domain.AccountNotInitialized
import domain.model.Account

class GetCurrentAccount(private val repository: AccountRepository): UseCase<Account> {

    override fun execute(): Account {
        return repository.getCurrentAccount() ?: throw AccountNotInitialized()
    }

}