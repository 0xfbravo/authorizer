package domain.usecases

import data.repository.AccountRepository
import domain.AccountAlreadyInitialized
import domain.AccountCantBeNull
import domain.model.Account

class CreateAccount(private val repository: AccountRepository): UseCase<Boolean> {
    private var account: Account? = null

    override fun execute(): Boolean {
        if (account == null) {
            throw AccountCantBeNull()
        }

        val accountWasAdded = repository.addAccount(account!!)
        if (!accountWasAdded) {
            throw AccountAlreadyInitialized()
        }
        return accountWasAdded
    }

    fun with(account: Account): CreateAccount {
        this.account = account
        return this
    }

}