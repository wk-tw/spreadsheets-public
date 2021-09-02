package com.wck.spreadsheets.infrastructure

import org.apache.poi.ss.usermodel.CellType.NUMERIC
import org.apache.poi.ss.usermodel.CellType.STRING
import org.apache.poi.xssf.usermodel.XSSFDataFormat
import org.apache.poi.xssf.usermodel.XSSFRow
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThatThrownBy
import org.javamoney.moneta.Money
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.util.*

internal class CellMapperKtTest {
    private lateinit var workbook: XSSFWorkbook
    private lateinit var sheet: XSSFSheet
    private lateinit var row: XSSFRow
    private lateinit var dataFormat: XSSFDataFormat

    @BeforeEach
    fun setUp() {
        workbook = XSSFWorkbook()
        dataFormat = workbook.createDataFormat()
        sheet = workbook.createSheet("SHEET_1")
        row = sheet.createRow(sheet.lastRowNum + 1)
    }

    @Test
    fun `toUUID, string field, should map to UUID`() {
        val uuid = UUID.fromString("8ed9fd1c-923c-4bff-8c1f-e065b3c67085")
        val cell = row.createCell(0, STRING).apply { setCellValue(uuid.toString()) }

        assertThat(cell.toUUID()).isEqualTo(uuid)
    }

    @Test
    fun `toLocalDate, numeric field, should map to LocalDate`() {
        val localDate = LocalDate.parse("2021-09-15")
        val cell = row.createCell(0, NUMERIC).apply { setCellValue(localDate) }

        assertThat(cell.toLocalDate()).isEqualTo(localDate)
    }

    @Test
    fun `toLocalDate, string field, should throw`() {
        val localDate = LocalDate.parse("2021-09-15")
        val cell = row.createCell(0, STRING).apply { setCellValue(localDate.toString()) }

        assertThatThrownBy { cell.toLocalDate() }
            .isInstanceOf(IllegalStateException::class.java)
    }

    @Test
    fun `toMoney, numeric field, should map to Money`() {
        val money = Money.of(20_000_000, "EUR")
        val cell = row.createCell(0, NUMERIC).apply {
            cellStyle.dataFormat = CurrencyUtils.getMoneyFormat(money.currency.currencyCode).let(dataFormat::getFormat)
            setCellValue(money.number.toDouble())
        }

        assertThat(cell.toMoney()).isEqualTo(money)
    }

    @Test
    fun `toMoney, string field, should throw`() {
        val money = Money.of(20_000_000, "EUR")
        val cell = row.createCell(0, STRING).apply {
            cellStyle.dataFormat = CurrencyUtils.getMoneyFormat(money.currency.currencyCode).let(dataFormat::getFormat)
            setCellValue(money.number.toString())
        }

        assertThatThrownBy { cell.toMoney() }
            .isInstanceOf(IllegalStateException::class.java)
    }
}
