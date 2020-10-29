/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thinhtpt.utils;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.UUID;

/**
 *
 * @author ThinhTPT
 */
public class ValidationHelper implements Serializable {

    public static boolean isValidNumber(String num) {
        String regex = "\\d+";
        return num.matches(regex);
    }

    public static boolean isValidDate(String dateStr) {
        if (dateStr == null) {
            return false;
        }
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try {
            sdf.parse(dateStr);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber.contains("(+84)")) {
            phoneNumber = phoneNumber.replace("(+84)", "0");
        } else if (phoneNumber.contains("+84")) {
            phoneNumber = phoneNumber.replace("+84", "0");
        }
        String regex = "(03|07|08|09|01[2|6|8|9])+([0-9]{8})$";
        return phoneNumber.matches(regex);
    }

    public static boolean isValidUUID(String uuidString) {
        try {
            UUID.fromString(uuidString);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }
        
}
