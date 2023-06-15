package com.food.ordring.system.order.service.domain.ports.input.message.listener.payment;

import com.food.ordring.system.order.service.domain.dto.message.PaymentResponse;

/**
 * This input interface being called from Payment Application service.
 */
public interface PaymentResponseMessageListener {

    /**
     * Being called after the payment is completed
     * @param paymentResponse
     */
    void paymentCompleted(PaymentResponse paymentResponse);

    /**
     * It's called when payment is cancelled due to business logic or failure in payment system.
     * @param paymentResponse
     */
    void paymentCancelled(PaymentResponse paymentResponse);
}
