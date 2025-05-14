package ru.nsu.nextples.ms_contracts.dto.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Schema(description = "Объект ошибки")
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDTO {

    @Schema(description = "Текст ошибки", example = "Ошибка валидации")
    private String message;

    @Schema(description = "HTTP-код ответа", example = "400")
    private int status;

    @Schema(description = "Время ошибки")
    private LocalDateTime timestamp;

    @Schema(description = "Ошибки", example = "{\"fieldName\": \"Error message\"}")
    private Map<String, String> errors;

    @Schema(description = "Список допустимых значений")
    private List<String> allowedValues;
}