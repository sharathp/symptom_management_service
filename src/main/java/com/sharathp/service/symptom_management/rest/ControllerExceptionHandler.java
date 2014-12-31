package com.sharathp.service.symptom_management.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    private final Log logger = LogFactory.getLog(getClass());

    @ExceptionHandler({JpaObjectRetrievalFailureException.class})
    public ResponseEntity<String> handleEntityNotFoundExceptionException(final Exception ex) {
        logger.error("Unable to find entity", ex);
        return new ResponseEntity<>("Unable to find entity: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
