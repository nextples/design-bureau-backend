package ru.nsu.nextples.ms_employee.dto.department;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;


@Data
@Schema(description = "Запрос на обновление информации об отделе")
public class DepartmentUpdateRequestDTO {

    @Size(min = 3, max = 100, message = "Название отдела должно содержать 3-100 символов")
    @Schema(description = "Название отдела")
    private String name;

    @Schema(description = "ID руководителя отдела")
    private UUID headId;
}
