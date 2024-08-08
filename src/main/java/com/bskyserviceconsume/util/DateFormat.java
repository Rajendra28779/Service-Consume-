package com.bskyserviceconsume.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @Project : BSKY Service Consume
 * @Auther : Sambit Kumar Pradhan,
 * @Created On : 05/04/2023 - 11:08 AM
 **/
public class DateFormat {

	public static String FormatToDateString(String date) {
		return date.substring(4)+"-"+date.substring(2, 4)+"-"+date.substring(0, 2);
	}

	public static String StringToDateFormat(String date) {
		return date.substring(4) + date.substring(2, 4) + date.substring(0, 2);
	}
}
