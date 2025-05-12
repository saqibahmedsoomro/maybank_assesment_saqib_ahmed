package org.maybak.demo;

import org.junit.jupiter.api.Test;
import org.maybak.demo.entity.TransactionEntity;
import org.maybak.demo.repository.TransactionRepository;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TransactionRepository transactionRepository;

    @Test
    void shouldUpdateDescription() throws Exception {
        Long transactionId = 1L;
        String newDescription = "Updated Description";

        TransactionEntity existingTransaction = new TransactionEntity();
        existingTransaction.setId(transactionId);
        existingTransaction.setDescription("Old Description");

        when(transactionRepository.findById(transactionId))
                .thenReturn(Optional.of(existingTransaction));

        when(transactionRepository.save(any(TransactionEntity.class)))
                .thenReturn(existingTransaction);

        mockMvc.perform(put("/api/transactions/{id}", transactionId)
                        .param("description", newDescription))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value(newDescription));
    }

    @Test
    void testUpdateDescription_MissingDescription() throws Exception {
        Long id = 1L;
        mockMvc.perform(put("/api/transactions/{id}", id)
                        .param("description",""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateDescription_TransactionNotFound() throws Exception {
        Long id = 100L;
        Mockito.when(transactionRepository.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(put("/transactions/{id}", id)
                        .param("description","New Description"))
                .andExpect(status().isNotFound());
    }

}