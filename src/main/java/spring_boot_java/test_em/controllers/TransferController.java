package spring_boot_java.test_em.controllers;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring_boot_java.test_em.services.TransferService;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/transfer")
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @SneakyThrows
    @PostMapping("/{id}")
    public ResponseEntity<String> transferMoney(@PathVariable("id") Long id, @RequestBody Map<String, String> requestBody) {
        String amount = requestBody.get("amount");
        BigDecimal transferAmount = new BigDecimal(amount);
        return transferService.transferMoney(transferAmount, id);
    }
}
