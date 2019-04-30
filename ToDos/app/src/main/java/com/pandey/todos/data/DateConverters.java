package com.pandey.todos.data;
import java.util.Date;

import androidx.room.TypeConverter;


public class DateConverters
{
    @TypeConverter
    public static Date toDate(Long timeStamp)
    {
        return timeStamp == null ? null : new Date(timeStamp);
    }

    @TypeConverter
    public static Long toTimeStamp(Date date)
    {
        return date == null ? null : date.getTime();
    }
}