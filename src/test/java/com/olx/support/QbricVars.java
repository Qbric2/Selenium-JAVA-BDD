package com.olx.support;

public final class QbricVars {
    private QbricVars() {}
    public static String get(String key, String defaultValue) {
        String v = System.getenv("QBRIC_" + key);
        return (v == null || v.isEmpty()) ? defaultValue : v;
    }
}
