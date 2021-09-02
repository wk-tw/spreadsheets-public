package com.wck.spreadsheets.infrastructure

import java.time.LocalDate
import javax.money.MonetaryAmount

data class Player(
    val id: String,
    val firstName: String,
    val lastName: String,
    val dateOfBirth: LocalDate,
    val teamName: String,
    val number: Int,
    val salary: MonetaryAmount
)
