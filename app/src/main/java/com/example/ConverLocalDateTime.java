package com.example;

import androidx.room.TypeConverter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class ConverLocalDateTime {

    @TypeConverter
    public static Long fromLocalDateTime(LocalDateTime dateTime) {
        return dateTime == null ? null : dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    @TypeConverter
    public static LocalDateTime toLocalDateTime(Long millis) {
        return millis == null ? null : LocalDateTime.ofEpochSecond(millis / 1000, 0, ZoneOffset.UTC);
    }
}
