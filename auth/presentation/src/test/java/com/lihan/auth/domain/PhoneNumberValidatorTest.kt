package com.lihan.auth.domain

import org.junit.Assert.*
import org.junit.Test

class PhoneNumberValidatorTest {
    @Test
    fun isValidTaiwanPhone() {
        assertTrue(PhoneNumberValidator.isValidTaiwanPhone("0912345678"))
        assertTrue(PhoneNumberValidator.isValidTaiwanPhone("+886912345678"))
        assertFalse(PhoneNumberValidator.isValidTaiwanPhone("0123456789"))
        assertFalse(PhoneNumberValidator.isValidTaiwanPhone("1234567890"))
    }

}