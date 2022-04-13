package com.example.backend.utils;

public class Validation {
     public static boolean isValidatedIdx(Integer idx) {
         Integer integer = new Integer("0");

        if (idx.compareTo(integer) == -1) {
            return false;
        }
        return true;
    }

}