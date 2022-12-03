package com.myfinancemap.app.util;

import com.myfinancemap.app.exception.ServiceExpection;

import javax.servlet.http.HttpServletRequest;

import static com.myfinancemap.app.exception.Error.UTILITY_CLASS;

public class ServerUtils {

    private ServerUtils() {
        throw new ServiceExpection(UTILITY_CLASS);
    }
    public static String applicationUrl(final HttpServletRequest request) {
        return "http://" +
                request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath();
    }
}
