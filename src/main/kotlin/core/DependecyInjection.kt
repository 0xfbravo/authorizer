package core

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import data.datasource.AccountDatasource
import data.datasource.AccountDatasourceImpl
import data.datasource.TransactionDatasource
import data.datasource.TransactionDatasourceImpl
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
    single<AccountDatasource> { AccountDatasourceImpl() }
    single<TransactionDatasource> { TransactionDatasourceImpl() }

    // Repository
    single<AccountRepository> { AccountRepositoryImpl() }
    single<TransactionRepository> { TransactionRepositoryImpl() }
}

val domainLayer = module {
    // Use cases
    single { CreateAccount() }
    single { CreateTransaction() }
    single { GetLastTransactions() }
    single { ReadRequest(get()) }
    single { ValidateAccountInitialization() }
    single { ValidateCardActivation() }
    single { ValidateCardLimit() }
    single { ValidateDoubleTransaction() }
    single { ValidateHighFrequency() }
}

val presentationLayer = module {

}