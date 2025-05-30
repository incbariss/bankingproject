package task.ing.model.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import task.ing.validation.Iban;

import java.math.BigDecimal;

public record DepositDto(

        @Iban
        @NotBlank(message = "Iban cannot be empty")
        String receiverIban,

        @NotNull(message = "Amount is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
        BigDecimal amount

) {
}
