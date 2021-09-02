package com.wck.spreadsheets.filehandler

import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThatThrownBy
import org.junit.jupiter.api.Test

import org.springframework.core.io.ClassPathResource

internal class XSSFFileHandlerTest {
    private companion object {
        val fileHandler = XSSFFileHandler(ClassPathResource("buffer.xlsx").file.path)
    }

    @Test
    fun `init, bad file path, should throw`() {
        assertThatThrownBy { XSSFFileHandler("/unknown_file_path") }
            .isInstanceOf(IllegalStateException::class.java)
    }

    @Test
    fun `getWorkbook, should return workbook`() {
        assertThat(fileHandler.workbook).isNotNull
    }

    @Test
    fun `getSheetByName, Players, should return sheet`() {
        assertThat(fileHandler.getSheetByName("Players")).isNotNull
    }

    @Test
    fun `getSheetByName, UNKNOWN, should throw`() {
        assertThatThrownBy { fileHandler.getSheetByName("UNKNOWN") }
            .isInstanceOf(IllegalStateException::class.java)
    }
}
