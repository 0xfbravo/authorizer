package domain.usecases.account

import data.repository.AccountRepository
import domain.AccountAlreadyInitialized
import domain.AccountCantBeNull
import domain.model.Account
import domain.usecases.UseCase

class CreateAccount(private val repository: AccountRepository): UseCase<Unit> {
    private var account: Account? = null

    override fun execute() {
        if (account == null) {
            throw AccountCantBeNull()
        }

        val isAccountCreated = repository.addAccount(account!!)
        if (!isAccountCreated) {
            val currentAccount = repository.getCurrentAccount()!!
            throw AccountAlreadyInitialized(currentAccount)
        }
    }

    fun with(account: Account): CreateAccount {
        this.account = account
        return this
    }

}