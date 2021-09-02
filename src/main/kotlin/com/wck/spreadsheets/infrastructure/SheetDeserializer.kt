package com.wck.spreadsheets.infrastructure

import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet

/**
 * Deserialize a workbook [Sheet] into a list of object [T]
 */
inline fun <reified T> Sheet.deserialize(mapper: (Row) -> T): List<T> =
    mapIndexedNotNull { index, row -> if (index == 0) null else mapper.invoke(row) }

