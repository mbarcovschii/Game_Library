package com.mbarcovschii.game_library.exceptions;

public class RequiredFieldValueIsNotDefinedException extends RuntimeException {

    private String fieldName;

    public RequiredFieldValueIsNotDefinedException(String fieldName) {
        super("Field (" + fieldName + ") value is not defined ");
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
