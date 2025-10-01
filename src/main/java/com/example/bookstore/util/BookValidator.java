package com.example.bookstore.util;

public class BookValidator {
    public static boolean isValidIsbn(String isbn) {
        if (isbn == null) return false;
        String digits = isbn.replaceAll("\\D", "");
        return (digits.length() == 10 || digits.length() == 13) && digits.equals(isbn);
    }
}

