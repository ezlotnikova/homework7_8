package com.gmail.zlotnikova.util;

public class BooleanUtil {

    private static BooleanUtil instance;

    private BooleanUtil() {
    }

    public static BooleanUtil getInstance() {
        if (instance == null) {
            instance = new BooleanUtil();
        }
        return instance;
    }

    public boolean parseBoolean(String checkboxStatus) {
        boolean status = false;
        if (checkboxStatus != null) {
            status = true;
        }
        return status;
    }

}