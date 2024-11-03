package com.evergreen.zoo.util;

public class CheckRegex {
    static String getRegex(String type) {
        return switch (type) {
            case "email" -> "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
            case "name" -> "^[a-zA-Z\\s]*$";
            case "number" -> "^[0-9]*$";
            case "password" -> "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
            case "username" -> "^[a-zA-Z0-9]*$";
            case "phone" -> "^[0-9]{10}$";
            default -> "";
        };
    }

    public static boolean checkRegex(String type, String input) {
        String regex = getRegex(type);
        return input.matches(regex);
    }
}
