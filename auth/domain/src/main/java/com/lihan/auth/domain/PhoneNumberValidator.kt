package com.lihan.auth.domain

object PhoneNumberValidator {

    private val TAIWAN_PHONE_REGEX = Regex("^(?:\\+886|0)9\\d{8}\$")

    fun isValidTaiwanPhone(phone: String): Boolean {
        // 先移除所有空白、破折號與括號
        val cleanPhone = phone.replace(Regex("[\\s-]"), "")
        return cleanPhone.matches(TAIWAN_PHONE_REGEX)
    }
}