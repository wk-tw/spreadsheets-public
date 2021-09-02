package com.wck.spreadsheets.infrastructure

object CurrencyUtils {
    private val moneyFormatCurrencyCodeAssociation = mapOf(
        "[$$-409] #,##0.00" to "USD",
        "[$€-2] #,##0.00" to "EUR"
    )

    fun getCurrencyCode(dataFormat: String) = moneyFormatCurrencyCodeAssociation[dataFormat]
        ?: throw IllegalArgumentException("Unable to retrieve currency code for data format $dataFormat.")

    fun getMoneyFormat(currencyCode: String): String = moneyFormatCurrencyCodeAssociation
        .filterValues { it == currencyCode }
        .keys.firstOrNull()
        ?: throw IllegalArgumentException("Unable to retrieve money format from currency code $currencyCode.")
}
