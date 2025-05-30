package task.ing.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import task.ing.model.dto.response.ExchangeRateResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class CurrencyConversionService {

    @Value("${exchange.api.key}")
    private String apiKey;

    @Value("${exchange.api.url}")
    private String apiUrlTemplate;

    private final RestTemplate restTemplate = new RestTemplate();
//    private final Map<String, CachedRates> cache = new HashMap<>();

    @Cacheable(value = "exchangeRates", key = "#baseCurrency")
    public Map<String, BigDecimal> getRates(String baseCurrency) {
        return fetchRatesFromApi(baseCurrency).conversionRates();
    }



    public BigDecimal getExchangeRate(String baseCurrency, String targetCurrency) {
        Map<String, BigDecimal> rates = getRates(baseCurrency);
        BigDecimal rate = rates.get(targetCurrency);
        if (rate == null){
            throw new RuntimeException("Cannot find exchange rate: " + targetCurrency);
        }
        return rate;
    }

    public BigDecimal convertAmount(String fromCurrency, String toCurrency, BigDecimal amount) {
        if (fromCurrency.equals(toCurrency)) {
            return amount;
        }

        BigDecimal rate = getExchangeRate(fromCurrency, toCurrency);
        return amount.multiply(rate);
    }

    private ExchangeRateResponse fetchRatesFromApi(String baseCurrency) {
        String url = apiUrlTemplate
                .replace("{apiKey}", apiKey)
                .replace("{baseCurrency}", baseCurrency);
        ResponseEntity<ExchangeRateResponse> response = restTemplate.getForEntity(url, ExchangeRateResponse.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
//            return new CachedRates(response.getBody().conversionRates(), LocalDateTime.now());
            return response.getBody();
        }
        throw new RuntimeException("Cannot get exchange rate");
    }


    @Getter
    @AllArgsConstructor
    private static class CachedRates {
        private final Map<String, BigDecimal> rates;
        private final LocalDateTime timestamp;

    }

}
