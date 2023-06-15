package com.food.ordring.system.order.service.domain.ports.input.message.listener.resturantapproval;

import com.food.ordring.system.order.service.domain.dto.message.RestaurentApprovalResponse;

public interface RestaurantApprovalResponseMessageListener {

    void orderApprove(RestaurentApprovalResponse restaurentApprovalResponse);

    void orderRejected(RestaurentApprovalResponse restaurentApprovalResponse);
}
