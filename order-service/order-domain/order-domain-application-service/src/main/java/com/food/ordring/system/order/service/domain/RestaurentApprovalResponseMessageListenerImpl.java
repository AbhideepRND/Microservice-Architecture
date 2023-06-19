package com.food.ordring.system.order.service.domain;

import com.food.ordring.system.order.service.domain.dto.message.RestaurentApprovalResponse;
import com.food.ordring.system.order.service.domain.ports.input.message.listener.resturantapproval.RestaurantApprovalResponseMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;


/**
 * This listener will trigger message received from Restaurant service. During SAGA design pattern
 */
@Slf4j
@Validated
@Service
public class RestaurentApprovalResponseMessageListenerImpl implements RestaurantApprovalResponseMessageListener {
    @Override
    public void orderApprove(RestaurentApprovalResponse restaurentApprovalResponse) {

    }

    @Override
    public void orderRejected(RestaurentApprovalResponse restaurentApprovalResponse) {

    }
}
