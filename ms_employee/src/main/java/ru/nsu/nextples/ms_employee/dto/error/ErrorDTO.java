package ru.nsu.nextples.ms_employee.dto.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "Объект ошибки")
public class ErrorDTO {
    @Schema(description = "Текст ошибки", example = "Ошибка валидации")
    private String message;

    @Schema(description = "HTTP-код ответа", example = "400")
    private int status;

    @Schema(description = "Время ошибки")
    private LocalDateTime timestamp;
}
