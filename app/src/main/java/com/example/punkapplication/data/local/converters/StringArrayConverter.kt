package com.example.punkapplication.data.local.converters

import androidx.room.TypeConverter

class StringArrayConverter {
    @TypeConverter
    fun fromArrayToString(array: List<String>): String {
        var convertedString = ""
        for (string in array)
            convertedString += "$string|"

        return convertedString
    }
    @TypeConverter
    fun fromStringToArray(convertedString: String): List<String> {
        return convertedString.split("|", ignoreCase = true).toList()
    }
}