package org.books.view;

import org.books.enums.ErrorType;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorResponse extends ErrorResponse<List<ValidationErrorResponse.FieldErrorAttribute>> {

    private List<FieldErrorAttribute> fieldErrorAttributes = new ArrayList<>();

    public ValidationErrorResponse() {
        super(ErrorType.VALIDATION);
    }

    public void add(FieldError fieldError) {
        getReasons().add(new FieldErrorAttribute(fieldError.getField(), fieldError.getDefaultMessage()));
    }

    @Override
    public List<FieldErrorAttribute> getReasons() {
        return fieldErrorAttributes;
    }

    public static final class FieldErrorAttribute {

        private String name;
        private String message;

        public FieldErrorAttribute(String name, String message) {
            this.name = name;
            this.message = message;
        }
        public String getName() {
            return name;
        }
        public String getMessage() {
            return message;
        }
    }

}
