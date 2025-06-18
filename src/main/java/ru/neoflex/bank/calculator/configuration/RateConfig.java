package ru.neoflex.bank.calculator.configuration;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Data
@Validated
@Configuration
@ConfigurationProperties
public class RateConfig {
    @NotNull
    private BigDecimal rate;
    @NotNull
    private BigDecimal insuranceRate;
}