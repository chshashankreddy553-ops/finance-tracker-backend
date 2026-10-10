package com.finance.controller;

import com.finance.dto.*;
import com.finance.entity.*;
import com.finance.repository.*;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionRepository transactions;
    private final UserRepository users;

    public TransactionController(TransactionRepository transactions, UserRepository users) {
        this.transactions = transactions;
        this.users = users;
    }

    @GetMapping
    public ResponseEntity<?> all(Authentication a) {
        if (a == null || a.getName() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: Missing JWT Token.");
        }
        User user = users.findByEmail(a.getName()).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User account not found.");
        }
        List<TransactionResponse> list = transactions.findByUserOrderByDateDescIdDesc(user)
                .stream()
                .map(TransactionResponse::from)
                .toList();
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody TransactionRequest r, Authentication a) {
        if (a == null || a.getName() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: Missing JWT Token.");
        }
        User user = users.findByEmail(a.getName()).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User account not found.");
        }

        Transaction t = new Transaction();
        t.setType(r.type().toUpperCase());
        t.setCategory(r.category());
        t.setAmount(r.amount());
        t.setDescription(r.description());
        t.setTitle(r.category() != null && !r.category().isEmpty() ? r.category() : r.type());
        t.setDate(r.date());
        t.setUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(TransactionResponse.from(transactions.save(t)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, Authentication a) {
        if (a == null || a.getName() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User user = users.findByEmail(a.getName()).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return transactions.findByIdAndUser(id, user)
                .map(t -> {
                    transactions.delete(t);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}