package com.test.autothon;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class tesst {

    public static void main(String[] a) {
        String val = "abs\nTesting this\nabg";
        Pattern p = Pattern.compile("\n[^\n]*");//. represents single character
        Matcher m = p.matcher(val);
        if (m.find()) {
            m.group();
        }

    }
}

