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
import domain.usecases.*
import org.koin.dsl.module
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
    // Use cases
    single { CreateAccount(get()) }
    single { CreateTransaction(get(), get(), get(), get(), get(), get(), get()) }
    single { GetCurrentAccount(get()) }
    single { GetLastTransactions() }
    single { ReadRequest(get()) }
    single { SendResponse(get()) }
    single { ValidateAccountInitialization(get()) }
    single { ValidateCardActivation() }
    single { ValidateCardLimit() }
    single { ValidateDoubleTransaction() }
    single { ValidateHighFrequencySmallInterval() }
}

val presentationLayer = module {

}