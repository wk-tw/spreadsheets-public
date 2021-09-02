package com.wck.spreadsheets.infrastructure

import org.apache.poi.ss.usermodel.Row

fun Row.toPlayer(): Player = Player(
    id = getCell(0).stringCellValue.toString(),
    firstName = getCell(1).stringCellValue,
    lastName = getCell(2).stringCellValue,
    dateOfBirth = getCell(3).toLocalDate(),
    teamName = getCell(4).stringCellValue,
    number = getCell(5).numericCellValue.toInt(),
    salary = getCell(6).toMoney(),
)


