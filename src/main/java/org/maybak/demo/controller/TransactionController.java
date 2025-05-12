package org.maybak.demo.controller;

import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import org.maybak.demo.entity.TransactionEntity;
import org.maybak.demo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionController {


        @Autowired
        TransactionService service;

        @Operation(summary = "Fetch all Transactions, with Pagination and filters")
		@GetMapping("/api/transactions")
 		public ResponseEntity< Page<TransactionEntity> > getTransactions(
                 @RequestParam(defaultValue = "0") int page,
                 @RequestParam(defaultValue = "10") int size,
                 @RequestParam(defaultValue = "") String description,
                 @RequestParam(defaultValue = "0") Long customerId,
                 @RequestParam(defaultValue = "") String accountNumber) {
        try {
                Pageable pageable = PageRequest.of(page, size);
                Page<TransactionEntity> transactions = service.search(description, accountNumber, customerId, pageable);
                return new ResponseEntity<>(transactions, HttpStatus.OK);
			}catch(Exception E)
            {
                System.out.println("Error encountered : "+E.getMessage());
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
		}

        @Operation(summary = "Update transaction description")
        @PutMapping("/api/transactions/{id}")
        public ResponseEntity<?> updateDescription(@PathVariable Long id, @RequestParam String description){
            try{
                if (description == null || description.isBlank()) {
                    return ResponseEntity.badRequest().body("Description must not be empty.");
                }
                TransactionEntity transaction = service.update(id,description);
                return ResponseEntity.ok(transaction);
            }catch(Exception E)
            {
                System.out.println("Error encountered : "+E.getMessage());
                return new ResponseEntity<Map<String,String>>(HttpStatus.NOT_FOUND);
            }
        }
}
