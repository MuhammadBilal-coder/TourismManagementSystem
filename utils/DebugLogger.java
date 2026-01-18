/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author dell
 */
package com.tms.utils;

public class DebugLogger {

    public static void log(String message) {
        System.out.println("[DEBUG] " + message);
    }

    // Accept Throwable instead of Exception
    public static void logError(String message, Throwable throwable) {
        System.err.println("[ERROR] " + message);
        if (throwable != null) {
            throwable.printStackTrace();
        }
    }
}
