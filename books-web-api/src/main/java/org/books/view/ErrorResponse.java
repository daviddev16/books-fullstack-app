package org.books.view;

import org.books.enums.ErrorType;

public abstract class ErrorResponse<E> {

    private ErrorType errorType;

    public ErrorResponse(ErrorType errorType) {
        this.errorType = errorType;
    }

    public ErrorType getType() {
        return errorType;
    }

    public abstract E getReasons();

}
