package com.myfinancemap.app.util;

import com.myfinancemap.app.persistence.domain.auth.AuthenticationToken;

import java.util.Calendar;
import java.util.Date;

public class TokenUtils {

    public static final String STATUS_VALID = "valid";
    public static final String STATUS_EXPIRED = "expired";
    public static final String STATUS_INVALID = "invalid";

    private TokenUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static Date calculateExpiryDate(final int expiryTimeInMinutes) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }
    public static long timeDifference(final AuthenticationToken token) {
        final Calendar calendar = Calendar.getInstance();
        return token.getExpiryDate().getTime() - calendar.getTime().getTime();
    }

}