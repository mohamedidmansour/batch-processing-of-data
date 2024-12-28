package com.med.batch.interfaces;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Deliminator {
    COMMA(","),
    TAB("\t"),
    PIPE("|"),
    SEMICOLON(";"),
    SPACE(" ");

    private final String delimiter;
}
