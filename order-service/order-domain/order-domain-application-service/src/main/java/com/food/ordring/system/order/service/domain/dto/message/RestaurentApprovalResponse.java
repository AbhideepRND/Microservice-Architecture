package com.food.ordring.system.order.service.domain.dto.message;

import com.food.ordring.system.domain.valueobject.OrderApprovalStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class RestaurentApprovalResponse {

    private String id;
    private String sagaId;
    private String orderId;
    private String restaurentId;
    private Instant createdAt;
    private OrderApprovalStatus orderApprovalStatus;
    private final List<String> failureMessage;

}
