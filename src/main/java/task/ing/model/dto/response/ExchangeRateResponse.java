package task.ing.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Map;

public record ExchangeRateResponse(

        @JsonProperty("conversion_rates")
        Map<String, BigDecimal> conversionRates
) {
}
