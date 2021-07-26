package domain.usecases.validator

import core.AccountCantBeNull
import domain.model.Account
import domain.model.Violation
import domain.usecases.UseCase

class ValidateCardActivation: UseCase<Violation?> {
    private var account: Account? = null

    override fun execute(): Violation? {
        if (account == null) {
            throw AccountCantBeNull()
        }

        if (!account!!.activeCard) {
            return Violation.CardNotActive
        }

        return null
    }

    fun with(account: Account): ValidateCardActivation {
        this.account = account
        return this
    }

}