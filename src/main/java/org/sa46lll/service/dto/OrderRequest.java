package org.sa46lll.service.dto;

public record OrderRequest(
        String orderId
) {

    public OrderRequest {
        if (orderId == null || orderId.isBlank()) {
            throw new IllegalArgumentException("Order ID is invalid.");
        }
    }
}
