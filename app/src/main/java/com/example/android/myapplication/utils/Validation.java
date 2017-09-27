package com.example.android.myapplication.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Avilash on 27-09-2017.
 */

public class Validation {

    public static boolean isValidEmail(String email) {
        email = email.trim();
        String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }

    }

}
