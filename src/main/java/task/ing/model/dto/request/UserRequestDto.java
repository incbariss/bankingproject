package task.ing.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserRequestDto(
        @NotBlank(message = "Name cannot be empty")
        String name,

        @NotBlank(message = "Surname cannot be empty")
        String surname,

        @NotBlank(message = "Identity number cannot be empty")
        @Pattern(regexp = "\\d{11}", message = "Identity number must be 11 digit")
        String tckno,

        @Email(message = "Type a valid email address")
        @NotBlank(message = "Email cannot be empty")
        String email
) {
}
