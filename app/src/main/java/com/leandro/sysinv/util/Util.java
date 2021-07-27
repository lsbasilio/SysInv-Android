package com.leandro.sysinv.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    public static SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public static String DateToStr(Date data) {
        if (data == null)
            return "";
        else {
            return sdf2.format(data);
        }
    }

    public static Date TryParseToDate(String date) {
        try {

            return sdf2.parse(date);

        } catch (ParseException e) {
            return null;
        }

    }

}
