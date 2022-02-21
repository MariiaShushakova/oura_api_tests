package com.oura.ring.api.constants;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateStamp {
    static LocalDate today = LocalDate.now();
    public static final String DATE_TODAY = today.toString();
    public static final String DATE_TOMORROW = today.plusDays(1).toString();

    static LocalDateTime todayDateTime = LocalDateTime.now();
    static DateTimeFormatter FORMATTER_DATE_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    public static final String DATE_TIME_TODAY = todayDateTime.format(FORMATTER_DATE_TIME);
    public static final String DATE_TIME_TOMORROW = todayDateTime.plusDays(1).format(FORMATTER_DATE_TIME);

    static DateTimeFormatter FORMATTER_DATE_TIME2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss+00:00"); //without T
    public static final String DATE_TIME_TODAY2 = todayDateTime.format(FORMATTER_DATE_TIME2);
    public static final String DATE_TIME_TOMORROW2 = todayDateTime.plusDays(1).format(FORMATTER_DATE_TIME2);

//    public static void main(String[] args) {
//        System.out.println(DATE_TIME_TODAY2);
//        System.out.println(DATE_TIME_TOMORROW2);
//    }
}
