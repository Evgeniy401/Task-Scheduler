package com.example.data.storage

import androidx.room.TypeConverter

enum class PriorityData {
    STANDARD, HIGH, MAXIMUM, NONE
}

class PriorityDataConverter {

    @TypeConverter
    fun fromPriorityData(priority: PriorityData): String {
        return priority.name
    }

    @TypeConverter
    fun toPriorityData(priorityString: String): PriorityData {
        return PriorityData.valueOf(priorityString)
    }
}
