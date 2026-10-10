package com.finance.dto;
import com.finance.entity.Transaction;
import java.time.LocalDate;
public record TransactionResponse(Long id,String type,String category,Double amount,String description,LocalDate date){
 public static TransactionResponse from(Transaction t){return new TransactionResponse(t.getId(),t.getType(),t.getCategory(),t.getAmount(),t.getDescription(),t.getDate());}
}