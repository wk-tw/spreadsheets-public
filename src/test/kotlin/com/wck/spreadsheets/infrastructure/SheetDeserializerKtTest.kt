package com.wck.spreadsheets.infrastructure

import org.apache.poi.ss.usermodel.DataFormat
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.javamoney.moneta.Money
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class SheetDeserializerKtTest {

    private val workbook: Workbook = XSSFWorkbook()
    private val worksheet: Sheet = workbook.createSheet()
    private val dataFormat: DataFormat = workbook.createDataFormat()

    private companion object {
        val kevinDurant: Player = Player(
            id = "b4528a7e-0fae-428f-9b48-7916d7d70e14",
            firstName = "Kevin",
            lastName = "DURANT",
            dateOfBirth = LocalDate.parse("1988-09-29"),
            teamName = "Nets",
            number = 35,
            salary = Money.of(40_920_000, "USD")
        )

        val rudyGobert: Player = Player(
            id = "5d67d51e-227a-4e98-86e0-1a4492713d95",
            firstName = "Rudy",
            lastName = "GOBERT",
            dateOfBirth = LocalDate.parse("1992-06-26"),
            teamName = "Jazz",
            number = 27,
            salary = Money.of(22_420_000, "EUR")
        )
    }

    @BeforeEach
    fun setUp() {
        worksheet.createRow(0)
    }

    @Test
    fun `deserialize, 2 players, should deserialized into list of players`() {
        insertPlayerIntoSheet(kevinDurant)
        insertPlayerIntoSheet(rudyGobert)

        val players = worksheet.deserialize(Row::toPlayer)
        assertThat(players.size).isEqualTo(2)
        assertThat(players.map(Player::id)).containsExactly(kevinDurant.id, rudyGobert.id)
    }

    /**
     * Insert into a new row the [player].
     */
    private fun insertPlayerIntoSheet(player: Player) = worksheet.createRow(worksheet.lastRowNum + 1).apply {
        with(player) {
            createCell(0).setCellValue(id)
            createCell(1).setCellValue(firstName)
            createCell(2).setCellValue(lastName)
            createCell(3).setCellValue(dateOfBirth)
            createCell(4).setCellValue(teamName)
            createCell(5).setCellValue(number.toDouble())
            createCell(6).run {
                setCellValue(salary.number.toDouble())
                cellStyle.dataFormat = CurrencyUtils.getMoneyFormat(salary.currency.currencyCode)
                    .let(dataFormat::getFormat)
            }
        }
    }
}
