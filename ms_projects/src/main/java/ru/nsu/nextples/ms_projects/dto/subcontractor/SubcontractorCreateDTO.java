package ru.nsu.nextples.ms_projects.dto.subcontractor;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Schema(description = "Запрос на создание субподрядчика")
public class SubcontractorCreateDTO {

    @NotBlank(message = "Не указано название компании")
    @Size(min = 1, max = 100, message = "Название компании должно содержать 1-100 символов")
    @Schema(description = "Название компании-субподрядчика", requiredMode = REQUIRED)
    private String companyName;

    @Pattern(regexp = "^\\S+@\\S+\\.\\S+$", message = "Введен невалидный email")
    @Schema(description = "Email субподрядчика")
    private String email;

    @Pattern(regexp = "^\\+?[1-9][0-9]{7,14}$", message = "Телефонный номер должен начинаться с + и содержать 7-14 цифр")
    @Schema(description = "Номер телефона субподрядчика")
    private String phoneNumber;
}
