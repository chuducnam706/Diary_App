package com.example;

import androidx.room.TypeConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConverLocaleDateTime {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @TypeConverter
    public static String fromLocalDateTime(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        return dateTime.format(formatter);
    }

    @TypeConverter
    public static LocalDateTime toLocalDateTime(String dateTimeString) {
        if (dateTimeString == null) return null;
        return LocalDateTime.parse(dateTimeString, formatter);
    }
}
