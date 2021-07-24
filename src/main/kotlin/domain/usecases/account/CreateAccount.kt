package domain.usecases.account

import core.AccountCantBeNull
import data.repository.AccountRepository
import domain.model.Account
import domain.model.Violation
import domain.usecases.UseCase

class CreateAccount(private val repository: AccountRepository): UseCase<Violation?> {
    private var account: Account? = null

    override fun execute(): Violation? {
        if (account == null) {
            throw AccountCantBeNull()
        }

        val isAccountCreated = repository.addAccount(account!!)
        if (!isAccountCreated) {
            val currentAccount = repository.getCurrentAccount()!!
            return Violation.AccountAlreadyInitialized
        }

        return null
    }

    fun with(account: Account): CreateAccount {
        this.account = account
        return this
    }

}