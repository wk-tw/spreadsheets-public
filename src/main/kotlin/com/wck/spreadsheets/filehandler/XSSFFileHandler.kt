package com.wck.spreadsheets.filehandler

import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.File

class XSSFFileHandler(private val path: String) {
    private var file: File = File(path)

    init {
        check(file.exists()) { "XSSFWorkbook file at path $path does not exist." }
    }

    val workbook: Workbook by lazy { WorkbookFactory.create(file) }

    fun getSheetByName(name: String): Sheet = workbook.getSheet(name)
        ?: throw IllegalStateException("Unable to retrieve sheet with name $name.")

}
