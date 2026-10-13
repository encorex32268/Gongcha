package com.lihan.auth.domain

object PhoneNumberValidator {
    fun isValidTaiwanPhone(phone: String): Boolean {
        val regex = Regex("^(\\+8869\\d{8}|09\\d{8})$")
        return regex.matches(phone)
    }
}
