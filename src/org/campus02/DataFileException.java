package org.campus02;

public class DataFileException extends Exception {
    // nimmt eine message entgegen, sonst nichts
    public DataFileException(String message) {
        super(message);
    }

    // nimmt message und andere exception entgegen (Exception Chaining)
    public DataFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
