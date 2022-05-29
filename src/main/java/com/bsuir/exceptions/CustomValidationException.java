package com.bsuir.exceptions;

import com.bsuir.validation.Violation;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CustomValidationException extends RuntimeException{

    @Getter
    private final transient List<Violation> violations;

    public CustomValidationException(List<Violation> violations) {
        super();
        this.violations = violations;
    }

}
