package com.bsuir.validation;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Violation {

    private final String fieldName;
    private final String message;

}
