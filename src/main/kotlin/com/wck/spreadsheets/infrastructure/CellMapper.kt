package com.wck.spreadsheets.infrastructure

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.DateUtil
import org.javamoney.moneta.Money
import java.time.LocalDate
import java.util.*
import javax.money.MonetaryAmount

/**
 * Map a uuid [Cell] to [UUID]
 */
fun Cell.toUUID(): UUID = UUID.fromString(stringCellValue)


/**
 * Map a date [Cell] to [LocalDate]
 */
fun Cell.toLocalDate(): LocalDate = DateUtil.getLocalDateTime(numericCellValue).toLocalDate()

/**
 * Map a money [Cell] to [MonetaryAmount]
 */
fun Cell.toMoney(): MonetaryAmount = Money.of(
    numericCellValue,
    CurrencyUtils.getCurrencyCode(cellStyle.dataFormatString)
)
