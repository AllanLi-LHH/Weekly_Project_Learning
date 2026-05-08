package com.aierken.aierken_practice.dto;

import lombok.Data;

@Data
public class DepositRequest {
    private Long userId;
    private Long accountId;
    private double amount;
}
