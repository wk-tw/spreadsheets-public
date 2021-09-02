package com.wck.spreadsheets.infrastructure

import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.junit.jupiter.api.Test
import org.springframework.core.io.ClassPathResource

internal class IntegrationTestKtTest {
    private companion object {
        val file = ClassPathResource("buffer.xlsx").file
        val workbook: Workbook = WorkbookFactory.create(file)
    }

    @Test
    fun `deserialize should log sheets name`() {
        workbook.first().deserialize(Row::toPlayer).forEach(::println)
    }
}
