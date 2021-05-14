package com.example.bookreviewer.DataFiles;

import android.content.Context;

public class Utils {
    private static Utils instance;

    public static Utils getInstance() {
        if (instance == null) {
            instance = new Utils();
        }
        return instance;
    }
    private Utils() {

    }
}
