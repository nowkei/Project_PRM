package com.example.project_prm.util;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static Date convertDateFromString(String date) {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            return new Date();
        }
    }

    public static String getDateTimeOrTime(String date) {
        try {
            Date convertedDate = convertDateFromString(date);
            Date currentDate = new Date();
            String dateShow = "";

            Calendar calendar = Calendar.getInstance();
            DateFormat secondFormatter = new SimpleDateFormat("dd/MM/yy");
            Date secondConvertedDate = secondFormatter.parse(date);

            calendar.setTime(currentDate);
            int currentDay = calendar.get(Calendar.DATE);
            int currentMonth = calendar.get(Calendar.MONTH);
            int currentYear = calendar.get(Calendar.YEAR);

            calendar.setTime(secondConvertedDate);
            int convertedDay = calendar.get(Calendar.DATE);
            int convertedMonth = calendar.get(Calendar.MONTH);
            int convertedYear = calendar.get(Calendar.YEAR);

            if (currentDay != convertedDay
                    && currentMonth != convertedMonth
                    && currentYear != convertedYear) {
                dateShow = secondFormatter.format(secondConvertedDate);
            }

            DateFormat formatter = new SimpleDateFormat("HH:mm");
            String time = formatter.format(convertedDate);
            return dateShow + " " + time;
        } catch (ParseException e) {
            return "";
        }
    }

    public static String getCurrentDate() {
        Date currentDate = new Date();
        DateFormat secondFormatter = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        String currentDateString = secondFormatter.format(currentDate);
        return currentDateString;
    }
}
