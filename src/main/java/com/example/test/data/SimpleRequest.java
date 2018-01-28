package com.example.test.data;

public class SimpleRequest {
    private static String id;

    private static String command;
    private static String description;
    private static String location;
    public static String getCommand() {
        return command;
    }

    public static void setCommand(String command) {
        SimpleRequest.command = command;
    }

    public static String getDescription() {
        return description;
    }

    public static void setDescription(String description) {
        SimpleRequest.description = description;
    }

    public static String getLocation() {
        return location;
    }

    public static void setLocation(String location) {
        SimpleRequest.location = location;
    }

    public static String getId() {
        return id;
    }

    public static void setId(String id) {
        SimpleRequest.id = id;
    }
}
