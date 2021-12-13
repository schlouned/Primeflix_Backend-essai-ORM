package com.BackPrimeflix.util;

import com.BackPrimeflix.dto.DateDto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConvertDate {
    public static Date convertJsonDateToDate(String strDate) throws ParseException {
        //convert string date to date
        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(strDate);
        return date1;
    }
}
