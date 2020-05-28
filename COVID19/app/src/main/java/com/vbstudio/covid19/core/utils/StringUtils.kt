package com.vbstudio.covid19.core.utils

import java.text.SimpleDateFormat

class StringUtils {
    companion object {
        fun formatNumberString(string: String, withDecorator: Boolean): String {
            val commaSplitterLength = 3;
            var commasInserted = 0
            var updatedString = string
            var commasToInsert =
                string.length / commaSplitterLength - (if (string.length % commaSplitterLength == 0) 1 else 0)

            while (commasToInsert != 0) {
                val splitIndex =
                    updatedString.length - (commaSplitterLength * (commasInserted + 1)) - commasInserted;
                val stringPart1 = updatedString.substring(
                    0,
                    splitIndex
                )
                val stringPart2 = updatedString.substring(
                    splitIndex,
                    updatedString.length
                )
                updatedString = stringPart1.plus(",").plus(stringPart2)
                commasToInsert--
                commasInserted++
            }

            return if (withDecorator) "{ $updatedString }" else updatedString
        }

        fun formatDate(inputDate: String?, dateFormatString: String): String {
            inputDate?.let {
                val inputDateFormat = SimpleDateFormat(dateFormatString)
                val outputDateFormat = SimpleDateFormat("dd MMM, hh:mm aa Z")
                val outputDate = inputDateFormat.parse(inputDate)

                outputDate?.let {
                    return outputDateFormat.format(it)
                }
            }
            return ""
        }
    }
}