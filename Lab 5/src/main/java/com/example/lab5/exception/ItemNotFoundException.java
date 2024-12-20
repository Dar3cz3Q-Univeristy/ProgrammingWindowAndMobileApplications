package com.example.lab5.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ItemNotFoundException extends CustomException {

    private static final String MESSAGE = "Item %s with id: %s doesn't exist";

    public ItemNotFoundException(ItemType itemType, Object id) {
        super(String.format(MESSAGE, itemType.getValue(), id));
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Getter
    @AllArgsConstructor
    public enum ItemType {
        TEACHER("TEACHER"),
        TEACHER_CLASS("TEACHER_CLASS"),
        RATE("RATE");

        private final String value;
    }
}
