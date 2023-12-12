package com.work.warehousemanager.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtils {
    private DateFormatUtils() {}

    public static String SimpleDateFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = simpleDateFormat.format(new Date());
        return date;
    }
}
