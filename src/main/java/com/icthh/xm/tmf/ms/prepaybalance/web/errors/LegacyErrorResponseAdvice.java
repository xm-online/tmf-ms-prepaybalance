package com.icthh.xm.tmf.ms.prepaybalance.web.errors;

import com.icthh.xm.commons.i18n.error.domain.vm.FieldErrorVM;
import com.icthh.xm.commons.i18n.error.web.ExceptionTranslator;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * Keeps two error responses the service had before the migration (Spring 5), ahead of the xm-commons
 * {@link ExceptionTranslator}:
 * <ul>
 *     <li>an unmapped path: Spring 6.1+ throws {@link NoResourceFoundException}, which the xm-commons translator
 *     turned into 500 {@code error.internalServerError}; before, the servlet container answered 404</li>
 *     <li>a missing request parameter: the {@code fieldErrors[].message} keeps the Spring 5 text
 *     ("Required String parameter 'name' is not present")</li>
 * </ul>
 */
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LegacyErrorResponseAdvice {

    private final ExceptionTranslator exceptionTranslator;

    public LegacyErrorResponseAdvice(ExceptionTranslator exceptionTranslator) {
        this.exceptionTranslator = exceptionTranslator;
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public void processNoResourceFound(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<FieldErrorVM> processMissingParameter(MissingServletRequestParameterException ex) {
        MissingServletRequestParameterException legacy =
            new MissingServletRequestParameterException(ex.getParameterName(), ex.getParameterType()) {
                @Override
                public String getMessage() {
                    return "Required " + getParameterType() + " parameter '" + getParameterName() + "' is not present";
                }
            };
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(exceptionTranslator.processMissingServletRequestParameterError(legacy));
    }
}
