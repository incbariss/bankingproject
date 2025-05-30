package task.ing.model.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import task.ing.validation.Iban;

import java.math.BigDecimal;

public record TransactionRequestDto(

        @NotBlank(message = "Receiver IBAN cannot be empty")
        @Iban
        String receiverIban,

        @NotNull(message = "Amount cannot be empty")
        @DecimalMin(value = "0.1", message = "Amount must be greater than zero")
        BigDecimal amount


) {


}
