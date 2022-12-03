package com.myfinancemap.app.util;

import javax.servlet.http.HttpServletRequest;

public class ServerUtils {

    private ServerUtils() {
        throw new IllegalStateException("Utility class");
    }
    public static String applicationUrl(final HttpServletRequest request) {
        return "http://" +
                request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath();
    }
}
