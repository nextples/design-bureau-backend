package ru.nsu.nextples.ms_equipments.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.nsu.nextples.ms_equipments.dto.error.ErrorDTO;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(e -> errors.put(e.getField(), e.getDefaultMessage()));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorDTO.builder()
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .errors(errors)
                        .status(HttpStatus.BAD_REQUEST.value())
                        .build()
                );
    }

    @ExceptionHandler({
            ObjectNotFoundException.class,
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
}
