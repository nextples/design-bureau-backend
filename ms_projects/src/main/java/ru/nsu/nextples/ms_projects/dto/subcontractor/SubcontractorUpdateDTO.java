package ru.nsu.nextples.ms_projects.dto.subcontractor;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на обновление субподрядчика")
public class SubcontractorUpdateDTO {

    @Size(min = 1, max = 100, message = "Название компании должно содержать 1-100 символов")
    @Schema(description = "Название компании-субподрядчика")
    private String companyName;

    @Pattern(regexp = "^\\S+@\\S+\\.\\S+$", message = "Введен невалидный email")
    @Schema(description = "Email субподрядчика")
    private String email;

    @Pattern(regexp = "^\\+?[1-9][0-9]{7,14}$", message = "Телефонный номер должен начинаться с + и содержать 7-14 цифр")
    @Schema(description = "Номер телефона субподрядчика")
    private String phoneNumber;
}
