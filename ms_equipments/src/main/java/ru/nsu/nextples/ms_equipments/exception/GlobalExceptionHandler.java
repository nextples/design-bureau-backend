package ru.nsu.nextples.ms_equipments.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import feign.FeignException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.nsu.nextples.ms_equipments.dto.error.ErrorDTO;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            ConstraintViolationException.class
    })
    public ResponseEntity<ErrorDTO> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(e -> errors.put(e.getField(), e.getDefaultMessage()));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorDTO.builder()
                        .message("Validation in request data failed")
                        .timestamp(LocalDateTime.now())
                        .errors(errors)
                        .status(HttpStatus.BAD_REQUEST.value())
                        .build()
                );
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorDTO> handleFeignException(FeignException ex) {
        return ResponseEntity.status(ex.status())
                .body(ErrorDTO.builder()
                        .message("Error when accessing an external service: " + ex.getMessage())
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDTO> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        String errorMessage = "Invalid request data";
        List<String> allowedValues = null;

        if (ex.getCause() instanceof InvalidFormatException cause) {
            if (cause.getTargetType() != null && cause.getTargetType().isEnum()) {
                errorMessage = "Invalid enum value";
                allowedValues = getEnumValues(cause.getTargetType());
            }
        }
        return ResponseEntity.badRequest()
                .body(ErrorDTO.builder()
                        .message(errorMessage)
                        .status(HttpStatus.BAD_REQUEST.value())
                        .allowedValues(allowedValues)
                        .timestamp(LocalDateTime.now())
                        .build()
                );
    }

    @ExceptionHandler({
            ObjectNotFoundException.class,
            DeleteConflictException.class,
            ObjectNotAvailableException.class,
            DoubleReturnException.class,
            UnavailableObjectStateException.class
    })
    public ResponseEntity<ErrorDTO> handleCustomExceptions(RuntimeException ex) {
        HttpStatus status = determineHttpStatus(ex);
        return buildErrorResponse(ex.getMessage(), status);
    }

    private HttpStatus determineHttpStatus(RuntimeException ex) {
        if (ex instanceof ObjectNotFoundException) {
            return HttpStatus.NOT_FOUND;
        }
        return HttpStatus.BAD_REQUEST;
    }

    private ResponseEntity<ErrorDTO> buildErrorResponse(String message, HttpStatus status) {
        ErrorDTO error = ErrorDTO.builder()
                .message(message)
                .status(status.value())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(status).body(error);
    }

    private List<String> getEnumValues(Class<?> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants())
                .map(Object::toString)
                .collect(Collectors.toList());
    }
}
