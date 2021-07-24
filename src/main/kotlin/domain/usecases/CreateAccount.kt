package domain.usecases

import data.repository.AccountRepository
import domain.AccountAlreadyInitialized
import domain.AccountCantBeNull
import domain.model.Account

class CreateAccount(private val repository: AccountRepository): UseCase<Unit> {
    private var account: Account? = null

    override fun execute() {
        if (account == null) {
            throw AccountCantBeNull()
        }

        val isAccountCreated = repository.addAccount(account!!)
        if (!isAccountCreated) {
            throw AccountAlreadyInitialized()
        }
    }

    fun with(account: Account): CreateAccount {
        this.account = account
        return this
    }

}