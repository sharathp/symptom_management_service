package com.sharathp.service.symptom_management.util;

import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class ValidationUtil {

    // singleton
    private ValidationUtil() {
        // no-op
    }

    public static String getErrorMessage(final List<ObjectError> errors) {
        if(errors == null) {
            return "";
        }
        // FIXME - Fix error message using proper ObjectError fields
        return errors.stream()
                .map(oe -> append(oe.getCode(), oe.getDefaultMessage()))
                .collect(Collectors.joining("; "));
    }

    public static String append(final String... elements) {
        final StringJoiner sj = new StringJoiner(" ");
        for(final String element: elements) {
            sj.add(element);
        }
        return sj.toString();
    }
}
