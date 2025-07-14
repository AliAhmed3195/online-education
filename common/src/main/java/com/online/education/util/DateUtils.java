package com.online.education.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class DateUtils {

    public static final String DATE_FORMAT_1 = "dd-MM-yyyy";

    public static String dateToString(Date dtDate, String format) {
        try {
            SimpleDateFormat dateFormatter = new SimpleDateFormat(Objects.isNull(format) || format.isEmpty() ? DATE_FORMAT_1 : format);
            String sDate = dateFormatter.format(dtDate);
            return sDate;
        } catch (Exception e) {
            return null;
        }
    }

    public static String getDuration(Date startDate, Date endDate){

        long time_difference = endDate.getTime() - startDate.getTime();

        long seconds_difference = (time_difference / 1000)% 60;

        long minutes_difference = (time_difference / (1000*60)) % 60;

        long hours_difference = (time_difference / (1000*60*60)) % 24;

        return String.format("%02d",hours_difference) +":"+ String.format("%02d",minutes_difference)+":"+String.format("%02d",seconds_difference);
    }
}
