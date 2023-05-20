package com.example.payment.service.payment;

import com.example.payment.service.common.DomainComponent;
import com.example.payment.service.common.usecase.RegisterHelper;
import com.example.payment.service.common.usecase.VoidUseCaseHandler;
import com.example.payment.service.payment.model.PaymentOrderStatus;
import com.example.payment.service.payment.service.PaymentFactory;
import com.example.payment.service.payment.service.strategy.PaymentStatus;
import com.example.payment.service.payment.usecase.PaymentListenerUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@DomainComponent
@Slf4j
public class PaymentListenerHandler extends RegisterHelper implements VoidUseCaseHandler<PaymentListenerUseCase> {

    private final PaymentFactory paymentFactory;

    public PaymentListenerHandler(PaymentFactory paymentFactory) {
        this.paymentFactory = paymentFactory;
        register(PaymentListenerUseCase.class, this);
    }

    @Transactional
    @Override
    public void handle(PaymentListenerUseCase useCase) {
        PaymentOrderStatus paymentOrderStatus = useCase.orderStatus();
        PaymentStatus paymentStatusService = paymentFactory.findService(paymentOrderStatus);
        paymentStatusService.execute(useCase);
    }
}
