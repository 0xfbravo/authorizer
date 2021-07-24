package core

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import data.datasource.AccountDataSource
import data.datasource.AccountDataSourceImpl
import data.datasource.TransactionDataSource
import data.datasource.TransactionDataSourceImpl
import data.repository.AccountRepository
import data.repository.AccountRepositoryImpl
import data.repository.TransactionRepository
import data.repository.TransactionRepositoryImpl
import domain.usecases.account.CreateAccount
import domain.usecases.account.GetCurrentAccount
import domain.usecases.account.UpdateCurrentAccount
import domain.usecases.transaction.CreateTransaction
import domain.usecases.transaction.GetTransactions
import domain.usecases.utils.ConvertResponse
import domain.usecases.utils.ReadRequest
import domain.usecases.validator.*
import org.koin.dsl.module
import presentation.InputHandler
import presentation.InputHandlerImpl
import java.time.LocalDateTime

val utils = module {
    single<Gson> { GsonBuilder()
        .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeHandler())
        .create()
    }
}

val dataLayer = module {
    // Datasource
    single<AccountDataSource> { AccountDataSourceImpl() }
    single<TransactionDataSource> { TransactionDataSourceImpl() }

    // Repository
    single<AccountRepository> { AccountRepositoryImpl(get()) }
    single<TransactionRepository> { TransactionRepositoryImpl(get()) }
}

val domainLayer = module {
    // Account use cases
    single { CreateAccount(get()) }
    single { GetCurrentAccount(get()) }
    single { UpdateCurrentAccount(get()) }
    // Transaction use cases
    single { CreateTransaction(get(), get(), get(), get(), get(), get(), get(), get()) }
    single { GetTransactions(get()) }
    // Validator use cases
    single { ValidateAccountInitialization(get()) }
    single { ValidateCardActivation() }
    single { ValidateCardLimit() }
    single { ValidateDoubledTransaction(get()) }
    single { ValidateHighFrequencySmallInterval(get()) }
    // Utils
    single { ReadRequest(get()) }
    single { ConvertResponse(get()) }
}

val presentationLayer = module {
    single<InputHandler> { InputHandlerImpl(get(), get(), get(), get()) }
}