package com.myfinancemap.app.util;

import java.util.Calendar;
import java.util.Date;

public class TokenUtils {

    private TokenUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static Date calculateExpiryDate(final int expiryTimeInMinutes) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

}