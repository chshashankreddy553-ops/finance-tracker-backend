package com.finance.dto;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
public record TransactionRequest(@NotBlank String type,@NotBlank String category,@NotNull @Positive Double amount,String description,@NotNull LocalDate date){}