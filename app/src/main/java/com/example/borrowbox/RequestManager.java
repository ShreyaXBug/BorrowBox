package com.example.borrowbox;

public class RequestManager {

    private static String bookTitle = "";
    private static String bookOwner = "";
    private static String message = "";
    private static boolean requestSent = false;

    public static void saveRequest(
            String title,
            String owner,
            String requestMessage) {

        bookTitle = title;
        bookOwner = owner;
        message = requestMessage;
        requestSent = true;
    }

    public static boolean hasRequest() {
        return requestSent;
    }

    public static String getBookTitle() {
        return bookTitle;
    }

    public static String getBookOwner() {
        return bookOwner;
    }

    public static String getMessage() {
        return message;
    }
}