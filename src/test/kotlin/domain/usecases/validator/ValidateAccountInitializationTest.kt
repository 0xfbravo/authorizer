package domain.usecases.validator

import data.repository.AccountRepository
import domain.model.Violation
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class ValidateAccountInitializationTest {

    @Test
    fun testAccountNotInitialized() {
        val repository = mock<AccountRepository> { on { containsAccount() } doReturn false }
        val useCase = ValidateAccountInitialization(repository)
        val violation = useCase.execute()
        assertNotNull(violation)
        Assert.assertEquals(violation, Violation.AccountNotInitialized)
    }

    @Test
    fun testAccountInitialized() {
        val repository = mock<AccountRepository> { on { containsAccount() } doReturn true }
        val useCase = ValidateAccountInitialization(repository)
        val violation = useCase.execute()
        assertNull(violation)
    }

}