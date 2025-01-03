package com.med.batch.exception;

public class LineNotFormated extends Exception {
    private final String message;

    public LineNotFormated(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return "Line not formated : " + message;
    }
}
