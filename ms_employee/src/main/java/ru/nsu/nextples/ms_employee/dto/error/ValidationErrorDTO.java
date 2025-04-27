package ru.nsu.nextples.ms_employee.dto.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "Ошибка валидации")
public class ValidationErrorDTO extends ErrorDTO {
    @Schema(description = "Ошибки", example = "{\"fieldName\": \"Error message\"}")
    private Map<String, String> errors;
}
