package com.med.batch.enumeration;

public enum Deliminator {
    COMMA(","),
    TAB("\t"),
    PIPE("|"),
    SEMICOLON(";"),
    SPACE(" ");

    private final String delimiter;

    Deliminator(String delimiter) {
        this.delimiter = delimiter;
    }

    public String getDelimiter() {
        return delimiter;
    }
}
