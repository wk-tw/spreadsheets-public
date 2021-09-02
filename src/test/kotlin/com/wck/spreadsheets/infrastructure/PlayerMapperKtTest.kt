package com.wck.spreadsheets.infrastructure

import org.apache.poi.ss.usermodel.CellType.NUMERIC
import org.apache.poi.ss.usermodel.CellType.STRING
import org.apache.poi.xssf.usermodel.XSSFDataFormat
import org.apache.poi.xssf.usermodel.XSSFRow
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.javamoney.moneta.Money
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class PlayerMapperKtTest {
    private companion object {
        val giánnisAntetokoúnmpo = Player(
            id = "1",
            firstName = "Giánnis",
            lastName = "ANTETOKOÚNMPO",
            dateOfBirth = LocalDate.parse("1994-12-06"),
            teamName = "Bucks",
            number = 34,
            salary = Money.of(27530000.00, "USD")
        )

    }

    private lateinit var workbook: XSSFWorkbook
    private lateinit var sheet: XSSFSheet
    private lateinit var row: XSSFRow
    private lateinit var dataFormat: XSSFDataFormat

    @BeforeEach
    fun setUp() {
        workbook = XSSFWorkbook()
        dataFormat = workbook.createDataFormat()
        sheet = workbook.createSheet("SHEET_1")
    }

    @Test
    fun `toPlayer, should map all fields`() {
        row = sheet.createRow(sheet.lastRowNum + 1)
        row.apply {
            createCell(0, STRING).setCellValue(giánnisAntetokoúnmpo.id)
            createCell(1, STRING).setCellValue(giánnisAntetokoúnmpo.firstName)
            createCell(2, STRING).setCellValue(giánnisAntetokoúnmpo.lastName)
            createCell(3, NUMERIC).setCellValue(giánnisAntetokoúnmpo.dateOfBirth)
            createCell(4, STRING).setCellValue(giánnisAntetokoúnmpo.teamName)
            createCell(5, NUMERIC).setCellValue(giánnisAntetokoúnmpo.number.toDouble())
            createCell(6, NUMERIC).run {
                cellStyle.dataFormat =
                    dataFormat.getFormat(CurrencyUtils.getMoneyFormat(giánnisAntetokoúnmpo.salary.currency.currencyCode))
                setCellValue(giánnisAntetokoúnmpo.salary.number.toDouble())
            }
        }

        val player = row.toPlayer()

        assertThat(player).isEqualTo(giánnisAntetokoúnmpo)
    }
}
