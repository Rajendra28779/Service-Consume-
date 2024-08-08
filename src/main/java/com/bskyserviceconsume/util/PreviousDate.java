package com.bskyserviceconsume.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @Project : BSKY Service Consume
 * @Auther : Sambit Kumar Pradhan
 * @Created On : 01/06/2023 - 3:38 PM
 */
public class PreviousDate {
    public static Date getPreviousDate() {
        Date previousDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(previousDate);
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }
}
